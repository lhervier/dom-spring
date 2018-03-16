package com.github.lhervier.domino.spring.sample;

import lotus.domino.NotesException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	public static class ErrorResponse {
		private String error;
		public String getError() { return this.error; }
		public void setError(String error) { this.error = error; }
	}
	
	@ExceptionHandler(NotesException.class)
	public ResponseEntity<ErrorResponse> processNotesException(NotesException e) {
		ErrorResponse resp = new ErrorResponse();
		resp.setError(e.text);
		return new ResponseEntity<ErrorResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
