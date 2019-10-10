package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import com.example.entity.Employee;
import com.example.exception.RecordNotFoundException;
import com.example.service.EmployeeService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)

// @WebMvcTest(EmployeeController.class)
// @ContextConfiguration(classes=EmployeeCrudOperationsApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {

		List<Employee> allEmployees = Arrays.asList(new Employee(1, "sai", 48000.22, "sai.shinde@yash.com"),
				new Employee(2, "sai1", 48070.22, "sai1.shinde@yash.com"));

		Mockito.when(employeeService.getAllEmployees()).thenReturn(allEmployees);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].empName", is(allEmployees.get(0).getEmpName())));

	}

	@Test
	public void givenEmployeeId_wwhenGetEmployeebyId_ThenReturnJsonObject() throws Exception {
		Employee employee = new Employee(1, "sai1", 55000, "sai1.shinde@yash.com");

		Mockito.when(employeeService.getEmployeeById(1)).thenReturn(Optional.of(employee));

		mockMvc.perform(MockMvcRequestBuilders.get("/employee/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.empId", is(1)));
	}

	@Test
	public void givenInvalidEmployeeId_WhenGetEmployeebyId_ThenReturnNotFound() throws Exception {

//		Employee employee=new Employee(11, "sai7", 55555, "sai7@gmail.com");
		Mockito.when(employeeService.getEmployeeById(122)).thenThrow(RecordNotFoundException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/employee/122").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testSaveEmployee() throws Exception {

		String exampleCourseJson = "{\"empName\":\"sai3\",\"salary\":\"48000.22\",\"email\":\"sai3.shinde@yash.com\"}";
		Employee employee = new Employee(3, "sai3", 49000.22, "sai3.shinde@yash.com");

		Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON)
				.content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	/*
	 * @Test public void ShouldThrowExceptionWhenSalrynotProvided() throws Exception
	 * {
	 * 
	 * String exampleCourseJson =
	 * "{\"empName\":\"sai7\",\"salary\\\":\\\"0.0\",\"email\":\"sai7.shinde@yash.com\"}";
	 * Employee employee = new Employee(7, "sai7", 57425.22,
	 * "sai7.shinde@yash.com");
	 * 
	 * Mockito.when(employeeService.saveEmployee(employee)).thenThrow(
	 * FieldShouldNotEmptyException.class);
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON)
	 * .content(exampleCourseJson).contentType(MediaType.APPLICATION_JSON);
	 * 
	 * mockMvc.perform(requestBuilder) .andExpect(status().is4xxClientError());
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * MockHttpServletResponse response = result.getResponse();
	 * 
	 * assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	 * 
	 * 
	 * }
	 */
	@Test
	public void shouldCheckDeleteMethodGetsCalled() throws Exception {
		Integer id = 2;

		Employee employee = new Employee(2, "sai2", 70000, "sai2.shinde@yash.com");

		Mockito.when(employeeService.getEmployeeById(2)).thenReturn(Optional.of(employee));

		doNothing().when(employeeService).deleteEmployeeById(id);

//		  verify(employeeService,times(1)).deleteEmployeeById(id);

		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenEmployeeId_wwhenUpdateEmployeebyId_ThenReturnJsonObject() throws Exception {
		Employee employee = new Employee(2, "sai2", 65000, "sai2.shinde@yash.com");

		Mockito.when(employeeService.getEmployeeById(2)).thenReturn(Optional.of(employee));

		Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);

		Gson gson = new Gson();
		String empGson = gson.toJson(employee);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/employeesup/2").contentType(MediaType.APPLICATION_JSON).content(empGson))
				.andExpect(status().isOk());

	}

	@Test
	public void givenInvalidEmployeeId_whenUpdateEmployeebyId_ThenReturnRecordNotFound() throws Exception {
		Employee employee = new Employee(2, "sai2", 65000, "sai2.shinde@yash.com");

		Mockito.when(employeeService.getEmployeeById(122)).thenThrow(RecordNotFoundException.class);

		Gson gson = new Gson();
		String empGson = gson.toJson(employee);

		Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/employeesup/122").contentType(MediaType.APPLICATION_JSON).content(empGson))
				.andExpect(status().isNotFound());
	}

}
