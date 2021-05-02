package dtu.projectManagementApp;

import java.util.Map;
import java.util.HashMap;

public class PlannedWeek extends Week {
	private Map<Activity,Double> plannedActivities;
	public static final double WORKHOURS_PER_DAY = 10.0;
	public static final int DAYS_PER_WEEK = 5;
	public static final double MAX_HOURS_PER_WEEK = 168.0;
	public static final String[] NON_WORK_ACTIVITIES = {"Holiday", "Sickness", "Courses", "Other"};
	
	public PlannedWeek(String name) {
		super(name);
		this.plannedActivities = new HashMap<Activity,Double>();
		
		for (int i = 0; i < NON_WORK_ACTIVITIES.length; i++) {
			addActivityToWeek(new NonWorkActivity(NON_WORK_ACTIVITIES[i]));
		}
	}
	
	public void addActivityToWeek(Activity activity) {
		plannedActivities.put(activity, 0.0);
	}
	
	public void addHoursForActivity(Activity activity, Double hours) {
		if (plannedActivities.get(activity) == null) {
			addActivityToWeek(activity);
		}
		
		
		Double registeredHours = plannedActivities.get(activity);
		
		plannedActivities.put(activity, registeredHours+hours);
	}

	public double calculateTotalPlannedHours() {
		double plannedWork = 0.0;
		for (Activity checkActivity : plannedActivities.keySet()) {
			plannedWork += plannedActivities.get(checkActivity);
		}
		return plannedWork;
	}
	
	public Map<Activity,Double> getPlannedActivities() {
		return plannedActivities;
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
