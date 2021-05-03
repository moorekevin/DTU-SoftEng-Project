package dtu.projectManagementApp;

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
		indexer.validateWeekInterval(start, end); indexer.validateProjectManager(pm, project);
		
		
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
		if (start.equals(""))
			start = activity.getStart();
		if (end.equals(""))
			end = activity.getEnd();

		indexer.validateWeekInterval(start, end);
		indexer.validateProjectManager(pm, project);

		// Should only be updated if user typed information
		if (!name.equals(""))
			activity.setName(name);
		activity.setStart(start);
		activity.setEnd(end);
	}

	public void setExpectedHours(WorkActivity activity, double exptectedHours) {
		if (activity != null) {
			activity.setExpectedHours(exptectedHours);
		}
	}

	public void assignEmployeeToActivity(Project project, WorkActivity workActivity, Employee pm, Employee em)
			throws Exception {

		indexer.findEmployee(pm.getInitials()); indexer.findEmployee(em.getInitials()); indexer.findProject(project.getId());
		indexer.validateProjectManager(pm, project); indexer.validateEmployeeAssigned(em, project);
	
		if (!project.getActivities().contains(workActivity))
			throw new Exception("Project does not have the Activity");
		
		workActivity.addEmployee(em);
	}

	public void allocateTimeForEmployee(Employee pm, Employee em, Double hours, Project project, WorkActivity activity,
			String yearWeek) throws Exception {

		indexer.validateEmployeeAssigned(em, project); indexer.validateProjectManager(pm, project);

		if (Integer.parseInt(yearWeek) > Integer.parseInt(activity.getEnd())) {
			throw new Exception("Activity has not begun/is ended for planned time");
		}

		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);
		
		if(plannedWeek == null) {
			plannedWeek = new PlannedWeek(yearWeek);
			plannedWeek.addActivityToWeek(activity);
			em.addPlannedWeek(plannedWeek);
		}
		
		if(plannedWeek.calculateTotalPlannedHours() + hours > PlannedWeek.MAX_HOURS_PER_WEEK)
			throw new Exception("Not enough available time for week");
		
		plannedWeek.addHoursForActivity(activity, hours);

	}

	public double calculatePlannedHours(Employee pm, Employee em, String week) throws Exception {
		indexer.findEmployee(em.getInitials()); indexer.findEmployee(pm.getInitials());

//		if (!pm.isProjectManger())
//			throw new Exception("Employee is not project manager");

		PlannedWeek foundPlannedWeek = indexer.findPlannedWeek(em, week);
		if (foundPlannedWeek == null) {
			return 0.0;
		}
		return foundPlannedWeek.calculateTotalPlannedHours();
	}

	public void assignToNonWorkActivity(Employee em, String activityName, Integer days, String yearWeek)
			throws Exception {

		double weekHours = days * PlannedWeek.WORKHOURS_PER_DAY;
		PlannedWeek plannedWeek = indexer.findPlannedWeek(em, yearWeek);
		if (plannedWeek.calculateTotalPlannedHours() + weekHours > PlannedWeek.WORKHOURS_PER_DAY
				* PlannedWeek.DAYS_PER_WEEK) {
			throw new Exception("WARNING: The total planned work exceeds allowed hours for the week."
					+ " Please contact a Project Manager");
		}
		for (Activity activity : plannedWeek.getPlannedActivities().keySet()) {
			if (activity.getName().equals(activityName)) {
				plannedWeek.addHoursForActivity(activity, weekHours);
			}
		}

	}
}
