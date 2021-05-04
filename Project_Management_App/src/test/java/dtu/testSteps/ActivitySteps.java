package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActivitySteps {
	private App projectApp;
	private WorkActivity activity;
	private ProjectHelper projectHelper;
	private ActivityHelper activityHelper;
	private EmployeeHelper employeeHelper;
	private ErrorMessage errorMessage;

	public ActivitySteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,
			ActivityHelper activityHelper, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.activityHelper = activityHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;
	}

	@Given("a WorkActivity with name {string} is assigned to the Project")
	public void a_work_activity_with_name_is_assigned_to_the_project(String name) throws Exception {
			Employee pm = employeeHelper.getAdditionalEmployee();
			String start = activityHelper.getCurrentYearWeek();
			String end = activityHelper.addToYearWeek(1, 0);	
			activity = projectApp.createWorkActivity(projectHelper.getProject().getId(), pm.getInitials(), name, start, end);
	}

	@Given("there is an Employee with initials {string} assigned to the project")
	public void there_is_an_employee_with_initials_assigned_to_the_project(String name) throws Exception {
		Employee pm = employeeHelper.getAdditionalEmployee();
		Employee em = employeeHelper.createEmployee(name);

		projectApp.assignEmployeeToProject(projectHelper.getProject().getId(), pm.getInitials(), em.getInitials());
	}

	@When("the Project Manager assigns the Employee to the Activity")
	public void the_project_manager_assigns_the_employee_to_the_activity() {
		try {
			projectApp.assignEmployeeToActivity(projectHelper.getProject(), activity,
					employeeHelper.getAdditionalEmployee(), employeeHelper.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee is assigned to the Activity")
	public void the_employee_is_assigned_to_the_activity() {
		Employee em = employeeHelper.getEmployee();
		assertTrue(activity.getAssignedEmployees().contains(em));
	}

	@Then("the Employee with initials {string} is not assigned to the Activity")
	public void the_employee_with_initials_is_not_assigned_to_the_activity(String initials) throws Exception {
		assertTrue(employeeHelper.getEmployee().getInitials().equals(initials));
		assertFalse(activity.getAssignedEmployees().contains(employeeHelper.getEmployee()));
	}

	// Feature Plan Project-Related Activities
	@When("the Project Manager creates a WorkActivity with name {string}, start-week {string} and end-week {string}")
	public void the_project_manager_creates_a_work_activity_with_name_start_week_and_end_week(String name,
			String startWeek, String endWeek) throws Exception {
		try {
			Employee pm = employeeHelper.getAdditionalEmployee();
			activity = projectApp.createWorkActivity(projectHelper.getProject().getId(), pm.getInitials(), name, startWeek, endWeek);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@When("the Project Manager sets the expected hours to {double} for the WorkActivity")
	public void the_project_manager_sets_the_expected_hours_to_for_the_work_activity(Double expectedHours) {
		try {
			projectApp.setExpectedHours(projectHelper.getProject().getId(), activity.getName(), expectedHours);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the WorkActivity is assigned to the Project")
	public void the_work_activity_is_assigned_to_the_project() throws Exception {
		assertTrue(projectHelper.getProject().getActivities().contains(activity));
	}

	@Then("the expected hours is {double} for the WorkActivity")
	public void the_expected_hours_is_for_the_work_activity(Double expectedHours) throws Exception {
		assertTrue(activity.getExpectedHours().equals(expectedHours));
	}

	@Then("the WorkActivity is not assigned to the Project")
	public void the_work_activity_is_not_assigned_to_the_project() throws Exception {
		assertFalse(projectHelper.getProject().getActivities().contains(activity));
	}

	@Given("a WorkActivity with name {string}, start-week {string} and end-week {string} is assigned to the Project")
	public void a_work_activity_with_name_start_week_and_end_week_is_assigned_to_the_project(String name, String start,
			String end) throws Exception {
		Project project = projectHelper.getProject();
		Employee pm = employeeHelper.getAdditionalEmployee();
		
		activity = projectApp.createWorkActivity(project.getId(), pm.getInitials(), name, start, end);
		activityHelper.setWorkActivity(activity);
	}

	@Given("the WorkActivity's expected hours is set to {double}")
	public void the_work_activity_s_expected_hours_is_set_to(Double expectedHours) throws Exception {
		projectApp.setExpectedHours(projectHelper.getProject().getId(), activity.getName(), expectedHours);
	}

	@When("the Project Manager edits the Activity to name {string}, start-week {string} and end-week {string}")
	public void the_project_manager_edits_the_activity_to_name_start_week_and_end_week(String name, String start,
			String end) {
		try {
			Employee pm = employeeHelper.getAdditionalEmployee();
			projectApp.editActivity(activity.getName(), projectHelper.getProject().getId(), pm.getInitials(), name, start, end);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the WorkActivity has start-week {string} and end-week {string}")
	public void the_work_activity_has_start_week_and_end_week(String start, String end) {
		assertTrue(activity.getStart().equals(start));
		assertTrue(activity.getEnd().equals(end));
	}

	@Then("the Activity has name {string}, start-week {string} and end-week {string}")
	public void the_activity_has_name_start_week_and_end_week(String name, String start, String end) {
		assertTrue(activity.getName().equals(name));
		assertTrue(activity.getStart().equals(start));
		assertTrue(activity.getEnd().equals(end));
	}

	@Given("the Employee is assigned to the WorkActivity")
	public void the_employee_is_assigned_to_the_work_activity() throws Exception {
		projectApp.assignEmployeeToActivity(projectHelper.getProject(), activity,
				employeeHelper.getAdditionalEmployee(), employeeHelper.getEmployee());
	}

	@When("the Employee is removed from the Activity")
	public void the_employee_is_removed_from_the_activity() throws Exception {

		projectApp.removeEmployeeFromActivity(projectHelper.getProject().getId(), employeeHelper.getEmployee().getInitials(), activity.getName());

	}

	@Then("the Employee is unassigned from the Activity")
	public void the_employee_is_unassigned_from_the_activity() throws Exception {
		assertFalse(activity.getAssignedEmployees().contains(employeeHelper.getEmployee()));
	}

}
