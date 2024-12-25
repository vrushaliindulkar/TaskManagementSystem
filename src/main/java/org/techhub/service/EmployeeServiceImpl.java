package org.techhub.service;

import java.util.List;

import org.techhub.model.Employee;
import org.techhub.respository.EmployeeRepository;
import org.techhub.respository.EmployeeRepositoryImpl;

public class EmployeeServiceImpl implements EmployeeService{

	EmployeeRepository empRepo=new EmployeeRepositoryImpl();
	public boolean registerEmployee(Employee employee) {
		return empRepo.registerEmployee(employee);
	}
	public List<Employee> getAllEmployee() {
		
		return empRepo.getAllEmployee();
	}
	@Override
	public boolean deleteEmployee(String fname, String lname) {
		
		return empRepo.deleteEmployee(fname, lname);
	}
	@Override
	public int getEmployeeId(String fname, String lname) {
		
		return empRepo.getEmployeeId(fname, lname);
	}
	@Override
	public boolean updateEmployee(int id, String fieldToUpdate, String newValue) {
		
		return empRepo.updateEmployee(id, fieldToUpdate, newValue);
	}
	@Override
	public Employee login(String username, String password) {
		
		return empRepo.login(username, password);
	}
	@Override
	public Employee showPersonalDetails(int id) {
		
		return empRepo.showPersonalDetails(id);
	}
	@Override
	public List<Employee> getEmployeeByTeamId(String team) {
		
		return empRepo.getEmployeeByTeamId(team);
	}
	
}


