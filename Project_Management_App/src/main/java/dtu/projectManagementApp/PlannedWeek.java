package dtu.projectManagementApp;

import java.util.Map;
import java.util.HashMap;

public class PlannedWeek extends Week {
	private Map<Activity,Double> plannedActivities;
	private final int NORMAL_WORKHOURS_PER_DAY = 10;
	private Double plannedWork;
	
	public PlannedWeek(String name) {
		super(name);
		this.plannedActivities = new HashMap<Activity,Double>();
		addActivityToWeek(new NonWorkActivity("Holiday"), 0.0);
		addActivityToWeek(new NonWorkActivity("Sickness"), 0.0);
		addActivityToWeek(new NonWorkActivity("Courses"), 0.0);
		addActivityToWeek(new NonWorkActivity("Other"), 0.0);
	}
	
	public void addActivityToWeek(Activity activity, Double hours) {
		plannedActivities.put(activity, hours);
	}
	
	public void addHoursForActivity(Activity activity, Double hours) {
		
		Double registeredHours = plannedActivities.get(activity);
		
		plannedActivities.put(activity, registeredHours+hours);
	}

	public double calculateTotalPlannedHours() {
		
		for (Activity checkActivity : plannedActivities.keySet()) {
			plannedWork += plannedActivities.get(checkActivity);
		}
		return plannedWork;
		
	}
	
	public void setPlannedWork(Double hours) {
		plannedWork = hours;
	}
	
	public Double getPlannedWork() {
		return plannedWork;
	}
	
	

//	@Override
//    public boolean equals(Object obj) {
//        if (obj == this) {
//            return true;
//        }
//  
//        if (!(obj instanceof PlannedWeek)) {
//            return false;
//        }
//          
//        PlannedWeek plannedWeekComparison = (PlannedWeek) obj;
//          
//        return this.getWeek().equals(plannedWeekComparison.getWeek());
//    }

}
