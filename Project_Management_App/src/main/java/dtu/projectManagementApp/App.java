package dtu.projectManagementApp;

import static java.util.Calendar.WEEK_OF_YEAR;

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
	private String errorMessage;
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
	
	public WorkActivity createWorkActivity(String name, int start, int end) {
		

		Week startWeek = new Week(start);
		Week endWeek = new Week(end);
		
		
		return new WorkActivity(name, startWeek, endWeek);
	}
	

	public int makeProjectId() {
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
/*		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(firstDayOfWeek);
		calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
		calendar.set(year, month, day);*/
//		Date example = new Date();
//		example.setDate(26);
//		example.setMonth(4);
//		example.setYear(localDate.getYear());
//		System.out.println(localDate.getYear() + "ew");
//		Calendar c = new GregorianCalendar();
//		//c.set(2021, 4, 26);
//		c.setTime(example);
//		//c.setTime(example);
//		System.out.println(c.get(Calendar.DAY_OF_MONTH));
//		System.out.println(c.get(Calendar.MONTH));
//		System.out.println(c.get(Calendar.YEAR));
//		System.out.println(c.get(Calendar.WEEK_OF_YEAR));
		
		
		
		
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

	public String getError() {
		return errorMessage;
	}

	public void deleteProject(int id) {
		Project p = findProject(id);
		if (p != null)
			projectRepository.remove(p);
		else
			errorMessage = "Project does not exist";
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	public void addEmployee(String initials) {
		if (findEmployee(initials) == null)
			employeeRepository.add(new Employee(initials));
		else
			errorMessage = "Employee already exists";
	}

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) {
		if (employeeRepository.contains(pm) && employeeRepository.contains(em)) {
			if (pm.isProjectManger()) {
				if (projectRepository.contains(project)) {
					project.assignEmployeeToProject(em);
				} else
					errorMessage = "Project does not exist";
			} else
				errorMessage = "Employee is not project manager";
		} else
			errorMessage = "Employee(s) do not exist";
	}

	public void assignProjectManager(Project project, Employee em) {
		if (em != null && project != null) {
			if (project.getProjectManager() == null) {
				em.setProjectManager(project);
				project.assignProjectManager(em);
			} else {
				errorMessage = "Project already has a Project Manager";
			}
		} else {
			errorMessage = "Project or Employee does not exist";
		}
	}

	public void removeProjectManager(Project project, Employee pm) {
		if (pm != null && project != null) {
			pm.removeProjectManager(project);
			project.removeProjectManager();
		}
	}

	public void removeEmployeeFromProject(Project project, Employee em) {
		if (em.getAssignedProjects().contains(project)) {
			em.removeProject(project);
		} else {
			errorMessage = "Project is not assigned to employee";
		}

		project.removeEmployeeFromProject(em);
	}
}
