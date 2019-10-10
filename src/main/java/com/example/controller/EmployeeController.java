package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Employee;
import com.example.exception.RecordNotFoundException;
import com.example.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/employees")
	public List<Employee> retrieveAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@GetMapping(value = "/employee/{id}")
	public ResponseEntity<Employee> retrieveEmployee(@PathVariable Integer id) {

		Employee employee1 = employeeService.getEmployeeById(id).orElseThrow(() -> new RecordNotFoundException(id));
		return new ResponseEntity<Employee>(employee1, HttpStatus.OK);
	}

	@DeleteMapping(value = "/employees/{id}")
	public void deleteEmployee(@PathVariable Integer id) {
		Employee employee = employeeService.getEmployeeById(id).orElseThrow(() -> new RecordNotFoundException(id));
		employeeService.deleteEmployeeById(employee.getEmpId());
	}

	@PostMapping(value = "/employee")
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		/*
		 * if (employee.getSalary() <= 0.0) { throw new
		 * FieldShouldNotEmptyException("Provide value for salary"); }
		 */
		Employee savedEmployee = employeeService.saveEmployee(employee);
		/*
		 * URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
		 * .buildAndExpand(savedEmployee.getEmpId()).toUri();
		 */
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	} 

	/*
	 * @PutMapping(value = "/employeesup/{id}") public ResponseEntity<Object>
	 * updateEmployee(@RequestBody Employee employee, @PathVariable Integer id) {
	 * Employee employee1 = employeeService.getEmployeeById(id).orElseThrow(() ->
	 * new RecordNotFoundException(id)); employee.setEmpId(id);
	 * employeeService.saveEmployee(employee); return
	 * ResponseEntity.noContent().build(); }
	 */
	
	@PutMapping(value = "/employeesup/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Integer id) {
		Employee employee1 = employeeService.getEmployeeById(id).orElseThrow(() -> new RecordNotFoundException(id));
		employee.setEmpId(id);
		employeeService.saveEmployee(employee);
		return new ResponseEntity<Employee>(employee1, HttpStatus.OK);
	}
}
