package com.ra.binhpt.service.impl;

import com.ra.binhpt.constants.RoleName;
import com.ra.binhpt.dto.req.FormLogin;
import com.ra.binhpt.dto.req.FormRegister;
import com.ra.binhpt.dto.resp.JwtResponse;
import com.ra.binhpt.exception.CustomException;
import com.ra.binhpt.model.Roles;
import com.ra.binhpt.model.Users;
import com.ra.binhpt.repository.IRoleRepository;
import com.ra.binhpt.repository.IUserRepository;
import com.ra.binhpt.security.jwt.JwtProvider;
import com.ra.binhpt.security.principle.MyUserDetails;
import com.ra.binhpt.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService
{
	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager manager;
	private final JwtProvider jwtProvider;
	
	@Override
	public void register(FormRegister formRegister) throws CustomException
	{
		Set<Roles> roles = new HashSet<>();
		roles.add(findByRoleName(RoleName.ROLE_USER));
		Users users = Users.builder()
				  .fullName(formRegister.getFullName())
				  .email(formRegister.getEmail())
				  .password(passwordEncoder.encode(formRegister.getPassword()))
				  .roles(roles)
				  .status(true)
				  .build();
		userRepository.save(users);
	}
	
	@Override
	public JwtResponse login(FormLogin formLogin) throws CustomException
	{
		Authentication authentication;
		try
		{
			authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getEmail(), formLogin.getPassword()));
		}
		catch (AuthenticationException e)
		{
			throw new CustomException("Username or password is incorrect", HttpStatus.BAD_REQUEST);
		}
		
		MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
		
		if(!userDetails.getUsers().getStatus())
		{
			throw new CustomException("Your account has blocked", HttpStatus.BAD_REQUEST);
		}
		
		
		
		return JwtResponse.builder()
				  .accessToken(jwtProvider.generateToken(userDetails.getUsername()))
				  .fullName(userDetails.getUsers().getFullName())
				  .email(userDetails.getUsers().getEmail())
				  .phone(userDetails.getUsers().getPhone())
				  .dob(userDetails.getUsers().getDob())
				  .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
				  .status(userDetails.getUsers().getStatus())
				  .build();
	}
	
	public Roles findByRoleName(RoleName roleName) throws CustomException
	{
		return roleRepository.findByRoleName(roleName).orElseThrow(() -> new CustomException("role not found", HttpStatus.NOT_FOUND));
	}
}
