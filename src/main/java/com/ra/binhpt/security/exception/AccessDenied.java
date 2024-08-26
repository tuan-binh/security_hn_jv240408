package com.ra.binhpt.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j // AOP
@Component
public class AccessDenied implements AccessDeniedHandler
{
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException
	{
		log.error("Un Permission {}", accessDeniedException.getMessage());
		response.setHeader("error", "FORBIDEN");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(403);
		Map<String, Object> errors = new HashMap<>();
		errors.put("error",accessDeniedException.getMessage());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(), errors);
	}
}
