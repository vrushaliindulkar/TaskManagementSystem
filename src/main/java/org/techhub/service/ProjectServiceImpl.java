package org.techhub.service;

import java.util.List;

import org.techhub.model.Project;
import org.techhub.respository.ProjectRepository;
import org.techhub.respository.ProjectRepositoryImpl;

public class ProjectServiceImpl implements ProjectService{
	
	ProjectRepository projectRepo=new ProjectRepositoryImpl();
	
	@Override
	public boolean newProject(Project project) {
	   return projectRepo.newProject(project);
	}

	@Override
	public int getIdByProjectName(String name) {
		
		return projectRepo.getIdByProjectName(name);
	}

	@Override
	public boolean updateProject(int id, String fieldToUpdate, String newValue) {
		
		return projectRepo.updateProject(id, fieldToUpdate, newValue);
	}

	@Override
	public boolean deleteProject(int id) {
		
		return projectRepo.deleteProject(id);
	}

	@Override
	public List<Project> getAllProject() {
		
		return projectRepo.getAllProject();
	}

}
