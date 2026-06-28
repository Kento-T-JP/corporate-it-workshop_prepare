package com.kento.corporateitworkshop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kento.corporateitworkshop.entity.Employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	void savesAndFindsEmployee() {
		Employee savedEmployee = employeeRepository.save(new Employee("Peach", "IT Strategy"));

		assertThat(savedEmployee.getId()).isNotNull();
		assertThat(employeeRepository.findById(savedEmployee.getId()))
			.isPresent()
			.get()
			.satisfies(employee -> {
				assertThat(employee.getName()).isEqualTo("Peach");
				assertThat(employee.getDepartment()).isEqualTo("IT Strategy");
			});
	}

	@Test
	void findByIdReturnsEmptyWhenEmployeeDoesNotExist() {
		assertThat(employeeRepository.findById(999L)).isEmpty();
	}

	@Test
	void updatesEmployee() {
		Employee savedEmployee = employeeRepository.save(new Employee("Mario", "Corporate IT"));

		savedEmployee.update("Mario", "IT Platform");
		employeeRepository.save(savedEmployee);

		assertThat(employeeRepository.findById(savedEmployee.getId()))
			.isPresent()
			.get()
			.satisfies(employee -> {
				assertThat(employee.getName()).isEqualTo("Mario");
				assertThat(employee.getDepartment()).isEqualTo("IT Platform");
			});
	}

	@Test
	void deletesEmployee() {
		Employee savedEmployee = employeeRepository.save(new Employee("Toad", "IT Support"));

		employeeRepository.delete(savedEmployee);

		assertThat(employeeRepository.findById(savedEmployee.getId())).isEmpty();
	}
}
