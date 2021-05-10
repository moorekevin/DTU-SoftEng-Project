/*
	Made by Jakob Jacobsen s204502
	This class controls the employees used in the tests
*/

package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Indexer;
import dtu.projectManagementApp.NonWorkActivity;
import dtu.projectManagementApp.PlannedWeek;
import dtu.projectManagementApp.Project;

public class EmployeeHelper {
	private App app;
	private Employee employee;
	private Employee additionalEmployee;
	private Indexer indexer;

	public EmployeeHelper(App app) {
		this.app = app;
		this.indexer = app.getIndexer();
	}

	public Employee getEmployee() {
		return employee;
	}

	public Employee getAdditionalEmployee() {
		return additionalEmployee;
	}

	public Employee createEmployee(String name) throws Exception {
		indexer.addEmployee(name);
		employee = indexer.findEmployee(name);
		return employee;
	}
	
	//Method used to create and differentiate between a normal employee and an employee who is a project manager
	public Employee createAdditionalEmployee(String name) throws Exception {
		indexer.addEmployee(name);
		additionalEmployee = app.getIndexer().findEmployee(name);
		return additionalEmployee;
	}
	
	//Method used for the scenarios "Given there is not an Employee.."
	public Employee createNonExistingEmployee(String name) {
		employee = new Employee(name);
		return employee;
	}

	public void makeEmployeeProjectManager(Employee em) {
		em.setProjectManager(new Project("v", 9));
	}
	
	//Sets planned time for the nonWorkActivity holiday
	public void setTimeAllocation(Employee em, String yearWeek, Double hours) throws Exception {
		PlannedWeek plannedWeek = em.createPlannedWeek(yearWeek);

		NonWorkActivity testActivity = plannedWeek.findNonWorkActivity(PlannedWeek.NON_WORK_ACTIVITIES[0]);
		plannedWeek.addHoursForActivity(testActivity, hours);
	}
}
