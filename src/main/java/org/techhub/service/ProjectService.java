package org.techhub.service;

import java.util.List;


import org.techhub.model.Project;

public interface ProjectService {
	public boolean newProject(Project project);
	public int getIdByProjectName(String name);
	public boolean updateProject( int id , String fieldToUpdate , String newValue);
	public boolean deleteProject(int id);
	public List<Project> getAllProject();

}
