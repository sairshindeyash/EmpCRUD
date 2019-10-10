package com.example.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.times;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dao.EmployeeDao;
import com.example.entity.Employee;
import com.example.service.EmployeeSeriveImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {
	
	@Mock
	EmployeeDao dao;
	
	@InjectMocks
	private EmployeeSeriveImpl employeeSeriveImpl;
	
	@Test
	public void testGetAllMovies() {
		List<Employee> employeeList=new ArrayList<Employee>();
		employeeList.add(new Employee(1, "sai", 48000.22, "sai.shinde@yash.com"));

		employeeList.add(new Employee(2, "sai1", 48070.22, "sai1.shinde@yash.com"));
		
		
		
		Mockito.when(dao.findAll()).thenReturn(employeeList);
		
		List<Employee> mList=employeeSeriveImpl.getAllEmployees();
		assertEquals(2, mList.size());
	}

	@Test
	public void testMovieById() {
		Employee emp=new Employee(1, "sai", 48000.22, "sai.shinde@yash.com");
		
		Mockito.when(dao.findById(1)).thenReturn(Optional.of(emp));
		
		Employee actual=employeeSeriveImpl.getEmployeeById(1).get();
		assertEquals(1, actual.getEmpId().intValue());
		assertEquals("sai", actual.getEmpName());
		assertEquals("sai.shinde@yash.com", actual.getEmail());
		assertEquals(48000.22, actual.getSalary(), 20.2);
		
	}
	
	
	@Test
	public void testAddMovie() {
		Employee emp=new Employee(5, "sai5", 58000.22, "sai5.shinde@yash.com");
		Mockito.when(dao.save(emp)).thenReturn(emp);
		
		Employee actual=employeeSeriveImpl.saveEmployee(emp);
		
		assertEquals(5, actual.getEmpId().intValue());
		assertEquals("sai5", actual.getEmpName());
		assertEquals("sai5.shinde@yash.com", actual.getEmail());
		assertEquals(58000.22, actual.getSalary(), 20.2);
	}
	
	@Test
	public void deleteTest() {
		Employee emp=new Employee(5, "sai5", 58000.22, "sai5.shinde@yash.com");
		employeeSeriveImpl.deleteEmployeeById(emp.getEmpId());
		Mockito.verify(dao,times(1)).deleteById(5);
	}
	
}
