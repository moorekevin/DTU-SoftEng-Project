package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

import dtu.dto.ProjectInfo;

public class App {
	private List<Employee> employeeRepository = new ArrayList<>();
	private List<Project> projectRepository = new ArrayList<>();
	
	public void createProject(Project project) {
		for (Project p : projectRepository) {

			try {
				if (project.getId() == p.getId()) throw new Exception("Project already exists");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		projectRepository.add(project);
	}

	public void deleteProject(Project project) throws Exception {
		for (Project p : projectRepository) {
			if (project.getId() != p.getId()) throw new Exception("Project does not exist");
		}
		projectRepository.remove(project);
	}

	public List<Project> getProjects() {
		return projectRepository;
	}
	
	public List<Employee> getEmployees() {
		return employeeRepository;
	}
	
	public void assignEmployeeToProject(Project project, Employee pm, Employee em) {
		
	}
	
	public void assignProjectManager(Project project, Employee pm) {
		if (employeeRepository.contains(pm)) {
		}
			
	}
	
}
