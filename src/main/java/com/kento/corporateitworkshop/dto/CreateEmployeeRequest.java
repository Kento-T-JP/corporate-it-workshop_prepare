package com.kento.corporateitworkshop.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
	@NotBlank String name,
	@NotBlank String department
) {
}
