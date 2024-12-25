package org.techhub.service;

import java.util.Date;
import java.util.List;

import org.techhub.model.Task;

public interface TaskService {
	
	public boolean addPersonalTask(Task task , int empid , int projectid);
	public boolean addTeamTask(Task task);
	public int getTaskIdByName(String taskName);
	public boolean addTeamTaskRef(int emp_id , int project_id , int task_id);
	public List<Task> getTodaysTask(Date tdate , int emp_id);

}
