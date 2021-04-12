package dtu.dto;

import java.util.List;

public class EmployeeInfo {
	private String initials;
	private boolean isProjectManager;
	private double registeredHoursToday;
	private List<Integer> assignedProjects;
	
	public EmployeeInfo(String initials) {
		this.initials = initials;
	}

}
