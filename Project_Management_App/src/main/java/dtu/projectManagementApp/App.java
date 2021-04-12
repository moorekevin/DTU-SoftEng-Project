package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class App {
	private List<Employee> employeeRepository = new ArrayList<>();
	private List<Project> projectRepository = new ArrayList<>();

	public void createProject(String name, int id) {
		projectRepository.add(new Project(name,id));
	}

	public void deleteProject(Project project) {
		projectRepository.remove(project);
	}

}
