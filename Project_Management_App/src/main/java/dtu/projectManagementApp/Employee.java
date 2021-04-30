package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String initials;
	private double registeredHoursToday;
	private List<Project> assignedProjects;
	private List<Project> managingProjects;
	private List<PlannedWeek> plannedWeekRepository;

	public Employee(String initials) {
		this.initials = initials;
		assignedProjects = new ArrayList<>();
		managingProjects = new ArrayList<>();
		plannedWeekRepository = new ArrayList<>();
	}

	public boolean isProjectManger() {
		return 0 < managingProjects.size();
	}

	public void setProjectManager(Project project) {
		managingProjects.add(project);
	}
	
	public void removeProjectManager(Project project) {
		managingProjects.remove(project);
	}

	public void assignToProject(Project project) {
		assignedProjects.add(project);
	}

	public void removeProject(Project project) {
		assignedProjects.remove(project);
	}

	public List<Project> getAssignedProjects() {
		return assignedProjects;
	}

	public String getInitials() {
		return initials;
	}

	public List<PlannedWeek> getPlannedWeeks() {
		return plannedWeekRepository;
	}


}
