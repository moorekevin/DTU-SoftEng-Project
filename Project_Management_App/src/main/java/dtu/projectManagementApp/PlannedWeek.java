package dtu.projectManagementApp;

import java.util.Map;
import java.util.HashMap;

public class PlannedWeek extends Week {
	private Map<Activity,Double> plannedActivities;
	private final int NORMAL_WORKHOURS_PER_DAY = 10;
	
	public PlannedWeek(String name) {
		super(name);
		this.plannedActivities = new HashMap<Activity,Double>();
		addActivityToWeek(new NonWorkActivity("Holiday"));
		addActivityToWeek(new NonWorkActivity("Sickness"));
		addActivityToWeek(new NonWorkActivity("Courses"));
		addActivityToWeek(new NonWorkActivity("Other"));
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
