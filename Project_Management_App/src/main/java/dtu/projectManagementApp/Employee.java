package dtu.projectManagementApp;

import java.util.List;

public class Employee {
	private String initials;
	private boolean isProjectManager;
	private double registeredHoursToday;
	private List<Integer> assignedProjects;
	
	public Employee(String initials) {
		this.initials = initials;
	}

}
