package com.kento.corporateitworkshop.controller;

import java.util.List;

import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.service.EmployeeService;

import org.springframework.web.bind.annotation.GetMapping;
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
}
