package com.ra.binhpt.advice;

import com.ra.binhpt.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationHandler
{
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e)
	{
		return new ResponseEntity<>(e.getMessage(), e.getStatus());
	}
	
	
}
