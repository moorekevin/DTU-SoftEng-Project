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

	// Commands
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

		if (project.getProjectManager() != null)
			throw new Exception("Project already has a Project Manager");
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

	public WorkActivity createWorkActivity(int projectID, String pmInitials, String name, String start, String end)
			throws Exception {
		Project project = indexer.findProject(projectID);
		Employee pm = indexer.findEmployee(pmInitials);

		indexer.validateWeekInterval(start, end);
		indexer.validateProjectManager(pm, project);

		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(name))
				throw new Exception("This Activity is already assigned to the Project");
		}

		WorkActivity activity = new WorkActivity(name, start, end);
		project.addActivity(activity);

		return activity;
	}

	public void editActivity(String activityName, int projectID, String pmInitials, String newName, String startWeek,
			String endWeek) throws Exception {
		Project project = indexer.findProject(projectID);
		Employee pm = indexer.findEmployee(pmInitials);
		indexer.validateProjectManager(pm, project);
		WorkActivity activity = indexer.findActivity(project, activityName);

		// Use old weeks if no information was typed
		if (startWeek.equals("")) {
			startWeek = activity.getStart();
		}
		if (endWeek.equals("")) {
			endWeek = activity.getEnd();
		}

		indexer.validateWeekInterval(startWeek, endWeek);
		indexer.validateProjectManager(pm, project);

		// Should only be updated if user typed information
		if (!newName.equals("")) {
			activity.setName(newName);
		}
		activity.setStart(startWeek);
		activity.setEnd(endWeek);
	}

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

	public void allocateTimeForEmployee(String pmInitials, String emInitials, double hours, int projectID, String activityName,
			String yearWeek) throws Exception {
		Employee pm = indexer.findEmployee(pmInitials);
		Employee em = indexer.findEmployee(emInitials);
		Project project = indexer.findProject(projectID);
		WorkActivity activity = indexer.findActivity(project, activityName);
		
		indexer.validateEmployeeAssigned(em, project);
		indexer.validateProjectManager(pm);
		indexer.validateYearWeek(yearWeek);

		if (Integer.parseInt(yearWeek) > Integer.parseInt(activity.getEnd())
				|| Integer.parseInt(yearWeek) < Integer.parseInt(activity.getStart())) {
			throw new Exception("Activity has not begun/ended for planned time");
		}

		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);

		plannedWeek.addActivityToWeek(activity);
		plannedWeek.addHoursForActivity(activity, hours);
	}

	public double calculatePlannedHours(String emInitials, String week) throws Exception {
		Employee em = indexer.findEmployee(emInitials);

		PlannedWeek foundPlannedWeek = indexer.findPlannedWeek(em, week);

		return foundPlannedWeek.calculateTotalPlannedHours();
	}

	public void assignToNonWorkActivity(String emInitials, String activityName, int days, String yearWeek)
			throws Exception {
		Employee em = indexer.findEmployee(emInitials);
		
		if (days > 7) {
			throw new OperationNotAllowedException("There cannot be more than 7 days in a week");
		}
		if (days < 0) {
			throw new OperationNotAllowedException("Amount of days cannot be less than 0");
		}
		double weekHours = days * PlannedWeek.WORKHOURS_PER_DAY;
		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);

		NonWorkActivity activityFound = plannedWeek.findNonWorkActivity(activityName);
		plannedWeek.addHoursForActivity(activityFound, weekHours);
	}
}