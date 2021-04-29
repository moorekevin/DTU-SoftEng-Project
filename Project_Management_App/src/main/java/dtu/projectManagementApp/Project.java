package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class Project {
	private String name;
	private int id;
	private List<Employee> assignedEmployees;
	private List<WorkActivity> activities;
	private Employee projectManager;

	public Project(String name, int id) {
		this.name = name;
		this.id = id;
		assignedEmployees = new ArrayList<Employee>();
		activities = new ArrayList<WorkActivity>();
		projectManager = null;
	}
	
	public Employee getProjectManager() {
		return projectManager;
	}
	
	public void removeProjectManager() {
		projectManager = null;
	}
	
	public void assignEmployeeToProject(Employee em) {
		assignedEmployees.add(em);
	}
	
	public void removeEmployeeFromProject(Employee em) {
		assignedEmployees.remove(em);
	}
	
	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}
	
	public List<WorkActivity> getActivities() {
		return activities;
	}
	
	public void assignProjectManager(Employee em) throws Exception {
		if (projectManager == null) {
			projectManager = em;
		} else {
			throw new Exception("Project Manager already exists for project");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addActivity(WorkActivity activity) {
		activities.add(activity);
	}

}
