package org.techhub.service;

import java.util.Date;
import java.util.List;

import org.techhub.model.Task;
import org.techhub.respository.TaskRepository;
import org.techhub.respository.TaskRepositoryImpl;

public class TaskServiceImpl  implements TaskService{
    TaskRepository taskRepo=new TaskRepositoryImpl();
	
	@Override
	public boolean addPersonalTask(Task task, int empid, int projectid) {
		
		return taskRepo.addPersonalTask(task, empid, projectid);
	}

	@Override
	public boolean addTeamTask(Task task) {
		
		return taskRepo.addTeamTask(task);
	}

	@Override
	public int getTaskIdByName(String taskName) {
		
		return taskRepo.getTaskIdByName(taskName);
	}

	@Override
	public boolean addTeamTaskRef(int emp_id, int project_id, int task_id) {
		
		return taskRepo.addTeamTaskRef(emp_id, project_id, task_id);
	}

	@Override
	public List<Task> getTodaysTask(Date tdate, int emp_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
