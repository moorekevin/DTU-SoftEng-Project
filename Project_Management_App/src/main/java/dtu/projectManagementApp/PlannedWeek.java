package dtu.projectManagementApp;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dtu.exceptions.OperationNotAllowedException;
import dtu.exceptions.WarningException;

public class PlannedWeek {
	private Map<Activity,Double> plannedActivities;
	private ArrayList<WorkActivity> invalidActivities = new ArrayList<WorkActivity>();
	private String yearWeek;
	public static final double WORKHOURS_PER_DAY = 10.0;
	public static final int DAYS_PER_WEEK = 5;
	public static final double MAX_HOURS_PER_WEEK = 168.0;
	public static final String[] NON_WORK_ACTIVITIES = {"Holiday", "Sickness", "Courses", "Other"};
	
	public PlannedWeek(String yearWeek) {
		this.yearWeek = yearWeek;
		this.plannedActivities = new HashMap<Activity,Double>();
		
		for (int i = 0; i < NON_WORK_ACTIVITIES.length; i++) {
			addActivityToWeek(new NonWorkActivity(NON_WORK_ACTIVITIES[i]));
		}
	}

	public NonWorkActivity findNonWorkActivity(String activityName) throws OperationNotAllowedException{
		for (String nonWorkActivityName : PlannedWeek.NON_WORK_ACTIVITIES) {
			if (nonWorkActivityName.equals(activityName)) { // If nonwork activity found it is returned back as an activity object
				for (Activity activity : plannedActivities.keySet()) {
					if (activity.getName().equals(activityName) && activity instanceof NonWorkActivity) {
						return (NonWorkActivity) activity;
					}
				}
			}
		}
		throw new OperationNotAllowedException("Non-work Activity is not found in Planned Week");
	}
	
	public void addActivityToWeek(Activity activity) {
		plannedActivities.put(activity, 0.0);
	}
	
	public void addHoursForActivity(Activity activity, Double hours) throws Exception {
		if (plannedActivities.get(activity) == null) {
			addActivityToWeek(activity);
		}
		
		Double registeredHours = plannedActivities.get(activity);
		double calculatedTotalHoursBefore = calculateTotalPlannedHours();
		if(calculatedTotalHoursBefore + hours > PlannedWeek.MAX_HOURS_PER_WEEK)
			throw new Exception("Not enough available time for week");

		plannedActivities.put(activity, registeredHours+hours);

		if (calculatedTotalHoursBefore + hours > PlannedWeek.WORKHOURS_PER_DAY * PlannedWeek.DAYS_PER_WEEK) {
			throw new WarningException("The total planned work exceeds allowed hours for the week."
					+ " Time is allocated but please contact a Project Manager");
		}

	}

	public double calculateTotalPlannedHours() {
		double plannedWork = 0.0;
		
		for (Activity checkActivity : plannedActivities.keySet()) {
			boolean activityIntervalValid = true;
			if (checkActivity instanceof WorkActivity) {
				activityIntervalValid = isActivityIntervalValid((WorkActivity) checkActivity);
			}
			if (activityIntervalValid) {
				plannedWork += plannedActivities.get(checkActivity);
			} 
		}
		
		
		clearInvalidActivities();
		
		return plannedWork;
	}

	public void clearInvalidActivities() {
		for(WorkActivity act : invalidActivities) {
			plannedActivities.remove(act);
		}
		invalidActivities.clear();
	}

	// Checks that the activity week interval still contains the plannedweek and otherwise removes it
	public boolean isActivityIntervalValid(WorkActivity checkActivity) { 
		if (Integer.parseInt(checkActivity.getStart()) > Integer.parseInt(yearWeek)
			|| Integer.parseInt(checkActivity.getEnd()) < Integer.parseInt(yearWeek)) {
				invalidActivities.add(checkActivity);
				return false;
		}
		return true;
	}
	
	public Map<Activity,Double> getPlannedActivities() {
		return plannedActivities;
	}
	
	public String getYearWeek() {
		return yearWeek;
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
