package com.kento.corporateitworkshop.repository;

import com.kento.corporateitworkshop.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
