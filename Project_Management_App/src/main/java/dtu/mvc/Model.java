package dtu.mvc;

import dtu.projectManagementApp.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class Model {
	App app;
	private static int projectNum;
	Date date; LocalDate localDate;

	public Model() {
		app = new App();
		projectNum = 1;
	}

	public int makeProjectId() {
		date = new Date();
		localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		String projectIDString = "" + localDate.getYear();
		projectIDString = projectIDString.substring(2, 4) + "" + projectNum/1000 + "" + projectNum/100 + "" + projectNum/10 + "" + projectNum/1;
		int projectID = Integer.parseInt(projectIDString);
		
		projectNum++;
		return projectID;
	}
	
	public void createProject(String name) {
		Project projectCreated = new Project(name, makeProjectId());
		app.createProject(projectCreated);
	}
}
