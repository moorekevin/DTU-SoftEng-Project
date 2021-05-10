/*
	Made by Bjørn Laursen s204451
	This class controls the activities used in the tests
*/
package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.DateServer;
import dtu.projectManagementApp.WorkActivity;

public class ActivityHelper {
	private App app;
	private WorkActivity workActivity;

	public ActivityHelper(App app) {
		this.app = app;
	}

	public WorkActivity getWorkActivity() {
		return workActivity;
	}

	public void setWorkActivity(WorkActivity activity) {
		workActivity = activity;
	}
	
	
	public String getCurrentYearWeek() {
		DateServer date = new DateServer();
		return (date.getYear() % 100) + "" + date.getWeek() / 10 + "" + date.getWeek() % 10;
	}
	
	//Method used to create an arbitrary week, where the arguments are used to increment the current yearWeek
	public String addToYearWeek(int yearPlus, int weekPlus) {
		String yearWeek = getCurrentYearWeek();
		int week = Integer.parseInt((yearWeek).substring(2, 4));
		int year = Integer.parseInt((yearWeek).substring(0, 2));
		
		return (year+yearPlus) % 100 + "" + (week+weekPlus) / 10 + "" + (week+weekPlus) % 10;
	}
		 
}
