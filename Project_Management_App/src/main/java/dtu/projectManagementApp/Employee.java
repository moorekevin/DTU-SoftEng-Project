package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String initials;
	private boolean isProjectManager;
	private double registeredHoursToday;
	private List<Project> assignedProjects;
	
	public Employee(String initials) {
		this.initials = initials;
		this.isProjectManager = false;
		assignedProjects = new ArrayList<>();
	}

	public boolean isProjectManger() {
		return isProjectManager;
	}
	
	public void setProjectManager(boolean b) {
		isProjectManager = b;
	}
	
	public void assignToProject(Project project) {
		assignedProjects.add(project);
	}
	
	public List<Project> getAssignedProjects(){
		return assignedProjects;
	}
	
	public String getInitials() {
		return initials;
	}
	
	

}
