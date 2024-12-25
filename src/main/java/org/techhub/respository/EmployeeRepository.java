package org.techhub.respository;

import java.util.List;

import org.techhub.model.Employee;

public interface EmployeeRepository {
	 public boolean registerEmployee(Employee employee);
	 public List<Employee> getAllEmployee();
	 public boolean deleteEmployee(String fname, String lname);
	 public int getEmployeeId(String fname , String lname);
	 public boolean updateEmployee(int id , String fieldToUpdate , String newValue);
	 public Employee login(String username , String password);
	 public Employee showPersonalDetails(int id);
	 public List<Employee> getEmployeeByTeamId(String team);
	 
}
