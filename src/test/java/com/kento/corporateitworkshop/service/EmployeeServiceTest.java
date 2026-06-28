package com.kento.corporateitworkshop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.kento.corporateitworkshop.dto.CreateEmployeeRequest;
import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.dto.UpdateEmployeeRequest;
import com.kento.corporateitworkshop.entity.Employee;
import com.kento.corporateitworkshop.exception.EmployeeNotFoundException;
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
	void findByIdReturnsEmployeeResponse() {
		when(employeeRepository.findById(1L))
			.thenReturn(Optional.of(new Employee("Mario", "Corporate IT")));

		EmployeeResponse employee = employeeService.findById(1L);

		assertThat(employee.name()).isEqualTo("Mario");
		assertThat(employee.department()).isEqualTo("Corporate IT");
	}

	@Test
	void findByIdThrowsExceptionWhenEmployeeDoesNotExist() {
		when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> employeeService.findById(999L))
			.isInstanceOf(EmployeeNotFoundException.class)
			.hasMessage("Employee not found. id=999");
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

	@Test
	void updateChangesEmployeeAndReturnsResponse() {
		Employee employee = new Employee("Mario", "Corporate IT");
		UpdateEmployeeRequest request = new UpdateEmployeeRequest("Mario", "IT Platform");

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		when(employeeRepository.save(employee)).thenReturn(employee);

		EmployeeResponse updatedEmployee = employeeService.update(1L, request);

		assertThat(updatedEmployee.name()).isEqualTo("Mario");
		assertThat(updatedEmployee.department()).isEqualTo("IT Platform");
	}

	@Test
	void updateThrowsExceptionWhenEmployeeDoesNotExist() {
		UpdateEmployeeRequest request = new UpdateEmployeeRequest("Mario", "IT Platform");
		when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> employeeService.update(999L, request))
			.isInstanceOf(EmployeeNotFoundException.class)
			.hasMessage("Employee not found. id=999");
	}

	@Test
	void deleteRemovesEmployee() {
		Employee employee = new Employee("Mario", "Corporate IT");
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		employeeService.delete(1L);

		verify(employeeRepository).delete(employee);
	}

	@Test
	void deleteThrowsExceptionWhenEmployeeDoesNotExist() {
		when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> employeeService.delete(999L))
			.isInstanceOf(EmployeeNotFoundException.class)
			.hasMessage("Employee not found. id=999");
	}
}
