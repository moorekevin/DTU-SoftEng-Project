package dtu.testSteps;

import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Activity;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.DateServer;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.Week;

public class ActivityHelper {
	private App app;
	private WorkActivity workActivity;

	public ActivityHelper(App app) {
		this.app = app;
	}

	public WorkActivity getWorkActivity() throws Exception {
		if (workActivity == null) {
			workActivity = createWorkActivity("AAAA");
		}
		return workActivity;
	}


	public void setWorkActivity(WorkActivity activity) {
		workActivity = activity;
	}
	
	
	public String getCurrentWeekYear() {
		DateServer date = new DateServer();
		if (date.getWeek() < 10) {
			return "0" + date.getWeek() + (date.getYear() % 100);
		}
		return "" + date.getWeek() + (date.getYear() % 100);
	}
	
	public String addToWeekYear(int weekPlus, int yearPlus) {
		String weekYear = getCurrentWeekYear();
		int week = Integer.parseInt((weekYear).substring(0,2));
		int year = Integer.parseInt((weekYear).substring(2,4));
		
		if(week+weekPlus < 10) {
			return "0" + (week+weekPlus) + (year+yearPlus);
		}
		return "" + (week+weekPlus) + (year+yearPlus); 
	}
	
	public WorkActivity createWorkActivity(String name) throws Exception {
		String startWeek = getCurrentWeekYear();
		String endWeek = addToWeekYear(5,0);
		workActivity = app.createWorkActivity(name, startWeek, endWeek);
		return workActivity;
	}
	
	public WorkActivity createWorkActivity(String name, String start, String end) throws Exception {
		workActivity = app.createWorkActivity(name, start, end);
		return workActivity;
	}
	
	 
}
