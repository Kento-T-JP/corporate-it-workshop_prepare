package com.kento.corporateitworkshop.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEmployeeRequest(
	@NotBlank String name,
	@NotBlank String department
) {
}
