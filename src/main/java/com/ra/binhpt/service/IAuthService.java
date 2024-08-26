package com.ra.binhpt.service;

import com.ra.binhpt.dto.req.FormLogin;
import com.ra.binhpt.dto.req.FormRegister;
import com.ra.binhpt.dto.resp.JwtResponse;
import com.ra.binhpt.exception.CustomException;

public interface IAuthService
{
	void register(FormRegister formRegister) throws CustomException;
	
	JwtResponse login(FormLogin formLogin) throws CustomException;
}
