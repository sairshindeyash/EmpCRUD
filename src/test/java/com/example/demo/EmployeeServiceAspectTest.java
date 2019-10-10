package com.example.demo;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.example.aop.EmployeeServiceAspect;
import com.example.dao.EmployeeDao;
import com.example.entity.Employee;
import com.example.service.EmployeeSeriveImpl;
import com.example.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceAspectTest {

	@InjectMocks
	private EmployeeSeriveImpl employeeService;
	
	@Mock
	private EmployeeDao dao;
	
	@Test
	public void testAop() {
		Employee employee=new Employee(2, "sai2", 65000, "sai2@gmail.com");
		
		Mockito.when(dao.findById(employee.getEmpId())).thenReturn(Optional.of(employee));
		
		AspectJProxyFactory proxyFactory=new AspectJProxyFactory(employeeService);
		proxyFactory.addAspect(EmployeeServiceAspect.class);
		EmployeeService proxy=proxyFactory.getProxy();
		proxy.getEmployeeById(employee.getEmpId());
		
	}
	
}
