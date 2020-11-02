package com.example.projetolojaunit.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MissingRequiredParamException extends ResponseEntityExceptionHandler{

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Requisição pode comprometer integridade do banco de dados";
		StandardError err = new StandardError(Instant.now(), status.value(), error, "O parâmetro " + ex.getParameterName() + " é obrigatório");
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
