package com.kento.corporateitworkshop.exception;

public record FieldErrorResponse(
	String field,
	String message
) {
}
