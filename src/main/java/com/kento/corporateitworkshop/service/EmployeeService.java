package com.kento.corporateitworkshop.service;

import java.util.List;

import com.kento.corporateitworkshop.dto.CreateEmployeeRequest;
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

	public EmployeeResponse create(CreateEmployeeRequest request) {
		Employee employee = new Employee(request.name(), request.department());
		Employee savedEmployee = employeeRepository.save(employee);

		return toResponse(savedEmployee);
	}

	private EmployeeResponse toResponse(Employee employee) {
		return new EmployeeResponse(
			employee.getId(),
			employee.getName(),
			employee.getDepartment()
		);
	}
}
