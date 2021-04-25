package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class App {
	private List<Employee> employeeRepository = new ArrayList<>();
	private List<Project> projectRepository = new ArrayList<>();
	private String errorMessage;

	public void createProject(Project project) {
		for (Project p : projectRepository) {

			if (project.getId() == p.getId()) {
				errorMessage = "Project already exists";
				return;
			}
		}
		projectRepository.add(project);
	}

	public String getError() {
		return errorMessage;
	}
	
	

	public void deleteProject(Project project) {
		for (Project p : projectRepository) {
			if (project.getId() != p.getId()) {
				errorMessage = "Project does not exist";
				return;
			}
		}
		projectRepository.remove(project);
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

	public List<Employee> getEmployees() {
		return employeeRepository;
	}

	public void addEmployee(Employee em) {
		employeeRepository.add(em);
	}

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) {

		if (employeeRepository.contains(pm)) {
			if (employeeRepository.contains(em)) {
				if (projectRepository.contains(project)) {
					em.assignToProject(project);
				} else
					errorMessage = "Project does not exist";
			} else
				errorMessage = "Employee does not exist";
		} else
			errorMessage = "Project Manager is not assigned to the Project";

	}

	public void assignProjectManager(Project project, Employee em) {
		if (employeeRepository.contains(em) && projectRepository.contains(project)) {
			em.setProjectManager(true);
			project.assignProjectManager(em);

		}

	}

}
