/*
	Made by Jakob Jacobsen s204502
	This class tests the time allocation features
*/
package dtu.testSteps;

import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.WorkActivity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TimeAllocationSteps {
	private App projectApp;
	private WorkActivity activity;
	private ProjectHelper projectHelper;
	private ActivityHelper activityHelper;
	private EmployeeHelper employeeHelper;
	private ErrorMessage errorMessage;

	public TimeAllocationSteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,
			ActivityHelper activityHelper, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.activityHelper = activityHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;
	}

	@Given("there is an Employee with initials {string} assigned to the WorkActivity")
	public void there_is_an_employee_with_initials_assigned_to_the_work_activity(String name) throws Exception {
		activity = activityHelper.getWorkActivity();
		employeeHelper.createEmployee(name);
		Employee pm = employeeHelper.getAdditionalEmployee();

		projectApp.assignEmployeeToProject(projectHelper.getProject().getId(), pm.getInitials(),
				employeeHelper.getEmployee().getInitials());

		projectApp.assignEmployeeToActivity(projectHelper.getProject().getId(),
				activityHelper.getWorkActivity().getName(), employeeHelper.getAdditionalEmployee().getInitials(),
				employeeHelper.getEmployee().getInitials());
	}

	@Given("the Employee has {double} total hours Planned Work for Week {string}")
	public void the_employee_has_total_hours_planned_work_for_week(Double hours, String yearWeek) throws Exception {
		try {
			employeeHelper.setTimeAllocation(employeeHelper.getEmployee(), yearWeek, hours);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

	}

	@When("the Project Manager allocates {double} hours for the Employee for the WorkActivity for Week {string}")
	public void the_project_manager_allocates_hours_for_the_employee_for_the_work_activity_for_week(Double hours,
			String yearWeek) {

		try {
			projectApp.allocateTimeForEmployee(employeeHelper.getAdditionalEmployee().getInitials(),
					employeeHelper.getEmployee().getInitials(), hours, projectHelper.getProject().getId(),
					activity.getName(), yearWeek);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee has a total of {double} hours Planned Work for Week {string}")
	public void the_employee_has_a_total_of_hours_planned_work_for_week(Double hours, String yearWeek)
			throws Exception {
		assertTrue(
				hours.equals(projectApp.calculatePlannedHours(employeeHelper.getEmployee().getInitials(), yearWeek)));
	}

	@When("the Employee assigns {int} days for the NonWorkActivity {string} for Week {string}")
	public void the_employee_assigns_days_for_the_non_work_activity_for_week(Integer days, String name,
			String yearWeek) {
		try {
			projectApp.assignToNonWorkActivity(employeeHelper.getEmployee().getInitials(), name, days, yearWeek);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
}
