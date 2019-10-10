package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EmployeeDao;
import com.example.entity.Employee;

@Service
public class EmployeeSeriveImpl implements EmployeeService{

	@Autowired
	private EmployeeDao empDao;
	@Override
	public List<Employee> getAllEmployees() {
		return empDao.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(Integer id) {
		return empDao.findById(id);
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return empDao.save(employee);
	}

	@Override
	public void deleteEmployeeById(Integer id) {
		empDao.deleteById(id);
		
	}

}
