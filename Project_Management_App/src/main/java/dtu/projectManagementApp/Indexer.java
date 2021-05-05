package dtu.projectManagementApp;

import java.util.List;


import java.util.ArrayList;
import dtu.exceptions.EmployeeNotFoundException;

public class Indexer {
	private List<Employee> employeeRepository;
	private List<Project> projectRepository;
	private List<String> weekRepository;

	public Indexer() {
		employeeRepository = new ArrayList<>();
		projectRepository = new ArrayList<>();
		weekRepository = new ArrayList<>();
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	// Index methods
	public void addEmployee(String initials) throws Exception {
		try {
			findEmployee(initials);
			throw new Exception("Employee already exists");
		} catch (EmployeeNotFoundException e) {
			employeeRepository.add(new Employee(initials));
		}
	}

	public void removeEmployee(String em) throws Exception {
		employeeRepository.remove(findEmployee(em));
	}

	public Project createProject(String name) {
		Project projectCreated = new Project(name, Project.makeProjectId());
		projectRepository.add(projectCreated);
		return projectCreated;
	}

	public void deleteProject(int project) throws Exception {
		projectRepository.remove(findProject(project));
	}

	// Validations
	public void validateProjectManager(Employee employeeToCheck) throws Exception {
		findEmployee(employeeToCheck.getInitials());
		if (!employeeToCheck.isProjectManger()) {
			throw new Exception("Employee is not project manager");
		}
	}

	public void validateProjectManager(Employee employeeToCheck, Project projectToCheck) throws Exception {
		validateProjectManager(employeeToCheck);
		findProject(projectToCheck.getId());
		if (projectToCheck.getProjectManager() != employeeToCheck) {
			throw new Exception("Project Manager is not assigned to the Project");
		}
	}

	public void validateEmployeeAssigned(Employee employeeToCheck, Project projectToCheck) throws Exception {
		findEmployee(employeeToCheck.getInitials());
		findProject(projectToCheck.getId());
		if (!projectToCheck.getAssignedEmployees().contains(employeeToCheck)) {
			throw new Exception("Employee is not assigned to the Project");
		}
	}

	public void validateYearWeek(String yearWeek) throws Exception {
		boolean correctFormat = true;
		if (yearWeek.length() > 4)
			correctFormat = false;

		DateServer date = new DateServer();
		int actualYear = date.getYear() % 100;
		int actualWeek = date.getWeek();
		
		try {
			int year = Integer.parseInt((yearWeek).substring(0, 2));

			int week;
			if (yearWeek.charAt(0) == '0')
				week = Integer.parseInt((yearWeek).substring(3, 4));
			else
				week = Integer.parseInt((yearWeek).substring(2, 4));
			if (!correctFormat || year < actualYear || year == actualYear && week < actualWeek || week < 1
					|| week > 52) {
				throw new Exception("Week(s) are invalid");
			}
		} catch (NumberFormatException e) {
			throw new Exception("Week(s) are invalid");
		}

	}

	public void validateWeekInterval(String start, String end) throws Exception {
		validateYearWeek(start);
		validateYearWeek(end);
		if (Integer.parseInt(start) > Integer.parseInt(end))
			throw new Exception("Start week cannot be larger than end week");
	}

	// Find methods
	public Project findProject(int id) throws Exception {
		for (Project p : projectRepository) {
			if (id == p.getId()) {
				return p;
			}
		}
		throw new Exception("Project does not exist");
	}

	public Employee findEmployee(String initials) throws EmployeeNotFoundException {
		for (Employee employee : employeeRepository) {
			if (employee.getInitials().equals(initials.toUpperCase())) {
				return employee;
			}
		}
		throw new EmployeeNotFoundException("Employee does not exist");
	}

	public WorkActivity findActivity(Project project, String activityName) throws Exception {
		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(activityName)) {
				return activity;
			}
		}
		throw new Exception("Activity is not assigned to the project");
	}

	public PlannedWeek findPlannedWeek(Employee em, String week) {

		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(week)) {
				return plannedWeek;
			}
		}
		PlannedWeek newPlannedWeek = new PlannedWeek(week);
		em.addPlannedWeek(newPlannedWeek);
		return newPlannedWeek;
	}
}