package dtu.testSteps;

import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Activity;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.Week;

public class ActivityHelper {
	private App app;
	private WorkActivity workActivity;

	public ActivityHelper(App app) {
		this.app = app;
	}

	public WorkActivity getWorkActivity() {
		if (workActivity == null) {
			workActivity = exampleWorkActivity();
		}
		return workActivity;
	}

	public WorkActivity exampleWorkActivity() {
//		Week startWeek = new Week(0120);
//		Week endWeek = new Week(0320);
//		workActivity = new WorkActivity("AAAA", startWeek, endWeek);
		return workActivity;
	}

	public void setWorkActivity(String name, String start, String end) {
		Week startWeek = new Week(start);
		Week endWeek = new Week(end);
		workActivity = new WorkActivity(name, startWeek, endWeek);
	}
	
//	public WorkActivity createWorkActivity(String name) {
//		project = app.createProject(name);
//		return project;
//	}
	
	 
}
