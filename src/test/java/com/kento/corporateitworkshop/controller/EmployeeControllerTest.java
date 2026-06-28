package com.kento.corporateitworkshop.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.kento.corporateitworkshop.dto.EmployeeResponse;
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
}
