package com.kento.corporateitworkshop.service;

import java.util.List;

import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.entity.Employee;
import com.kento.corporateitworkshop.repository.EmployeeRepository;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<EmployeeResponse> findAll() {
		return employeeRepository.findAll().stream()
			.map(this::toResponse)
			.toList();
	}

	private EmployeeResponse toResponse(Employee employee) {
		return new EmployeeResponse(
			employee.getId(),
			employee.getName(),
			employee.getDepartment()
		);
	}
}
