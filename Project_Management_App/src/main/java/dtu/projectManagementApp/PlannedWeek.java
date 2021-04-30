package dtu.projectManagementApp;

import java.util.Map;
import java.util.HashMap;

public class PlannedWeek {
	private Week week;
	private Map<Activity,Double> plannedActivities;

	public PlannedWeek(Week week) {
		this.week = week;
		this.plannedActivities = new HashMap<Activity,Double>();
	}
	
	public Week getWeek() {
		return week;
	}
	

	public void addHoursForActivity(Activity activity, Double hours) {
		if(!plannedActivities.containsKey(activity)) {
			plannedActivities.put(activity, 0.0);
		}
		
		Double registeredHours = plannedActivities.get(activity);
		
		plannedActivities.put(activity, registeredHours+hours);
	}

	public double getTotalPlannedHours() {
		double totalPlannedHours = 0.0; 
		for (Activity checkActivity : plannedActivities.keySet()) {
			totalPlannedHours += plannedActivities.get(checkActivity);
		}
		return totalPlannedHours;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
  
        if (!(obj instanceof PlannedWeek)) {
            return false;
        }
          
        PlannedWeek plannedWeekComparison = (PlannedWeek) obj;
          
        return this.getWeek().equals(plannedWeekComparison.getWeek());
    }

}
