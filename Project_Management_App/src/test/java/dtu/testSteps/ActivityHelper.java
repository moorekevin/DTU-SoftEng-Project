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
	
	
	public String getCurrentYearWeek() {
		DateServer date = new DateServer();
		return (date.getYear() % 100) + "" + date.getWeek() / 10 + "" + date.getWeek() % 10;
	}
	
	public String addToYearWeek(int yearPlus, int weekPlus) {
		String yearWeek = getCurrentYearWeek();
		int week = Integer.parseInt((yearWeek).substring(2, 4));
		int year = Integer.parseInt((yearWeek).substring(0, 2));
		
		return (year+yearPlus) % 100 + "" + (week+weekPlus) / 10 + "" + (week+weekPlus) % 10;
	}
	

	public WorkActivity createWorkActivity(String name) throws Exception {
		String startWeek = getCurrentYearWeek();
		String endWeek = addToYearWeek(1, 0);
		workActivity = app.createWorkActivity(name, startWeek, endWeek);
		return workActivity;
	}
	
	public WorkActivity createWorkActivity(String name, String start, String end) throws Exception {
		workActivity = app.createWorkActivity(name, start, end);
		return workActivity;
	}
	
	 
}
