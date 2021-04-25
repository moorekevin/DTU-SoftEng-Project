package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

import dtu.dto.ProjectInfo;

public class App {
	private List<Employee> employeeRepository = new ArrayList<>();
	private List<Project> projectRepository = new ArrayList<>();
<<<<<<< HEAD

	public void createProject(Project project) throws Exception {
		for (Project p : projectRepository) {
			if (project.getId() == p.getId())
				throw new Exception("Project already exists");
=======
	
	public void createProject(Project project) {
		for (Project p : projectRepository) {

			try {
				if (project.getId() == p.getId()) throw new Exception("Project already exists");
			} catch (Exception e) {
				e.printStackTrace();
			}
>>>>>>> a50ed11391b5b3f3f052b065170141d1bb9d1871
		}
		projectRepository.add(project);
	}

	public void deleteProject(Project project) throws Exception {
		for (Project p : projectRepository) {
			if (project.getId() != p.getId())
				throw new Exception("Project does not exist");
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

	public void assignEmployeeToProject(Project project, Employee pm, Employee em) throws Exception {

		if (employeeRepository.contains(pm)) {
<<<<<<< HEAD
			if (employeeRepository.contains(em)) {
				if (projectRepository.contains(project)) {
					em.assignToProject(project);
				} else
					throw new Exception("Project does not exist");
			} else
				throw new Exception("Employee does not exist");

		} else
			throw new Exception("Project Manager is not assigned to the Project");

	}

	public void assignProjectManager(Project project, Employee em) {
		if (employeeRepository.contains(em) && projectRepository.contains(project)) {
			em.setProjectManager(true);
			project.assignProjectManager(em);

=======
>>>>>>> a50ed11391b5b3f3f052b065170141d1bb9d1871
		}

	}

}
