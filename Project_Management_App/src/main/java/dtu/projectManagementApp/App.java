/*
	Made by Aryan Mirzazadeh - s204489
	This class is the main part of our business logic, where we have implemented the different functions of our program
*/
package dtu.projectManagementApp;

import dtu.exceptions.OperationNotAllowedException;

public class App {
	private Indexer indexer;

	public App() {
		indexer = new Indexer();
	}

	public Indexer getIndexer() {
		return indexer;
	}
	
	public void assignEmployeeToProject(int projectID, String pmInitials, String emInitials) throws Exception {
		Employee pm = indexer.findEmployee(pmInitials);
		Employee em = indexer.findEmployee(emInitials);
		Project project = indexer.findProject(projectID);

		indexer.validateProjectManager(pm, project);
		if (project.getAssignedEmployees().contains(em)) {
			throw new Exception("Employee is already assigned to the project");
		}

		project.assignEmployeeToProject(em);
	}

	public void assignProjectManager(int projectID, String emInitials) throws Exception {
		Project project = indexer.findProject(projectID);
		Employee em = indexer.findEmployee(emInitials);
		em.setProjectManager(project);
		project.assignProjectManager(em);
	}

	public void unassignProjectManager(int projectID, String pmInitials) throws Exception {
		Project project = indexer.findProject(projectID);
		Employee pm = indexer.findEmployee(pmInitials);
		indexer.validateProjectManager(pm, project);

		pm.removeProjectManager(project);
		project.removeProjectManager();
	}

	public void removeEmployeeFromProject(int projectID, String emInitials) throws Exception {
		Project project = indexer.findProject(projectID);
		Employee em = indexer.findEmployee(emInitials);

		indexer.validateEmployeeAssigned(em, project);
		project.removeEmployeeFromProject(em);

	}

	public void removeEmployeeFromActivity(int projectID, String emInitials, String activityName) throws Exception {
		Employee em = indexer.findEmployee(emInitials);
		Project project = indexer.findProject(projectID);
		WorkActivity activity = indexer.findActivity(project, activityName);

		if (!activity.getAssignedEmployees().contains(em)) {
			throw new Exception("Employee is not assigned to the activity");
		}
		activity.removeEmployee(em);
	}

	public WorkActivity createWorkActivity(int projectID, String pmInitials, String activityName, String start, String end)
			throws Exception {
		Project project = indexer.findProject(projectID);
		Employee pm = indexer.findEmployee(pmInitials);

		DateServer.validateWeekInterval(start, end);
		indexer.validateProjectManager(pm, project);

		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(activityName))
				throw new Exception("This Activity is already assigned to the Project");
		}

		WorkActivity activity = new WorkActivity(activityName, start, end);
		project.addActivity(activity);

		return activity;
	}

	public void editActivity(String activityName, int projectID, String pmInitials, String newName, String startWeek,
			String endWeek) throws Exception {
		Project project = indexer.findProject(projectID);
		Employee pm = indexer.findEmployee(pmInitials);
		indexer.validateProjectManager(pm, project);
		WorkActivity activity = indexer.findActivity(project, activityName);

		if (startWeek.equals("")) { 
			startWeek = activity.getStart(); // Uses same startweek
		}
		if (endWeek.equals("")) {
			endWeek = activity.getEnd(); // Uses same endweek
		}

		DateServer.validateWeekInterval(startWeek, endWeek);
		indexer.validateProjectManager(pm, project);

		if (!newName.equals("")) {
			activity.setName(newName); // Uses same name
		}
		
		activity.setStart(startWeek);
		activity.setEnd(endWeek);
	}

	// Sets expected hours for a WorkActivity
	public void setExpectedHours(int projectID, String activityName, double expectedHours) throws Exception {
		Project project = indexer.findProject(projectID);
		WorkActivity activity = indexer.findActivity(project, activityName);
		activity.setExpectedHours(expectedHours);
	}

	public void assignEmployeeToActivity(int projectID, String activityName, String pmInitials, String emInitials)
			throws Exception {
		Employee pm = indexer.findEmployee(pmInitials);
		Employee em = indexer.findEmployee(emInitials);
		Project project = indexer.findProject(projectID);
		WorkActivity activity = indexer.findActivity(project, activityName);
		
		indexer.validateProjectManager(pm, project);
		indexer.validateEmployeeAssigned(em, project);

		activity.addEmployee(em);
	}

	public void allocateTimeForEmployee(String pmInitials, String emInitials, Double hours, int projectID, String activityName,
			String yearWeek) throws Exception {
		// Finds and validates the objects given
		Employee pm = indexer.findEmployee(pmInitials);
		Employee em = indexer.findEmployee(emInitials);
		Project project = indexer.findProject(projectID);
		WorkActivity activity = indexer.findActivity(project, activityName);
		indexer.validateEmployeeAssigned(em, project, activity);
		indexer.validateProjectManager(pm, project);
		PlannedWeek plannedWeek = checkPlannedWeek(yearWeek, em);
		
		//Check if the yearWeek is inside the boundaries of the activities start and end yearWeek
		if (Integer.parseInt(yearWeek) > Integer.parseInt(activity.getEnd())
				|| Integer.parseInt(yearWeek) < Integer.parseInt(activity.getStart())) {
			throw new Exception("Activity has not begun/ended for planned time");
		}

		plannedWeek.addHoursForActivity(activity, hours);
	}

	public double calculatePlannedHours(String emInitials, String week) throws Exception {
		Employee em = indexer.findEmployee(emInitials);

		PlannedWeek foundPlannedWeek = indexer.findPlannedWeek(em, week);
		if(foundPlannedWeek == null) {
			return 0.0;
		}

		return foundPlannedWeek.calculateTotalPlannedHours();
	}

	public void assignToNonWorkActivity(String emInitials, String activityName, int days, String yearWeek)
			throws Exception {
		Employee em = indexer.findEmployee(emInitials);
		
		if (days > 7 || days < 0) {
			throw new OperationNotAllowedException("Invalid amount of days");
		}
		
		//Convert the days to hours depending on how many hours a normal work day is set to
		double weekHours = days * PlannedWeek.WORKHOURS_PER_DAY;
		
		PlannedWeek plannedWeek = checkPlannedWeek(yearWeek, em);

		NonWorkActivity activityFound = plannedWeek.findNonWorkActivity(activityName);
		plannedWeek.addHoursForActivity(activityFound, weekHours);
	}

	public PlannedWeek checkPlannedWeek(String yearWeek, Employee em) throws Exception {
		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);
		if(plannedWeek == null) {
			plannedWeek = em.createPlannedWeek(yearWeek);
		}
		return plannedWeek;
	}
}