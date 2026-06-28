package com.kento.corporateitworkshop.controller;

import java.net.URI;
import java.util.List;

import com.kento.corporateitworkshop.dto.CreateEmployeeRequest;
import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public List<EmployeeResponse> findAll() {
		return employeeService.findAll();
	}

	@GetMapping("/employees/{id}")
	public EmployeeResponse findById(@PathVariable Long id) {
		return employeeService.findById(id);
	}

	@PostMapping("/employees")
	public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody CreateEmployeeRequest request) {
		EmployeeResponse employee = employeeService.create(request);

		return ResponseEntity
			.created(URI.create("/employees/" + employee.id()))
			.body(employee);
	}
}
