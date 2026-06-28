package com.kento.corporateitworkshop.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception
	) {
		List<FieldErrorResponse> fieldErrors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> new FieldErrorResponse(
				fieldError.getField(),
				fieldError.getDefaultMessage()
			))
			.toList();

		ErrorResponse response = new ErrorResponse(
			"VALIDATION_ERROR",
			"Request validation failed.",
			fieldErrors
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
