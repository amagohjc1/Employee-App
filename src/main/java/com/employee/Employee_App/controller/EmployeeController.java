package com.employee.Employee_App.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee_App.exception.ResourceNotFoundException;
import com.employee.Employee_App.model.Employee;
import com.employee.Employee_App.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins= "*")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/postEmployee")
	public Employee postEmployee(@RequestBody Employee em){
		 employeeRepository.save(em);
		 return em;		
	}
	
	
	
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
         Employee employee= employeeRepository.findById(id)
        		 .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: "+ id));
         return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
		System.out.println("Inside update");
         Employee employee= employeeRepository.findById(id)
        		 .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: "+ id));
         
         employee.setFirstName(employeeDetails.getFirstName());
         employee.setLastName(employeeDetails.getLastName());
         employee.setEmailId(employeeDetails.getEmailId());
         
         Employee updatedEmployee = employeeRepository.save(employee);
         
         return ResponseEntity.ok(updatedEmployee);
	
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable long id){
		System.out.println("Inside update");  
         employeeRepository.deleteById(id);
         Map<String, Boolean> response = new HashMap<String, Boolean>();
         response.put("deleted", Boolean.TRUE);
         return ResponseEntity.ok(response);
	
	}

}
