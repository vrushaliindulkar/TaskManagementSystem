package org.techhub.respository;
import org.techhub.helpers.Constants;


import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.techhub.client.TaskManagementSystemApp;
import org.techhub.model.Task;


public class TaskRepositoryImpl extends DB_Instance implements TaskRepository{

	static Logger Logger = org.apache.log4j.Logger.getLogger(TaskRepositoryImpl.class);

	List<Task> TodoList = new ArrayList<>();

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

	@Override
	public List<Task> getTodaysTask(Date tdate, int emp_id) {
	    try {
	       
	        
	        stmt = conn.prepareStatement(Query.getTodoList);
	        stmt.setInt(1, emp_id);
	        stmt.setDate(2, new java.sql.Date(tdate.getTime()));
	        rs = stmt.executeQuery();
	        if(rs!=null)
			{			
			
				while(rs.next()) {
			          Task t=  statusTypePriorityValues( rs.getString(4),  rs.getString(5), rs.getString(6));
			            TodoList.add(new Task(rs.getInt(1), rs.getString(2), rs.getDate(3),
			               t.getStatus()    , t.getTaskType(),t.getPriority() , rs.getDate(7)));
			        } 
			}
	        
	        
	        
	        return TodoList.size() > 0 ? TodoList : null;
	    } catch (Exception ex) {
	        Logger.fatal("TaskRepositoryImpl :: " + ex.getMessage());
	        return null;
	    }
	 
	}

	@Override
	public List<Task> getEmployeeAllTask(int emp_id , int pid) {
		try {
			stmt=conn.prepareStatement(Query.getAllTaskEmployee);
			stmt.setInt(1, emp_id);
			stmt.setInt(2, pid);
			rs = stmt.executeQuery();
			if(rs!=null)
			{			
			
				while(rs.next()) {
			          Task t=  statusTypePriorityValues( rs.getString(4),  rs.getString(5), rs.getString(6));
			            TodoList.add(new Task(rs.getInt(1), rs.getString(2), rs.getDate(3),
			               t.getStatus()    , t.getTaskType(),t.getPriority() , rs.getDate(7)));
			        } 
			}
	        
	       
	        return TodoList.size() > 0 ? TodoList : null;
			
		}catch (Exception ex) {
	        Logger.fatal("TaskRepositoryImpl :: " + ex.getMessage());
	        return null;
	    }
		
	}

	@Override
	public boolean updateStatus(int taskid, String status) {
		try {
			stmt=conn.prepareStatement(Query.updateStatus);
			stmt.setString(1, status);
			stmt.setInt(2, taskid);
			int value=stmt.executeUpdate();
			return value>0 ?true :false;
		}catch (Exception ex) {
	        Logger.fatal("TaskRepositoryImpl :: " + ex.getMessage());
	       
	    }
		return false;
	}

	@Override
	public Task statusTypePriorityValues(String s, String t, String p) {
		try {
			Task task=new Task();
			
			String status="";
			String type="";
			String prio="";
			
			if(s.equals(Constants.STATUS_COMPLETED))
			{
				status= "Completed" ;
				task.setStatus(status);
			}
		    else if(s.equals(Constants.STATUS_IN_PROGRESS))
		    {
		    	status="In Progress";
		    	task.setStatus(status);
		    }
		    else if(s.equals(Constants.STATUS_ON_HOLD))
		    {
		    	status="On Hold";
		    	task.setStatus(status);
		    }
		    else if(s.equals(Constants.STATUS_PENDING))
		    {
		    	status="Pending";
		    	task.setStatus(status);
		    }
			
			if(t.equals(Constants.TEAM_TASK))
			{
				type="Team";
				task.setTaskType(type);
			}
			else
			{
				type="Personal";
				task.setTaskType(type);
			}
			
			if(p.equals(Constants.PRIORITY_HIGH)) {
				prio="High";
				task.setPriority(prio);
			}
			else if(p.equals(Constants.PRIORITY_MEDIUM)) {
				prio="Medium";
				task.setPriority(prio);
			}
			else if(p.equals(Constants.PRIORITY_LOW)) {
				prio="Low";
				task.setPriority(prio);
			}
			
			return task;
			
		}catch (Exception ex) {
	        Logger.fatal("TaskRepositoryImpl :: " + ex.getMessage());
		       
		return null;
	}

	}

	@Override
	public List<Task> getEmployeeTaskStatusWise(int emp_id, String status) {
		try
		{
			stmt=conn.prepareStatement(Query.getAllTaskEmployeeStatusWise);
			stmt.setInt(1, emp_id);
			stmt.setString(2,status);
			rs=stmt.executeQuery();
			if(rs!=null)
			{			
			
				while(rs.next()) {
			          Task t=  statusTypePriorityValues( rs.getString(4),  rs.getString(5), rs.getString(6));
			            TodoList.add(new Task(rs.getInt(1), rs.getString(2), rs.getDate(3),
			               t.getStatus()    , t.getTaskType(),t.getPriority() , rs.getDate(7)));
			        } 
			}
			return TodoList.size() > 0 ? TodoList : null;
			
		}catch (Exception ex) {
	        Logger.fatal("TaskRepositoryImpl :: " + ex.getMessage());
		       
		return null;
	}
	}

}
