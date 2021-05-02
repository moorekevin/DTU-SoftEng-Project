package dtu.projectManagementApp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dtu.exceptions.EmployeeNotFoundException;

public class App {
	private List<Employee> employeeRepository;
	private List<Project> projectRepository;

	public App() {
		employeeRepository = new ArrayList<>();
		projectRepository = new ArrayList<>();
	}

	public List<Project> getProjects() {
		return projectRepository;
	}
	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	// Validations
	public void validateProjectManager(Employee employeeToCheck) throws Exception{
		if (!employeeToCheck.isProjectManger()) {
			throw new Exception("Employee is not project manager");
		}
	}
	public void validateProjectManager(Employee employeeToCheck, Project projectToCheck) throws Exception {
		validateProjectManager(employeeToCheck);
		if (projectToCheck.getProjectManager() != employeeToCheck) {
			throw new Exception("Project Manager is not assigned to the Project");
		}
	}
	public Project findProject(int id) throws Exception {
		for (Project p : projectRepository) {
			if (id == p.getId()) {
				return p;
			}
		}
		throw new Exception("Project does not exist");
	}
	public Employee findEmployee(String initials) throws Exception {
		for (Employee employee : employeeRepository) {
			if (employee.getInitials().equals(initials.toUpperCase())) {
				return employee;
			}
		}
		throw new EmployeeNotFoundException("Employee does not exist");
	}
	public WorkActivity findActivity(Project project, String name) throws Exception {
		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		throw new Exception("Activity is not assigned to the project");
	}

	// Commands 
	public Project createProject(String name) {
		Project projectCreated = new Project(name, Project.makeProjectId());
		projectRepository.add(projectCreated);
		return projectCreated;
	}

	public void deleteProject(Project project) throws Exception {
		Project p = findProject(project.getId());
		projectRepository.remove(p);
	}

	public void addEmployee(String initials) throws Exception {
		try {
			findEmployee(initials);
			throw new Exception("Employee already exists");
		} catch (EmployeeNotFoundException e) {
			employeeRepository.add(new Employee(initials));
		}
	}

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) throws Exception {
		findEmployee(pm.getInitials());
		findEmployee(em.getInitials());

		validateProjectManager(pm, project);
		if (project.getAssignedEmployees().contains(em)) {
			throw new Exception("Employee is already assigned to the project");
		}

		project.assignEmployeeToProject(em);

	}

	public void assignProjectManager(Project project, Employee em) throws Exception {
		if (project.getProjectManager() != null)
			throw new Exception("Project already has a Project Manager");
		em.setProjectManager(project);
		project.assignProjectManager(em);
	}

	// TODO bliver ikke brugt i controller
	public void removeProjectManager(Project project, Employee pm) throws Exception {
		findProject(project.getId());
		findEmployee(pm.getInitials());
		validateProjectManager(pm, project);

		pm.removeProjectManager(project);
		project.removeProjectManager();
	}

	// TODO bliver ikke brugt i controller
	public void removeEmployeeFromProject(Project project, Employee em) throws Exception {

		if (!project.getAssignedEmployees().contains(em))
			throw new Exception("Project is not assigned to employee");
		project.removeEmployeeFromProject(em);

	}
	
	public void removeEmployeeFromActivity(Project project, Employee em, WorkActivity activity){
	    if (project.getActivities().contains(activity)) {
	        if (activity.getAssignedEmployees().contains(em)) {
	            activity.getAssignedEmployees().remove(em);
	        }
	    }
	}

	public WorkActivity createWorkActivity(Project project, Employee pm, String name, String start, String end)
			throws Exception {
		validateWeekInterval(start, end);

		validateProjectManager(pm, project);

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

		validateWeekInterval(start, end);
		validateProjectManager(pm, project);

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

	private void validateWeekInterval(String start, String end) throws Exception {
		isYearWeekValid(start);
		isYearWeekValid(end);
		if (Integer.parseInt(start) > Integer.parseInt(end))
			throw new Exception("Start week cannot be larger than end week");
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
				throw new Exception("Activity start/end-YearWeek is invalid");
			}
		} catch (NumberFormatException e) {
			throw new Exception("Week should be numeric");
		} catch (StringIndexOutOfBoundsException e) {
			throw new Exception("Week should be on form yyww");
		}

	}

	public void assignEmployeeToActivity(Project project, WorkActivity workActivity, Employee pm, Employee em)
			throws Exception {

		// Vigtig
		if (!employeeRepository.contains(pm) || !employeeRepository.contains(em))
			throw new Exception("Employee(s) do not exist");

		if (!pm.isProjectManger())
			throw new Exception("Employee is not project manager");

		if (!projectRepository.contains(project))
			throw new Exception("Project does not exist");
		if (!project.getActivities().contains(workActivity))
			throw new Exception("Project does not have the Activity");

//		//Skal virke
		if (project.getProjectManager() != pm)
			throw new Exception("Project Manager is not assigned to the Project");

		// Vigtig
		if (!project.getAssignedEmployees().contains(em))
			throw new Exception("Employee is not assigned to the Project");

		workActivity.addEmployee(em);
	}

	public void allocateTimeForEmployee(Employee pm, Employee em, Double hours, Project project, WorkActivity activity,
			String yearWeek) throws Exception {

		if (!project.getAssignedEmployees().contains(em))

			throw new Exception("Employee not assigned to project");

		if (!project.getProjectManager().equals(pm))
			throw new Exception("Project Manager is not assigned to the Project");

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

		if (thisPlannedWeek.calculateTotalPlannedHours() + hours > PlannedWeek.MAX_HOURS_PER_WEEK)
			throw new Exception("Not enough available time for week");

		thisPlannedWeek.addHoursForActivity(activity, hours);

	}

	public double calculatePlannedHours(Employee pm, Employee em, String week) throws Exception {
		if (pm == null || em == null) {
			throw new Exception("Employee(s) do not exist");
		}

//		if (!pm.isProjectManger())
//			throw new Exception("Employee is not project manager");

		PlannedWeek foundPlannedWeek = findPlannedWeek(em, week);
		if (foundPlannedWeek == null) {
			return 0.0;
		}
		return foundPlannedWeek.calculateTotalPlannedHours();
	}

	public PlannedWeek findPlannedWeek(Employee em, String week) throws Exception {
		isYearWeekValid(week);

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(week)) {
				return plannedWeek;
			}
		}
		return null;
	}

	public void assignToNonWorkActivity(Employee em, String activityName, Integer days, String yearWeek)
			throws Exception {

		

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			double weekHours = days * PlannedWeek.WORKHOURS_PER_DAY;
			if (plannedWeek.getYearWeek().equals(yearWeek)) {
				if (plannedWeek.calculateTotalPlannedHours() + weekHours > PlannedWeek.WORKHOURS_PER_DAY * PlannedWeek.DAYS_PER_WEEK) {
					throw new Exception("WARNING: The total planned work exceeds allowed hours for the week."
							+ " Please contact a Project Manager");
				}
				for (Activity activity : plannedWeek.getPlannedActivities().keySet()) {
					if (activity.getName().equals(activityName)) {
						plannedWeek.addHoursForActivity(activity, weekHours);
					}
				}
			}
		}
	}
}
