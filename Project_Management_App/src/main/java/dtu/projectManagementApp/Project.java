/*
	Made by Jakob Jacobsen s204502
	This class creates and represents projects in the program with all project the related functionalities
*/
package dtu.projectManagementApp;

import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Project {
	private String name;
	private int id;
	private List<Employee> assignedEmployees;
	private List<WorkActivity> activities;
	private Employee projectManager;
	private static int projectNum = 1;

	public Project(String name) {
		this.name = name;
		this.id = makeProjectId();
		assignedEmployees = new ArrayList<Employee>();
		activities = new ArrayList<WorkActivity>();
		projectManager = null;
	}

	public Project(String name, int id) {
		this(name);
		this.id = id;
	}
	
	// Creates a unique ID where first two digits are the last two digits of the year
	// and last four digits is the number of the project created
	// e.g. 210001 for the first project created with the year 2100
	public static int makeProjectId() {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String projectIDString = "" + localDate.getYear();
		projectIDString = projectIDString.substring(2, 4) + "" + projectNum / 1000 + "" + projectNum % 1000 / 100 + ""
				+ projectNum % 100 / 10 + "" + projectNum % 10;
		int projectID = Integer.parseInt(projectIDString);

		projectNum++;
		return projectID;
	}

	public Employee getProjectManager() {
		return projectManager;
	}

	public void removeProjectManager() {
		projectManager = null;
	}

	public void assignEmployeeToProject(Employee em) {
		assignedEmployees.add(em);
	}

	public void removeEmployeeFromProject(Employee em) {
		assignedEmployees.remove(em);
	}

	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public List<WorkActivity> getActivities() {
		return activities;
	}

	public void assignProjectManager(Employee em) throws Exception {
		if (projectManager == null) {
			projectManager = em;
		} else {
			throw new Exception("Project already has a Project Manager");
		}
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void addActivity(WorkActivity activity) {
		activities.add(activity);
	}

}
