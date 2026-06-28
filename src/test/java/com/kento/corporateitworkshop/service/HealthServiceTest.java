package com.kento.corporateitworkshop.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

class HealthServiceTest {

	private final HealthService healthService = new HealthService();

	@Test
	void getHealthStatusReturnsUp() {
		Map<String, String> healthStatus = healthService.getHealthStatus();

		assertThat(healthStatus).containsEntry("status", "UP");
	}
}
