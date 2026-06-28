package com.kento.corporateitworkshop.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.kento.corporateitworkshop.dto.CreateEmployeeRequest;
import com.kento.corporateitworkshop.dto.EmployeeResponse;
import com.kento.corporateitworkshop.dto.UpdateEmployeeRequest;
import com.kento.corporateitworkshop.exception.EmployeeNotFoundException;
import com.kento.corporateitworkshop.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService employeeService;

	@Test
	void findAllReturnsEmployees() throws Exception {
		when(employeeService.findAll()).thenReturn(List.of(
			new EmployeeResponse(1L, "Mario", "Corporate IT"),
			new EmployeeResponse(2L, "Luigi", "Information Systems")
		));

		mockMvc.perform(get("/employees"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(1))
			.andExpect(jsonPath("$[0].name").value("Mario"))
			.andExpect(jsonPath("$[0].department").value("Corporate IT"))
			.andExpect(jsonPath("$[1].id").value(2))
			.andExpect(jsonPath("$[1].name").value("Luigi"))
			.andExpect(jsonPath("$[1].department").value("Information Systems"));
	}

	@Test
	void findByIdReturnsEmployee() throws Exception {
		when(employeeService.findById(1L))
			.thenReturn(new EmployeeResponse(1L, "Mario", "Corporate IT"));

		mockMvc.perform(get("/employees/1"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("Mario"))
			.andExpect(jsonPath("$.department").value("Corporate IT"));
	}

	@Test
	void findByIdReturnsNotFoundWhenEmployeeDoesNotExist() throws Exception {
		when(employeeService.findById(999L))
			.thenThrow(new EmployeeNotFoundException(999L));

		mockMvc.perform(get("/employees/999"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.code").value("EMPLOYEE_NOT_FOUND"))
			.andExpect(jsonPath("$.message").value("Employee not found. id=999"))
			.andExpect(jsonPath("$.fieldErrors").isArray())
			.andExpect(jsonPath("$.fieldErrors").isEmpty());
	}

	@Test
	void createReturnsCreatedEmployee() throws Exception {
		when(employeeService.create(new CreateEmployeeRequest("Peach", "IT Strategy")))
			.thenReturn(new EmployeeResponse(3L, "Peach", "IT Strategy"));

		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Peach",
						"department": "IT Strategy"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/employees/3"))
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(3))
			.andExpect(jsonPath("$.name").value("Peach"))
			.andExpect(jsonPath("$.department").value("IT Strategy"));
	}

	@Test
	void createReturnsBadRequestWhenNameIsBlank() throws Exception {
		mockMvc.perform(post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "",
						"department": "IT Strategy"
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("Request validation failed."))
			.andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
			.andExpect(jsonPath("$.fieldErrors[0].message").value("must not be blank"));
	}

	@Test
	void updateReturnsUpdatedEmployee() throws Exception {
		when(employeeService.update(1L, new UpdateEmployeeRequest("Mario", "IT Platform")))
			.thenReturn(new EmployeeResponse(1L, "Mario", "IT Platform"));

		mockMvc.perform(put("/employees/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Mario",
						"department": "IT Platform"
					}
					"""))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.name").value("Mario"))
			.andExpect(jsonPath("$.department").value("IT Platform"));
	}

	@Test
	void updateReturnsNotFoundWhenEmployeeDoesNotExist() throws Exception {
		when(employeeService.update(999L, new UpdateEmployeeRequest("Mario", "IT Platform")))
			.thenThrow(new EmployeeNotFoundException(999L));

		mockMvc.perform(put("/employees/999")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Mario",
						"department": "IT Platform"
					}
					"""))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value("EMPLOYEE_NOT_FOUND"))
			.andExpect(jsonPath("$.message").value("Employee not found. id=999"));
	}

	@Test
	void updateReturnsBadRequestWhenDepartmentIsBlank() throws Exception {
		mockMvc.perform(put("/employees/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
						"name": "Mario",
						"department": ""
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.fieldErrors[0].field").value("department"));
	}
}
