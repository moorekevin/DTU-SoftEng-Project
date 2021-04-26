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

	public WorkActivity createWorkActivity(String name, int start, int end) {

		Week startWeek = new Week(start);
		Week endWeek = new Week(end);

		return new WorkActivity(name, startWeek, endWeek);
	}

	public int makeProjectId() {
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
//		Calendar calendar = Calendar.getInstance();
//		// calendar.setFirstDayOfWeek(firstDayOfWeek);
//		// calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
//		// calendar.set(year, month, day);
//		
//		Date example = new Date();
//		// example.setDate(localDate.getDayOfMonth());
//		example.setMonth(localDate.getMonthValue());
//		// example.setYear(localDate.getYear());
//		//System.out.println(localDate.getYear() + "ew");
//		Calendar c = new GregorianCalendar();
//		//c.set(2021, 4, 26);
//		c.setTime(example);
//		//c.setTime(example);
//		System.out.println(localDate.getMonth());
//		System.out.println("day of month: " + c.get(Calendar.DAY_OF_MONTH));
//		System.out.println("month: " + c.get(Calendar.MONTH));
//		System.out.println("year: " + c.get(Calendar.YEAR));
//		System.out.println("week of year: " + c.get(Calendar.WEEK_OF_YEAR));
//		c.setTime(date);
//		System.out.println("month with new Date: " + c.get(Calendar.MONTH));
//		System.out.println("week of year with new Date: " + c.get(Calendar.WEEK_OF_YEAR));
//		
//		calendar.setTime(date);
//		System.out.println("month with new Calendar: " + calendar.get(Calendar.MONTH));
//		System.out.println("week of year with new Calendar: " + calendar.get(Calendar.WEEK_OF_YEAR));
		
		
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

}
