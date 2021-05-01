package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String initials;
	private double registeredHoursToday;
	private List<Project> managingProjects;
	private List<PlannedWeek> plannedWeekRepository;

	public Employee(String initials) {
		this.initials = initials.toUpperCase();
		managingProjects = new ArrayList<>();
		plannedWeekRepository = new ArrayList<>();
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
	
	public void addPlannedWeek(PlannedWeek plannedWeek) {
		plannedWeekRepository.add(plannedWeek);
	}


}
