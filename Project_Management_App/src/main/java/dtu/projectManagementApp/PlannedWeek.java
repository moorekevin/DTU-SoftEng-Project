package dtu.projectManagementApp;

import java.util.ArrayList;
import java.util.List;

public class PlannedWeek {
	private int projectId;
	private int plannedHours;
	private Week week;
	private List<Activity> activities = new ArrayList<>();
	
	 
	
	
	
	
	public void setPlannedHours(int hours) {
		plannedHours = hours;
	}

	public int getPlannedHours() {
		return plannedHours;
	}
}
