package org.techhub.respository;

import java.util.Date;
import java.util.List;

import org.techhub.model.Task;

public interface TaskRepository {
	public boolean addPersonalTask(Task task, int empid, int projectid);
	public boolean addTeamTask(Task task);
	public int getTaskIdByName(String taskName);
	public boolean addTeamTaskRef(int emp_id , int project_id , int task_id);
	public List<Task> getTodaysTask(Date tdate, int emp_id);
	public List<Task> getEmployeeAllTask(int emp_id , int pid);
	public boolean updateStatus(int taskid, String status);
	public Task statusTypePriorityValues(String s , String t , String p);
	public List<Task> getEmployeeTaskStatusWise(int emp_id, String status);

}
