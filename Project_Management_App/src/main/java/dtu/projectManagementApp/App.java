package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

import dtu.dto.ProjectInfo;

public class App {
	private List<Employee> employeeRepository = new ArrayList<>();
	private List<Project> projectRepository = new ArrayList<>();
	
	public void createProject(Project project) throws Exception {
		if (!projectRepository.contains(project)) projectRepository.add(project);
		else {
			throw new Exception("Id already exist");
		}
		
	}

	public void deleteProject(Project project) throws Exception {
		if (projectRepository.contains(project)) projectRepository.remove(project);
		else {
			throw new Exception("Project does not exist");
		}
	}

	public List<Project> getProjects() {
		return projectRepository;
	}

}
