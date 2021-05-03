package dtu.testSteps;

import dtu.projectManagementApp.Activity;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.NonWorkActivity;
import dtu.projectManagementApp.PlannedWeek;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Indexer;

public class EmployeeHelper {
	private App app;
	private Employee employee;
	private Employee additionalEmployee;
	private Indexer indexer;

	public EmployeeHelper(App app) {
		this.app = app;
		this.indexer = app.getIndexer();
	}

	public Employee getEmployee() throws Exception {
		if (employee == null) {
			employee = createEmployee("AAAA");
		}
		return employee;
	}

	public Employee getAdditionalEmployee() throws Exception {
		if (additionalEmployee == null) {
			additionalEmployee = createAdditionalEmployee("ABCD");
		}
		return additionalEmployee;
	}

	public Employee createEmployee(String name) throws Exception {
		indexer.addEmployee(name);
		employee = indexer.findEmployee(name);
		return employee;
	}

	// Nødt til at skelne fordi skal bruge begge seperate i senere test
	public Employee createAdditionalEmployee(String name) throws Exception {
		indexer.addEmployee(name);
		additionalEmployee = app.getIndexer().findEmployee(name);
		return additionalEmployee;
	}

	// Tilføjet fordi "given there is not an employee" - kaldte på createEmployee
	// som tilføjer den til repository
	public Employee createNonExistingEmployee(String name) {
		employee = new Employee(name);
		return employee;
	}

	public void makeEmployeeProjectManager(Employee em) {
		em.setProjectManager(new Project("AAAA", 9));
	}

	public void setEmployee(Employee em) {
		employee = em;
	}
	
	public void setPlannedWeek() {
		
	}

	public void setTimeAllocation(Employee em, String yearWeek, Double hours) throws Exception {
		PlannedWeek plannedWeek = new PlannedWeek(yearWeek);
		em.addPlannedWeek(plannedWeek);

		System.out.println(plannedWeek.calculateTotalPlannedHours());

		Activity testActivity = plannedWeek.checkNonWorkActivity(PlannedWeek.NON_WORK_ACTIVITIES[0]);
		plannedWeek.addHoursForActivity(testActivity, hours);
		System.out.println(plannedWeek.calculateTotalPlannedHours());
	}
}
