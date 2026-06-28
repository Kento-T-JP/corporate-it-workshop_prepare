package com.kento.corporateitworkshop.exception;

import java.util.List;

public record ErrorResponse(
	String code,
	String message,
	List<FieldErrorResponse> fieldErrors
) {
}
