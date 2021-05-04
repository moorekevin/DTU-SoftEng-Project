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
	public void assignEmployeeToProject(Project project, Employee pm, Employee em) throws Exception {
		indexer.findEmployee(pm.getInitials()); indexer.findEmployee(em.getInitials());

		indexer.validateProjectManager(pm, project);
		if (project.getAssignedEmployees().contains(em)) {
			throw new Exception("Employee is already assigned to the project");
		}

		project.assignEmployeeToProject(em);
	}

	public void assignProjectManager(Project project, Employee em) throws Exception {
		if (project.getProjectManager() != null)
			throw new Exception("Project already has a Project Manager");
		em.setProjectManager(project);
		project.assignProjectManager(em);
	}

	public void removeProjectManager(Project project, Employee pm) throws Exception {
		indexer.findProject(project.getId()); indexer.findEmployee(pm.getInitials());
		indexer.validateProjectManager(pm, project);

		pm.removeProjectManager(project);
		project.removeProjectManager();
	}

	public void removeEmployeeFromProject(Project project, Employee em) throws Exception {

		indexer.validateEmployeeAssigned(em, project);
		project.removeEmployeeFromProject(em);

	}

	public void removeEmployeeFromActivity(Employee em, WorkActivity activity) throws Exception{
		if (!activity.getAssignedEmployees().contains(em)) {
		    throw new Exception("Employee is not assigned to the activity");
		}
		activity.removeEmployee(em);
	}

	public WorkActivity createWorkActivity(Project project, Employee pm, String name, String start, String end)
			throws Exception {
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
	
	
	public void editActivity(WorkActivity activity, Project project, Employee pm, String name, String start, String end)
			throws Exception {

		// Use old weeks if no information was typed
		if (start.equals("")) {
			start = activity.getStart();
		}
		if (end.equals("")) {
			end = activity.getEnd();
		}

		indexer.validateWeekInterval(start, end);
		indexer.validateProjectManager(pm, project);

		// Should only be updated if user typed information
		if (!name.equals("")) {
			activity.setName(name);
		}
		activity.setStart(start);
		activity.setEnd(end);
	}

	public void setExpectedHours(WorkActivity activity, double expectedHours) throws Exception {
		if (activity != null) {
			activity.setExpectedHours(expectedHours);
		}
	}

	public void assignEmployeeToActivity(Project project, WorkActivity workActivity, Employee pm, Employee em)
			throws Exception {
		indexer.findEmployee(pm.getInitials()); indexer.findEmployee(em.getInitials()); indexer.findProject(project.getId());
		indexer.findActivity(project, workActivity.getName());
		indexer.validateProjectManager(pm, project); indexer.validateEmployeeAssigned(em, project);
		
		workActivity.addEmployee(em);
	}

	public void allocateTimeForEmployee(Employee pm, Employee em, Double hours, Project project, WorkActivity activity,
			String yearWeek) throws Exception {
		indexer.validateEmployeeAssigned(em, project); indexer.validateProjectManager(pm); indexer.validateYearWeek(yearWeek);
		
		if (Integer.parseInt(yearWeek) > Integer.parseInt(activity.getEnd()) 
			||Integer.parseInt(yearWeek) < Integer.parseInt(activity.getStart())) {
			throw new Exception("Activity has not begun/ended for planned time");
		}
		
		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);
		
		plannedWeek.addActivityToWeek(activity);
		plannedWeek.addHoursForActivity(activity, hours);
	}

	public double calculatePlannedHours(Employee em, String week) throws Exception  {
		indexer.findEmployee(em.getInitials());
		
		PlannedWeek foundPlannedWeek = indexer.findPlannedWeek(em, week);

		return foundPlannedWeek.calculateTotalPlannedHours();
	}

	public void assignToNonWorkActivity(Employee em, String activityName, Integer days, String yearWeek)
			throws Exception {
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