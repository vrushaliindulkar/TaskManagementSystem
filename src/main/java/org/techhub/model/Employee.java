package org.techhub.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	private int Emp_Id;
	private String Emp_FirstName;
	private String Emp_LastName;
	private String Email;
	private String Phone;
	private String Address;
	private String Emp_Role;
	private String username;
	private String password;
	private String team;
	
	
	
	

} 
