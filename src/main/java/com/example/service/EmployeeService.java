package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Employee;

public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public Optional<Employee> getEmployeeById(Integer id);

	public Employee saveEmployee(Employee employee);

	public void deleteEmployeeById(Integer id);

}
