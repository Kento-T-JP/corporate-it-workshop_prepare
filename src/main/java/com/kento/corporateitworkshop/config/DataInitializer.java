package com.kento.corporateitworkshop.config;

import com.kento.corporateitworkshop.entity.Employee;
import com.kento.corporateitworkshop.repository.EmployeeRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	@Bean
	ApplicationRunner initializeEmployees(EmployeeRepository employeeRepository) {
		return args -> {
			if (employeeRepository.count() == 0) {
				employeeRepository.save(new Employee("Mario", "Corporate IT"));
				employeeRepository.save(new Employee("Luigi", "Information Systems"));
			}
		};
	}
}
