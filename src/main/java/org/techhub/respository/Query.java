package org.techhub.respository;

public class Query {
	public static String registerEmp="insert into employee values('0' , ? , ? ,? , ? , ? , ? ,? ,? ,?)";
	public static String getEmpList="select * from employee";
	public static String getEmpIdByName="select emp_id from employee where firstname=?  and lastname=?";
	public static String deleteEmployee="delete from employee where emp_id=?";
	public static String login="select * from employee where username= ?  and password=?";
	public static String showPersonalDetails="select * from employee where emp_id=?";
	public static String newProject="insert into project values('0' , ?  ,? , ? , ?)";
	public static  String getIdByProjectName="select project_id from project where project_name=?";
	public static String deleteProject="delete from project where project_id=?";
	public static String getProjectList="select * from project";
	public static String getEmployeeByTeamId="select * from employee where team=?";
	public static String addTeamTask="insert into task values('0' , ? ,?, ?,?,?,?)";
	public static String getTaskIdByName="select task_id from task where task_name=?";
	public static String addTeamTaskRef="insert into employeetaskjoin values(? ,? ,?)";
	public static String getTodoList="SELECT t.task_id, t.task_name, t.task_date, t.status, t.task_type, t.priority, t.deadline FROM task t INNER JOIN employeetaskjoin e ON e.task_id = t.task_id WHERE e.emp_id = ? AND t.task_date = ?";
	public static String getAllTaskEmployee="SELECT t.task_id, t.task_name, t.task_date, t.status, t.task_type, t.priority, t.deadline FROM task t INNER JOIN employeetaskjoin e ON e.task_id = t.task_id  WHERE e.emp_id = ? and e.project_id=?";
	public static String updateStatus="update task set status=? where task_id=?";
	public static String getAllTaskEmployeeStatusWise="SELECT t.task_id, t.task_name, t.task_date, t.status, t.task_type, t.priority, t.deadline FROM task t INNER JOIN employeetaskjoin e ON e.task_id = t.task_id  WHERE e.emp_id = ? and t.status=?";


}
	
