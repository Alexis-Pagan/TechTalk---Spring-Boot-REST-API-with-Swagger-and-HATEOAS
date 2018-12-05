package com.dev.engineering.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class GenericExceptionAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler
	public ResponseEntity<GenericExceptionModel> handler(Exception ex) {
		
		GenericExceptionModel model = new GenericExceptionModel();
		
		model.setErrorMsg(ex.getMessage());
		
		return new ResponseEntity<GenericExceptionModel
				>(model, HttpStatus.NOT_FOUND);
	}
}
