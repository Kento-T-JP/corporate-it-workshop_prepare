package com.kento.corporateitworkshop.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kento.corporateitworkshop.dto.HealthResponse;

import org.junit.jupiter.api.Test;

class HealthServiceTest {

	private final HealthService healthService = new HealthService();

	@Test
	void getHealthStatusReturnsUp() {
		HealthResponse healthStatus = healthService.getHealthStatus();

		assertThat(healthStatus.status()).isEqualTo("UP");
	}
}
