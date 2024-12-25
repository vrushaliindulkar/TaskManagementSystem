package org.techhub.respository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.techhub.model.Employee;
import org.techhub.model.Project;

public class ProjectRepositoryImpl extends DB_Instance implements ProjectRepository {
   
	static Logger Logger = org.apache.log4j.Logger.getLogger(ProjectRepositoryImpl.class);
	List<Project> list = new ArrayList<Project>();

	@Override
	public boolean newProject(Project project) {
		try {
			stmt=conn.prepareStatement(Query.newProject);
			stmt.setString(1, project.getProjectName());
			stmt.setString(2, project.getDesc());
			stmt.setDate(3, new java.sql.Date(project.getStart_date().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEnd_date().getTime()));

			int value=stmt.executeUpdate();
			return value>0 ? true : false;
			
		}catch(Exception ex){
			Logger.fatal("ProjectRepositoryImpl::"+ex);
		}
		return false;
	}

	@Override
	public int getIdByProjectName(String name) {
		try {
			stmt=conn.prepareStatement(Query.getIdByProjectName);
			stmt.setString(1, name);
			rs=stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
			
		}catch(Exception ex){
			Logger.fatal("ProjectRepositoryImpl::"+ex);
		}
		return 0;
	}

	@Override
	public boolean updateProject(int id, String fieldToUpdate, String newValue) {
		try {
			
			stmt=conn.prepareStatement("UPDATE project SET " + fieldToUpdate + " = ? WHERE project_id = ?");
			stmt.setString(1, newValue);
			stmt.setInt(2, id);

			int value = stmt.executeUpdate();
			return value > 0 ? true : false;
			
		}catch(Exception ex){
			Logger.fatal("ProjectRepositoryImpl::"+ex);
		}
		return false;
	}

	@Override
	public boolean deleteProject(int id) {
		try {
			stmt = conn.prepareStatement(Query.deleteProject);
			stmt.setInt(1, id);
			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			Logger.fatal("ProjectRepositoryImpl ::" + ex);
		}
		return false;
	}

	@Override
	public List<Project> getAllProject() {
		try {
			stmt = conn.prepareStatement(Query.getProjectList);
			rs = stmt.executeQuery();
			while (rs.next()) {
				
				list.add(new Project(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDate(5)));
			}
			return list.size() > 0 ? list : null;

		} catch (Exception ex) {
			Logger.fatal("ProjectRepositoryImpl ::" + ex);
		}
		return null;
	}
	

}
