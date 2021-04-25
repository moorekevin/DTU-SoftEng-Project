package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class Project {
	private String name;
	private int id;
	private List<Employee> assignedEmployees;
	private List<Activity> activities;
	private Employee projectManager;
	private static int count;

	public Project(String name, int id) {
		this.name = name;
		this.id = id;
		assignedEmployees = new ArrayList<Employee>();
		activities = new ArrayList<Activity>();
	}
	
	public void assignEmployeeToProject(Employee em) {
		assignedEmployees.add(em);
	}
	
	public void removeEmployeeFromProject(Employee em) {
		assignedEmployees.remove(em);
	}
	
	public void assignProjectManager(Employee pm) {
		projectManager = pm;
	}
	
	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void setAssignedEmployees(List<Employee> assignedEmployees) {
		this.assignedEmployees = assignedEmployees;
	}

	public Employee getProjectManger() {
		return projectManager;
	}
	
	public void removeProjectManger() {
		projectManager = null;
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
	
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

}
