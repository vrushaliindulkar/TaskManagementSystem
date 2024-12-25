package org.techhub.respository;

import java.sql.Date;

import org.apache.log4j.Logger;
import org.techhub.client.TaskManagementSystemApp;
import org.techhub.model.Task;

public class TaskRepositoryImpl extends DB_Instance implements TaskRepository{

	static Logger Logger = org.apache.log4j.Logger.getLogger(TaskRepositoryImpl.class);

	@Override
	public boolean addPersonalTask(Task task, int empid, int projectid) {
		try {
		
			
		        cstmt = conn.prepareCall("{call AddTaskPersonal(? ,? ,? , ? ,? ,? ,? ,?)}");
		        cstmt.setString(1, task.getTaskName());
		        cstmt.setDate(2, new java.sql.Date(task.getTaskDate().getTime()));
		        cstmt.setString(3, task.getStatus());
		        cstmt.setString(4, task.getTaskType());
		        cstmt.setString(5, task.getPriority());
		        cstmt.setDate(6, new java.sql.Date(task.getDeadLine().getTime()));
		        cstmt.setInt(7, empid);
		        cstmt.setInt(8, projectid);
		        int result = cstmt.executeUpdate();
		        return result > 0;
			

			
				
		}catch(Exception ex){
			 
			Logger.fatal("TaskRepositoryImpl :: "+ex);
		}
		return false;
	}

	@Override
	public boolean addTeamTask(Task task) {
		try
		{ 
			
			stmt=conn.prepareStatement(Query.addTeamTask);
			stmt.setString(1, task.getTaskName());
			stmt.setDate(2, new java.sql.Date(task.getTaskDate().getTime()));
	        stmt.setString(3, task.getStatus());
	        stmt.setString(4, task.getTaskType());
	        stmt.setString(5, task.getPriority());
	        stmt.setDate(6, new java.sql.Date(task.getDeadLine().getTime()));
	        int values=stmt.executeUpdate();
	        return values>0?true:false;
			
		}
		catch(Exception ex){
			 
			Logger.fatal("TaskRepositoryImpl :: "+ex.getMessage());
		}
		return false;
	}

	@Override
	public int getTaskIdByName(String taskName) {
		try{
			stmt=conn.prepareStatement(Query.getTaskIdByName);
			stmt.setString(1, taskName);
			rs=stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
		}catch(Exception ex)
		{
			Logger.fatal("TaskRepositoryImpl :: "+ex);
		}
		return 0;
	}

	@Override
	public boolean addTeamTaskRef(int emp_id, int project_id, int task_id) {
		
		try {
			stmt=conn.prepareStatement(Query.addTeamTaskRef);
			stmt.setInt(1, emp_id);
			stmt.setInt(2, project_id);
			stmt.setInt(3, task_id);
			int value=stmt.executeUpdate();
			return value>0 ?true:false;
		}
		catch(Exception ex)
		{
			Logger.fatal("TaskRepositoryImpl :: "+ex.getMessage());
		}
		return false;
	}
	

}
