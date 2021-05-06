package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String initials;
	private List<Project> managingProjects;
	private List<PlannedWeek> plannedWeekRepository;
	private List<NonWorkActivity> nonWorkActivities;

	public Employee(String initials) {
		this.initials = initials.toUpperCase();
		managingProjects = new ArrayList<>();
		plannedWeekRepository = new ArrayList<>();
		nonWorkActivities = new ArrayList<>();
	}

	public boolean isProjectManger() {
		return managingProjects.size() > 0;
	}

	public void setProjectManager(Project project) {
		managingProjects.add(project);
	}
	
	public void removeProjectManager(Project project) {
		managingProjects.remove(project);
	}

	public String getInitials() {
		return initials;
	}

	public List<PlannedWeek> getPlannedWeeks() {
		return plannedWeekRepository;
	}
	
	public PlannedWeek createPlannedWeek(String yearWeek) throws Exception {
		DateServer.validateYearWeek(yearWeek);
		PlannedWeek plannedWeek = new PlannedWeek(yearWeek);
		plannedWeekRepository.add(plannedWeek);
		return plannedWeek;
	
	}
	
}
