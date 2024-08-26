package com.ra.binhpt.controller;

import com.ra.binhpt.dto.req.FormLogin;
import com.ra.binhpt.dto.req.FormRegister;
import com.ra.binhpt.exception.CustomException;
import com.ra.binhpt.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
	private final IAuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> handleRegister( @RequestBody FormRegister formRegister) throws CustomException
	{
		authService.register(formRegister);
		return ResponseEntity.created(URI.create("api/v1/auth/register")).body("Register successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> handleLogin(@Valid @RequestBody FormLogin formLogin) throws CustomException
	{
//		return ResponseEntity.ok().body(authService.login(formLogin));
		return new ResponseEntity<>(authService.login(formLogin), HttpStatus.OK);
	}
	
}
