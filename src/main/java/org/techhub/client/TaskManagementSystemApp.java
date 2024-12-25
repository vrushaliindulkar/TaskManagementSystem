package org.techhub.client;

import java.util.Date;
import java.lang.invoke.ConstantBootstraps;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.techhub.helpers.Constants;
import org.techhub.helpers.MailNotification;
import org.techhub.model.Employee;
import org.techhub.model.Project;
import org.techhub.model.Task;
import org.techhub.service.EmployeeService;
import org.techhub.service.EmployeeServiceImpl;
import org.techhub.service.ProjectService;
import org.techhub.service.ProjectServiceImpl;
import org.techhub.service.TaskService;
import org.techhub.service.TaskServiceImpl;

public class TaskManagementSystemApp {

	static Logger Logger = org.apache.log4j.Logger.getLogger(TaskManagementSystemApp.class);
	static {
		PropertyConfigurator
				.configure("D:\\Project\\TaskManageementSystem\\src\\main\\resources\\application.properties");
	}
	public static Employee loggedInEmployee;

	public static void main(String[] args) throws ParseException {

		Logger.debug("Main Thread started ");

		EmployeeService empService = new EmployeeServiceImpl();
		ProjectService projectService = new ProjectServiceImpl();
		TaskService taskService = new TaskServiceImpl();
		Scanner sc = new Scanner(System.in);

		System.out.println("****************************************************************************");
		System.out.println();
		System.out.println("...Task Management System....");
		System.out.println();
		System.out.println("*****************************************************************************\n");

		while (true) {
			System.out.println("============================================================================\n");
			System.out.println("1. Register Employee");
			System.out.println("2. Login");
			System.out.println("10.Logout");
			System.out.println("============================================================================\n");
			System.out.println("Enter your choice:");

			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("============================================================================\n");
				System.out.println("Enter Employee Details");
				sc.nextLine();
				System.out.println("Enter Firstname:");
				String firstname = sc.nextLine();
				System.out.println("Enter Lastname:");
				String lastname = sc.nextLine();
				System.out.println("Enter Email:");
				String email = sc.nextLine();
				System.out.println("Enter Contact Number:");
				String phone = sc.nextLine();
				System.out.println("Enter Address:");
				String address = sc.nextLine();
				System.out.println("Enter Employee Role (e.g., Manager, Employee):");
				String role = sc.nextLine();
				System.out.println("Enter Username:");
				String username = sc.nextLine();
				System.out.println("Enter Password:");
				String password = sc.nextLine();
				System.out.println("Enter Team (Development , Marketing , Hr , Sales) :");
				String str=sc.nextLine();
				String team="";
				if(str.equalsIgnoreCase("development")) {
					team=Constants.DEVELOPMENT_TEAM;
				}else if(str.equalsIgnoreCase("marketing"))
				{
					team=Constants.MARKETING_TEAM;
				}
				else if(str.equalsIgnoreCase("sales"))
				{
					team=Constants.SALES_TEAM;
				}

				Employee employee = new Employee(0, firstname, lastname, email, phone, address, role, username,
						password , team);
				if (empService.registerEmployee(employee)) {
					Logger.info("Employee registration successful........");
				} else {
					Logger.info("Some problem occurred during registration.......");
				}
				break;

			case 2:
				System.out.println("============================================================================\n");
				System.out.println("Enter Username:");
				sc.nextLine();
				String loginUsername = sc.nextLine();
				System.out.println("Enter Password:");
				String loginPassword = sc.nextLine();

				loggedInEmployee = empService.login(loginUsername, loginPassword);

				if (loggedInEmployee != null) {
					Logger.info("Login successful.....");
					System.out.println("Login successful.....");
					String empRole = loggedInEmployee.getEmp_Role();
					System.out.println("Welcome, " + loggedInEmployee.getEmp_FirstName() + " (" + empRole + ")");
					showRoleBasedOptions(empRole, empService, projectService, taskService, sc);
				} else {
					Logger.info("Invalid credentials");
					System.out.println("Invalid credentials");
				}
				break;

			case 10:
				System.exit(0);

			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void showRoleBasedOptions(String role, EmployeeService empService, ProjectService projectService,
			TaskService taskService, Scanner sc) throws ParseException {

		switch (role.toLowerCase()) {
		case "manager":
			System.out.println("============================================================================\n");
			System.out.println("1. Show All Employee Details");
			System.out.println("2. Update Employee");
			System.out.println("3. Delete Employee");
			System.out.println("4. Add New Project");
			System.out.println("5. Show Project");
			System.out.println("6. Update Project");
			System.out.println("7. Delete Project");
			System.out.println("8. Give Team Task");
			System.out.println("10. Logout");
			break;

		case "employee":
			System.out.println("============================================================================\n");
			System.out.println("1. View My Details");
			System.out.println("2. Update Own Record");
			System.out.println("3. Add Today's Task");
			System.out.println("4. Update Task");
			System.out.println("10. Logout");
			break;

		default:
			System.out.println("Invalid role. Contact Manager.");
			return;
		}

		while (true) {
			System.out.println(
					"======================================================================================================\n");
			System.out.println("Enter your choice:");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				if (role.equalsIgnoreCase("manager")) {
					System.out.printf("%-5s %-10s %-10s %-30s %-15s %-20s %-15s %-10s\n", "Id", "FName", "LName",
							"Email", "PhoneNo", "Address", "Role", "Username");
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------");
					List<Employee> list = empService.getAllEmployee();
					if (list != null) {
						list.forEach(emp -> System.out.printf("%-5s %-10s %-10s %-30s %-15s %-20s %-15s %-10s\n",
								emp.getEmp_Id(), emp.getEmp_FirstName(), emp.getEmp_LastName(), emp.getEmail(),
								emp.getPhone(), emp.getAddress(), emp.getEmp_Role(), emp.getUsername()));
					} else {
						Logger.info("Some problem occurred while fetching employee details");
					}
				} else if (role.equalsIgnoreCase("employee")) {
					System.out.println(
							"======================================================================================================\n");
					String Firstname = loggedInEmployee.getEmp_FirstName();

					String Lastname = loggedInEmployee.getEmp_LastName();
					int id = empService.getEmployeeId(Firstname, Lastname);
					System.out.println("------------------------------------------------");

					System.out.printf("%-5s %-10s %-10s %-30s %-15s %-20s %-15s %-10s\n", "Id", "FName", "LName",
							"Email", "PhoneNo", "Address", "Role", "Username");
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------");
					Employee emp = empService.showPersonalDetails(id);
					System.out.printf("%-5s %-10s %-10s %-30s %-15s %-20s %-15s %-10s\n", emp.getEmp_Id(),
							emp.getEmp_FirstName(), emp.getEmp_LastName(), emp.getEmail(), emp.getPhone(),
							emp.getAddress(), emp.getEmp_Role(), emp.getUsername());
				}
				break;

			case 2:
				System.out.println(
						"======================================================================================================\n");
				String Firstname = loggedInEmployee.getEmp_FirstName();

				String Lastname = loggedInEmployee.getEmp_LastName();
				int id = empService.getEmployeeId(Firstname, Lastname);
				System.out.println("------------------------------------------------");

				System.out.println(
						"Which  Record Do want to update firstname , lastname , email , phone_number , address. ");
				sc.nextLine();
				String fieldToUpdate = sc.nextLine();
				System.out.println("------------------------------------------------");

				System.out.println("Enter the new value for " + fieldToUpdate);

				String newValue = sc.nextLine();
				System.out.println("------------------------------------------------");

				System.out.println(
						(empService.updateEmployee(id, fieldToUpdate, newValue)) ? " Record Updated Successfully "
								: "Some problem is there......");
				System.out.println(
						"======================================================================================================\n");
				break;

			case 3:
				System.out.println(
						"======================================================================================================\n");
				if (role.equalsIgnoreCase("manager")) {
					Firstname = loggedInEmployee.getEmp_FirstName();

					Lastname = loggedInEmployee.getEmp_LastName();
					System.out.println(
							(empService.deleteEmployee(Firstname, Lastname) ? "Record Deleted Successfully......"
									: "Some problem is there"));

				} else if (role.equalsIgnoreCase("employee")) {

					System.out.println(
							"---------------------------------------Project List--------------------------------------\n");
					sc.hasNextLine();
					System.out.printf("%-10s %-20s %-30s %-20s %-20s \n", "Id", "ProjectName", "Desc", "Start date",
							"End date");
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------");
					List<Project> list = projectService.getAllProject();
					if (list != null) {
						list.forEach(project -> System.out.printf("%-10s %-20s %-30s %-20s %-20s \n",
								project.getProjectId(), project.getProjectName(), project.getDesc(),
								project.getStart_date(), project.getEnd_date()));
					} else {
						Logger.info("Some problem occlurred while fetching project details");
					}
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------");

					System.out.println("Select project name in which you want to add task.");
					sc.nextLine();
					String pname = sc.nextLine();
					int pid = projectService.getIdByProjectName(pname);
					int empid = loggedInEmployee.getEmp_Id();

					System.out.println("Enter Task Details.");
					System.out.println(
							"----------------------------------------------------------------------------------------");

					System.out.println("Enter Task.");
					String taskname = sc.nextLine();
					System.out.println("Enter task priority as Low , medium or high ");
					String str = sc.nextLine();
					String Priority = "";
					if (str.equalsIgnoreCase("High")) {
						Priority = Constants.PRIORITY_HIGH;
					} else if (str.equalsIgnoreCase("medium")) {
						Priority = Constants.PRIORITY_MEDIUM;
					} else if (str.equalsIgnoreCase("low")) {
						Priority = Constants.PRIORITY_LOW;
					}

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date currentDate = new Date();
					String formattedDate = dateFormat.format(currentDate);
					Date parsedDate = dateFormat.parse(formattedDate);

					System.out.println("Enter Deadline for task in this format  (yyyy-MM-dd)");
					String dedalineInput = sc.nextLine();
					Date deadline = dateFormat.parse(dedalineInput);

					Task task = new Task(0, taskname, parsedDate, Constants.STATUS_PENDING, Constants.PERSONAL_TASK,
							Priority, deadline);

					System.out.println((taskService.addPersonalTask(task, empid, pid)) ? "Task Added Successfully..."
							: "Cannot add task............");

				} else {
					System.out.println("Invalid choice for your role.");
				}
				break;
			case 4:
				if (role.equalsIgnoreCase("manager")) {
					Employee e = new Employee();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					System.out.println(
							"======================================================================================================\n");
					System.out.println("Enter Project Details");
					System.out.println(
							"-----------------------------------------------------------------------------------------------------");
					System.out.println("Enter Project Name:");
					sc.nextLine();
					String projectName = sc.nextLine();
					System.out.println("Enter Project Description:");
					String desc = sc.nextLine();

					System.out.println("Enter Start Date (yyyy-MM-dd):");
					String startDateInput = sc.nextLine();
					Date startDate = dateFormat.parse(startDateInput);

					System.out.println("Enter End Date (yyyy-MM-dd):");
					String endDateInput = sc.nextLine();
					Date endDate = dateFormat.parse(endDateInput);

					Project project = new Project(0, projectName, desc, startDate, endDate);
					System.out.println((projectService.newProject(project) ? "Project added successfully....."
							: "Some problem is there....."));
				} else if(role.equalsIgnoreCase("employee"))
				{
					
				}
				else {
					System.out.println("Invalid choice for your role.");
				}
				break;
			case 5:
				System.out.println(
						"======================================================================================================\n");
				System.out.println(
						"---------------------------------------Project List--------------------------------------\n");
				System.out.printf("%-10s %-20s %-30s %-20s %-20s \n", "Id", "ProjectName", "Desc", "Start date",
						"End date");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------");
				List<Project> list = projectService.getAllProject();
				if (list != null) {
					list.forEach(project -> System.out.printf("%-10s %-20s %-30s %-20s %-20s \n",
							project.getProjectId(), project.getProjectName(), project.getDesc(),
							project.getStart_date(), project.getEnd_date()));
				} else {
					Logger.info("Some problem occlurred while fetching project details");
				}

				break;

			case 6:
				if (role.equalsIgnoreCase("manager")) {
					System.out.println(
							"======================================================================================================\n");
					System.out.println("Enter project name");
					sc.nextLine();
					String projectName = sc.nextLine();
					int pid = projectService.getIdByProjectName(projectName);
					System.out.println(
							"Which  Record Do want to update project_name , description , start_date , end_date . ");

					fieldToUpdate = sc.nextLine();
					System.out.println("------------------------------------------------");

					System.out.println("Enter the new value for " + fieldToUpdate);

					newValue = sc.nextLine();
					System.out.println("------------------------------------------------");

					System.out.println(
							(projectService.updateProject(pid, fieldToUpdate, newValue) ? "Record Updated Successfully "
									: "Some problem is there"));

				} else {
					System.out.println("Invalid choice for your role.");
				}
				break;
			case 7:
				if (role.equalsIgnoreCase("manager")) {
					System.out.println(
							"======================================================================================================\n");
					System.out.println("Enter project name");
					sc.nextLine();
					String projectName = sc.nextLine();
					int pid = projectService.getIdByProjectName(projectName);
					System.out.println((projectService.deleteProject(pid) ? "Record Deleted Successfully......"
							: "Some problem is there"));

				} else {
					System.out.println("Invalid choice for your role.");
				}
				break;
			case 8:
				    if(role.equalsIgnoreCase("manager")) {
				    	System.out.println(
								"======================================================================================================\n");
						System.out.println("For Which Department Do You Want To Allocate Task (Development , Marketing , Sales , Hr)");
						sc.nextLine();
						String str=sc.nextLine();
						String dept="";
						if(str.equalsIgnoreCase("Development")){
							dept= Constants.DEVELOPMENT_TEAM;
						}
						else if(str.equalsIgnoreCase("Marketing"))
						{
							dept=Constants.MARKETING_TEAM;
						}
						else if(str.equalsIgnoreCase("sales")) {
							dept=Constants.SALES_TEAM;
						}
						else if(str.equalsIgnoreCase("Hr")) {
							dept=Constants.HR_TEAM;
						}
						List<Employee> listEmployee=empService.getEmployeeByTeamId(dept);
						System.out.println(
								"---------------------------------------Project List--------------------------------------\n");
						System.out.printf("%-10s %-20s %-30s %-20s %-20s \n", "Id", "ProjectName", "Desc", "Start date",
								"End date");
						System.out.println(
								"--------------------------------------------------------------------------------------------------------------");
							list = projectService.getAllProject();
						if (list != null) {
							list.forEach(project -> System.out.printf("%-10s %-20s %-30s %-20s %-20s \n",
									project.getProjectId(), project.getProjectName(), project.getDesc(),
									project.getStart_date(), project.getEnd_date()));
						} else {
							Logger.info("Some problem occlurred while fetching project details");
						}
						System.out.println(
								"--------------------------------------------------------------------------------------------------------------");

						System.out.println("Select project name in which you want to add task.");
						
						String pname = sc.nextLine();
						int pid = projectService.getIdByProjectName(pname);

						System.out.println("Enter Task Details.");
						System.out.println(
								"----------------------------------------------------------------------------------------");

						System.out.println("Enter Task.");
						String taskname = sc.nextLine();
						System.out.println("Enter task priority as Low , medium or high ");
						String str1 = sc.nextLine();
						String Priority = "";
						if (str1.equalsIgnoreCase("High")) {
							Priority = Constants.PRIORITY_HIGH;
						} else if (str1.equalsIgnoreCase("medium")) {
							Priority = Constants.PRIORITY_MEDIUM;
						} else if (str1.equalsIgnoreCase("low")) {
							Priority = Constants.PRIORITY_LOW;
						}

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date currentDate = new Date();
						String formattedDate = dateFormat.format(currentDate);
						Date parsedDate = dateFormat.parse(formattedDate);

						System.out.println("Enter Deadline for task in this format  (yyyy-MM-dd)");
						String dedalineInput = sc.nextLine();
						Date deadline = dateFormat.parse(dedalineInput);

						Task task = new Task(0, taskname, parsedDate, Constants.STATUS_PENDING, Constants.TEAM_TASK,
								Priority, deadline);
						boolean b=false;
						boolean InsertedTask=taskService.addTeamTask(task);
						
						int task_id=taskService.getTaskIdByName(taskname);
						
						List<String> teamEmails = new ArrayList<>();
						if(listEmployee!=null) {
							
							
							String empEmail="";
							for(Employee emp:listEmployee)
							{
								
								int empid=emp.getEmp_Id();
								empEmail = emp.getEmail();
							b=taskService.addTeamTaskRef(empid, pid, task_id);
							
							if (empEmail != null && !empEmail.isEmpty()) {
					            teamEmails.add(empEmail);
					        }
							}
							if (empEmail != null && !empEmail.isEmpty()) {
					            teamEmails.add(empEmail);
					        }
							
						}
						System.out.println((b  && InsertedTask) ?"Records save successfully" :"some problem is there");
						
						
				    }else {
				    	System.out.println("Invalid choice for your role");
				    }
				    break;

			case 10:
				System.out.println("Logged out successfully.");
				return;

			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
