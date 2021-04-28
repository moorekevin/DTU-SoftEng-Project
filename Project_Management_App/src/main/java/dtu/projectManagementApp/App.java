package dtu.projectManagementApp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class App {
	private List<Employee> employeeRepository;
	private List<Project> projectRepository;
	private static int projectNum;
	Date date;
	LocalDate localDate;

	public App() {
		employeeRepository = new ArrayList<>();
		projectRepository = new ArrayList<>();
		projectNum = 1;
	}

	public Project createProject(String name) {
		Project projectCreated = new Project(name, makeProjectId());
		projectRepository.add(projectCreated);
		return projectCreated;
	}

	public int makeProjectId() {
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String projectIDString = "" + localDate.getYear();
		projectIDString = projectIDString.substring(2, 4) + "" + projectNum / 1000 + "" + projectNum / 100 + ""
				+ projectNum / 10 + "" + projectNum / 1;
		int projectID = Integer.parseInt(projectIDString);

		projectNum++;
		return projectID;
	}

	public Project findProject(int id) {
		for (Project p : projectRepository) {
			if (id == p.getId()) {
				return p;
			}
		}
		return null;
	}

	public Employee findEmployee(String initials) {
		for (Employee employee : employeeRepository) {
			if (employee.getInitials().equals(initials)) {
				return employee;
			}
		}
		return null;
	}

	public void deleteProject(int id) throws Exception {
		Project p = findProject(id);
		if (p == null)
			throw new Exception("Project does not exist");
		projectRepository.remove(p);
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	public void addEmployee(String initials) throws Exception {
		if (findEmployee(initials) != null)
			throw new Exception("Employee already exists");
		employeeRepository.add(new Employee(initials));
	}

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) throws Exception {

		if (!employeeRepository.contains(pm) || !employeeRepository.contains(em))
			throw new Exception("Employee(s) do not exist");
		if (!pm.isProjectManger())
			throw new Exception("Employee is not project manager");
		if (!projectRepository.contains(project))
			throw new Exception("Project does not exist");
		project.assignEmployeeToProject(em);

	}

	public void assignProjectManager(Project project, Employee em) throws Exception {
		if (em == null || project == null)
			throw new Exception("Project or Employee does not exist");
		if (project.getProjectManager() != null)
			throw new Exception("Project already has a Project Manager");
		em.setProjectManager(project);
		project.assignProjectManager(em);

	}

	public void removeProjectManager(Project project, Employee pm) throws Exception {
		
		if (pm == null || project == null)
			throw new Exception("Project or Project Manager does not exist");
		pm.removeProjectManager(project);
		project.removeProjectManager();

	}

	public void removeEmployeeFromProject(Project project, Employee em) throws Exception {
		
		if (!em.getAssignedProjects().contains(project))
			throw new Exception("Project is not assigned to employee");
		em.removeProject(project);
		project.removeEmployeeFromProject(em);

	}

	public WorkActivity createWorkActivity(String name, String start, String end) throws Exception {
		isWeekYearValid(start);
		isWeekYearValid(end);
		
		Week startWeek = new Week(start);
		Week endWeek = new Week(end);
		return new WorkActivity(name, startWeek, endWeek);
		
	}
	
	public void isWeekYearValid(String weekYear) throws Exception {

		DateServer date = new DateServer();
		int actualYear = date.getYear() % 100;
		int actualWeek = date.getWeek();
		
		// first two digits
		char temp = weekYear.charAt(0);
		int week = temp == 0 ? 
			Integer.parseInt((weekYear).substring(1, 2)): 
			Integer.parseInt((weekYear).substring(0,2));
		
		// last two digits
		int year = Integer.parseInt((weekYear).substring(2,4));
		
		if (year < actualYear)
			throw new Exception("The year cannot be set to before " + actualYear);

		if (year == actualYear && week < actualWeek)
			throw new Exception("The week cannot be set to before " + actualWeek);
		
		if ( week > 52) 
			throw new Exception("Stop så");
		
//		//TEST
//		if (week < 10) System.out.println("0" + week + year); 
//		else System.out.println(week + year);

	}
	
	public void addActivityToProject(Project project, WorkActivity workActivity) {
		project.addActivity(workActivity);
	}
	
	public void assignEmployeeToActivity(Project project, WorkActivity workActivity, Employee pm, Employee em) throws Exception {
		
		//Vigtig
		if (!employeeRepository.contains(pm) || !employeeRepository.contains(em))
			throw new Exception("Employee(s) do not exist");
		
		if (!pm.isProjectManger())
			throw new Exception("Employee is not project manager");
		
		if (!projectRepository.contains(project))
			throw new Exception("Project does not exist");
		if(!project.getActivities().contains(workActivity))
			throw new Exception("Project does not have the Activity");
		
//		//Skal virke
		if (!project.getProjectManager().equals(pm))
			throw new Exception("Project Manager not assigned to Project");
		
		//Vigtig
		if(!project.getAssignedEmployees().contains(em))
			throw new Exception("Employee is not assigned to the Project");
		
		workActivity.addEmployee(em);

	}
}
