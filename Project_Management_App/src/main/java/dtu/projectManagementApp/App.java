package dtu.projectManagementApp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import dtu.exceptions.*;

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
		projectIDString = projectIDString.substring(2, 4) + "" + projectNum / 1000 + "" + projectNum % 1000 / 100 + ""
				+ projectNum % 100 / 10 + "" + projectNum % 10;
		int projectID = Integer.parseInt(projectIDString);

		projectNum++;
		return projectID;
	}

	public Project findProject(int id) throws Exception {
		for (Project p : projectRepository) {
			if (id == p.getId()) {
				return p;
			}
		}
		throw new Exception("ERROR: Project does not exist");
	}

	public Employee findEmployee(String initials) throws Exception {
		for (Employee employee : employeeRepository) {
			if (employee.getInitials().equals(initials.toUpperCase())) {
				return employee;
			}
		}
		throw new EmployeeNotFoundException("ERROR: Employee does not exist");
	}

	public WorkActivity findActivity(Project project, String name) throws Exception {
		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		throw new Exception("ERROR: Activity is not assigned to the project");
	}

	// TODO Should receive project instead
	public void deleteProject(Project project) throws Exception {
		Project p = findProject(project.getId());
		projectRepository.remove(p);
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	public void addEmployee(String initials) throws Exception {
		try {
			findEmployee(initials);
			throw new Exception("ERROR: Employee already exists");
		} catch (EmployeeNotFoundException e) {
			employeeRepository.add(new Employee(initials));
		}
	}

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) throws Exception {
		// Checks if employees exist
		findEmployee(pm.getInitials());
		findEmployee(em.getInitials());

		if (!pm.isProjectManger()) {
			throw new Exception("ERROR: Employee is not project manager");
		}
		if (project.getProjectManager() != pm) {
			throw new Exception("ERROR: Project Manager is not assigned to the Project");
		}
		if (project.getAssignedEmployees().contains(em)) {
			throw new Exception("ERROR: Employee is already assigned to the project");
		}

		project.assignEmployeeToProject(em);

	}

	public void assignProjectManager(Project project, Employee em) throws Exception {
		if (project.getProjectManager() != null)
			throw new Exception("ERROR: Project already has a Project Manager");
		em.setProjectManager(project);
		project.assignProjectManager(em);
	}

	public void removeProjectManager(Project project, Employee pm) throws Exception {

		if (pm == null || project == null)
			throw new Exception("ERROR: Project or Project Manager does not exist");
		pm.removeProjectManager(project);
		project.removeProjectManager();

	}

	public void removeEmployeeFromProject(Project project, Employee em) throws Exception {

		if (!project.getAssignedEmployees().contains(em))
			throw new Exception("ERROR: Project is not assigned to employee");
		project.removeEmployeeFromProject(em);

	}

	public WorkActivity createWorkActivity(Project project, Employee pm, String name, String start, String end)
			throws Exception {
		validateYearWeeks(start, end);

		if (project.getProjectManager() != pm)
			throw new Exception("ERROR: Project Manager must be assigned to the Project");

		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(name))
				throw new Exception("This Activity is already assigned to the Project");
		}

		Week startWeek = new Week(start);
		Week endWeek = new Week(end);
		WorkActivity activity = new WorkActivity(name, startWeek, endWeek);

		project.addActivity(activity);

		return activity;
	}
	

	public void editActivity(WorkActivity activity, Project project, Employee pm, String name, String start, String end)
			throws Exception {

		// Use old weeks if no information was typed
		if (start.equals(""))
			start = activity.getStartWeek().getYearWeek();
		if (end.equals(""))
			end = activity.getEndWeek().getYearWeek();

		validateYearWeeks(start, end);

		if (project.getProjectManager() != pm)
			throw new Exception("Project Manager must be assigned to the Project");

		// Should only be updated if user typed information
		if (!name.equals(""))
			activity.setName(name);
		activity.setStart(start);
		activity.setEnd(end);
	}

	public void setExpectedHours(WorkActivity activity, double exptectedHours) {
		if (activity != null) {
			activity.setExpectedHours(exptectedHours);
		}
	}

	private void validateYearWeeks(String start, String end) throws Exception {
		isYearWeekValid(start);
		isYearWeekValid(end);
		if (Integer.parseInt(start) > Integer.parseInt(end))
			throw new Exception("ERROR: Start week cannot be larger than end week");
	}

	public void isYearWeekValid(String yearWeek) throws Exception {
		DateServer date = new DateServer();
		int actualYear = date.getYear() % 100;
		int actualWeek = date.getWeek();

		// first two digits
		try {
			int year = Integer.parseInt((yearWeek).substring(0, 2));

			// last two digits
			char temp = yearWeek.charAt(0);
			int week;
			week = temp == 0 ? Integer.parseInt((yearWeek).substring(3, 4))
					: Integer.parseInt((yearWeek).substring(2, 4));

			if (year < actualYear || year == actualYear && week < actualWeek || week < 1 || week > 52) {
				throw new Exception("ERROR: Activity start/end-YearWeek is invalid");
			}
		} catch (NumberFormatException e) {
			throw new Exception("ERROR: Week should be numeric");
		} catch (StringIndexOutOfBoundsException e) {
			throw new Exception("ERROR: Week should be on form yyww");
		}

	}

	public void assignEmployeeToActivity(Project project, WorkActivity workActivity, Employee pm, Employee em)
			throws Exception {

		// Vigtig
		if (!employeeRepository.contains(pm) || !employeeRepository.contains(em))
			throw new Exception("ERROR: Employee(s) do not exist");

		if (!pm.isProjectManger())
			throw new Exception("ERROR: Employee is not project manager");

		if (!projectRepository.contains(project))
			throw new Exception("ERROR: Project does not exist");
		if (!project.getActivities().contains(workActivity))
			throw new Exception("ERROR: Project does not have the Activity");

//		//Skal virke
		if (project.getProjectManager() != pm)
			throw new Exception("ERROR: Project Manager is not assigned to the Project");

		// Vigtig
		if (!project.getAssignedEmployees().contains(em))
			throw new Exception("ERROR: Employee is not assigned to the Project");

		workActivity.addEmployee(em);
	}

	public void allocateTimeForEmployee(Employee pm, Employee em, Double hours, Project project, WorkActivity activity,
			String yearWeek) throws Exception {

		// HUSK FEJLHÅNDTERING

		if (!project.getAssignedEmployees().contains(em))

			throw new Exception("Employee not assigned to project");

		if (!project.getProjectManager().equals(pm))
			throw new Exception("ERROR: Project Manager is not assigned to the Project");

		if (Integer.parseInt(yearWeek) > Integer.parseInt(activity.getEndWeek().getYearWeek())) {
			throw new Exception("Activity has not begun/is ended for planned time");
		}

		PlannedWeek thisPlannedWeek = null;

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(yearWeek)) {
				thisPlannedWeek = plannedWeek;
			}
		}

		if (thisPlannedWeek == null) {
			thisPlannedWeek = new PlannedWeek(yearWeek);
			thisPlannedWeek.addActivityToWeek(activity);
			em.addPlannedWeek(thisPlannedWeek);
		}

		if (thisPlannedWeek.calculateTotalPlannedHours() + hours > 168.0)
			throw new Exception("Not enough available time for week");
		
		thisPlannedWeek.addHoursForActivity(activity, hours);

	}

	public double calculatePlannedHours(Employee pm, Employee em, String week) throws Exception {

		// HUSK FEJLHÅNDTERING
		isYearWeekValid(week);

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(week)) {
				return plannedWeek.calculateTotalPlannedHours();
			}
		}

		return 0.0;
	}

	public void assignToNonWorkActivity(Employee em, NonWorkActivity activity, Integer days, String yearWeek){
        
        int hours = days * 10;
		//gør noget med integer
		

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(yearWeek)) {
				
				
			}
		}
	}

}
