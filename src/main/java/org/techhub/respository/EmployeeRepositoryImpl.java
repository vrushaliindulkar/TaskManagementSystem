package org.techhub.respository;

import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;
import org.techhub.model.Employee;

public class EmployeeRepositoryImpl extends DB_Instance implements EmployeeRepository {
	static Logger Logger = org.apache.log4j.Logger.getLogger(EmployeeRepositoryImpl.class);
	List<Employee> list = new ArrayList<Employee>();

	public boolean registerEmployee(Employee employee) {
		try {

			stmt = conn.prepareStatement(Query.registerEmp);
			stmt.setString(1, employee.getEmp_FirstName());
			stmt.setString(2, employee.getEmp_LastName());
			stmt.setString(3, employee.getEmail());
			stmt.setString(4, employee.getPhone());
			stmt.setString(5, employee.getAddress());
			stmt.setString(6, employee.getEmp_Role());
			stmt.setString(7, employee.getUsername());
			stmt.setString(8, employee.getPassword());
			stmt.setString(9, employee.getTeam());
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return false;

	}

	public List<Employee> getAllEmployee() {
		try {
			stmt = conn.prepareStatement(Query.getEmpList);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9) , rs.getString(10)));
			}
			return list.size() > 0 ? list : null;

		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return null;
	}

	@Override
	public int getEmployeeId(String fname, String lname) {
		try {
			stmt = conn.prepareStatement(Query.getEmpIdByName);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}

		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
			return 0;
		}

	}

	@Override
	public boolean deleteEmployee(String fname, String lname) {
		try {
			int id = this.getEmployeeId(fname, lname);
			stmt = conn.prepareStatement(Query.deleteEmployee);
			stmt.setInt(1, id);
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return false;
	}

	@Override
	public boolean updateEmployee(int id, String fieldToUpdate, String newValue) {
		try {
			stmt = conn.prepareStatement("UPDATE Employee SET " + fieldToUpdate + " = ? WHERE Emp_Id = ?");
			stmt.setString(1, newValue);
			stmt.setInt(2, id);

			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return false;
	}

	@Override
	public Employee login(String username, String password) {
		try {
			Employee e=null;
			stmt = conn.prepareStatement(Query.login);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();

			if (rs.next()) {
				e=new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9) , rs.getString(10));
				return e;
			} else {
				return e;
			}


		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return null;
	}

	@Override
	public Employee showPersonalDetails(int id) {
		try {
			Employee e=null;
			stmt = conn.prepareStatement(Query.showPersonalDetails);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				e=new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9) , rs.getString(10));
				return e;
			} else {
				return e;
			}
		} catch (Exception ex) {
			Logger.fatal("EmployeeRepositoryImpl ::" + ex);
		}
		return null;
	}

	@Override
	public List<Employee> getEmployeeByTeamId(String team) {
		try {
			
			stmt=conn.prepareStatement(Query.getEmployeeByTeamId);
			stmt.setString(1, team);
			rs=stmt.executeQuery();
			while (rs.next()) {
				list.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9) , rs.getString(10)));
			}
			return list.size() > 0 ? list : null;

			
		}catch(Exception ex)
		{
			Logger.fatal("EmployeeRepositoryImpl:: "+ex);
		}
		return null;
	}

}
