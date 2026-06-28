package com.kento.corporateitworkshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import com.kento.corporateitworkshop.dto.CreateEmployeeRequest;
import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.entity.Employee;
import com.kento.corporateitworkshop.repository.EmployeeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	void findAllReturnsEmployeeResponses() {
		when(employeeRepository.findAll()).thenReturn(List.of(
			new Employee("Mario", "Corporate IT"),
			new Employee("Luigi", "Information Systems")
		));

		List<EmployeeResponse> employees = employeeService.findAll();

		assertThat(employees)
			.extracting(EmployeeResponse::name)
			.containsExactly("Mario", "Luigi");
	}

	@Test
	void createSavesEmployeeAndReturnsResponse() {
		CreateEmployeeRequest request = new CreateEmployeeRequest("Peach", "IT Strategy");

		when(employeeRepository.save(any(Employee.class)))
			.thenReturn(new Employee("Peach", "IT Strategy"));

		EmployeeResponse employee = employeeService.create(request);

		assertThat(employee.name()).isEqualTo("Peach");
		assertThat(employee.department()).isEqualTo("IT Strategy");
	}
}
