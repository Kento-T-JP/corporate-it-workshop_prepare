package com.kento.corporateitworkshop.service;

import com.kento.corporateitworkshop.dto.HealthResponse;

import org.springframework.stereotype.Service;

@Service
public class HealthService {

	public HealthResponse getHealthStatus() {
		return new HealthResponse("UP");
	}
}
