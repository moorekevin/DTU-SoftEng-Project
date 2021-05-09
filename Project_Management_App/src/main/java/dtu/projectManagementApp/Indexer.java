package dtu.projectManagementApp;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import dtu.exceptions.EmployeeNotFoundException;

public class Indexer {
	private List<Employee> employeeRepository;
	private List<Project> projectRepository;
	

	public Indexer() {
		employeeRepository = new ArrayList<>();
		projectRepository = new ArrayList<>();
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
		Project projectCreated = new Project(name);
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
		PlannedWeek plannedWeek = null;
		for (PlannedWeek pw : em.getPlannedWeeks()) {
			if (pw.getYearWeek().equals(week)) {
				plannedWeek = pw;
			}
		}
		return plannedWeek;
//		PlannedWeek newPlannedWeek = new PlannedWeek(week);
//		em.addPlannedWeek(newPlannedWeek);
//		return newPlannedWeek;
	}
}