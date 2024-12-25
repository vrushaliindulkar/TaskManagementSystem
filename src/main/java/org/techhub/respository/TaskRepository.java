package org.techhub.respository;

import org.techhub.model.Task;

public interface TaskRepository {
	public boolean addPersonalTask(Task task, int empid, int projectid);
	public boolean addTeamTask(Task task);
	public int getTaskIdByName(String taskName);
	public boolean addTeamTaskRef(int emp_id , int project_id , int task_id);

}
