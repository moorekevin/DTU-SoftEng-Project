package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Activity;

public class ActivitySteps {
	private App projectApp;
	private Project project;
	private WorkActivity activity;
	private ProjectHelper projectHelper;
	private ActivityHelper activityHelper;
	private Employee pm;
	private Employee em;
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
	public void a_work_activity_with_name_is_assigned_to_the_project(String name) {

		try {
			
			pm = employeeHelper.getAdditionalEmployee();
			String start = activityHelper.getCurrentYearWeek();
			String end = activityHelper.addToYearWeek(1, 0);
//			
			activity = projectApp.createWorkActivity(projectHelper.getProject(), pm, name, start, end);

		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Given("there is an Employee with initials {string} assigned to the project")
	public void there_is_an_employee_with_initials_assigned_to_the_project(String name) throws Exception {
		pm = employeeHelper.getAdditionalEmployee();
		em = employeeHelper.createEmployee(name);

		try {
			projectApp.assignEmployeeToProject(projectHelper.getProject(), pm, em);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

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
			pm = employeeHelper.getAdditionalEmployee();
			activity = projectApp.createWorkActivity(projectHelper.getProject(), pm, name, startWeek, endWeek);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}


	@When("sets the expected hours to {double} for the WorkActivity")
	public void sets_the_expected_hours_to_for_the_work_activity(Double expectedHours) {
		projectApp.setExpectedHours(activity, expectedHours);
	}
	
	@Then("the WorkActivity is assigned to the Project")
	public void the_work_activity_is_assigned_to_the_project() throws Exception {
	    assertTrue(projectHelper.getProject().getActivities().contains(activity));
	}

	@Then("the expected hours is {double} for the WorkActivity")
	public void the_expected_hours_is_for_the_work_activity(Double expectedHours) throws Exception {
		System.out.println(expectedHours);
		System.out.println(activity.getExpectedHours());
		assertTrue(activity.getExpectedHours()==expectedHours);
	}
	
	@Then("the WorkActivity is not assigned to the Project")
	public void the_work_activity_is_not_assigned_to_the_project() throws Exception {
		assertFalse(projectHelper.getProject().getActivities().contains(activity));
	}
	
	@Given("a WorkActivity with name {string}, start-week {string} and end-week {string} is assigned to the Project")
	public void a_work_activity_with_name_start_week_and_end_week_is_assigned_to_the_project(String name, String start, String end) throws Exception {
		project = projectHelper.getProject();
		pm = employeeHelper.getAdditionalEmployee();
		try {
			activity = projectApp.createWorkActivity(project, pm, name, start, end);
			activityHelper.setWorkActivity(activity);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@Given("the WorkActivity's expected hours is set to {double}")
	public void the_work_activity_s_expected_hours_is_set_to(Double expectedHours) {
	    activity.setExpectedHours(expectedHours);
	}
	
	@When("the Project Manager edits the Activity to name {string}, start-week {string} and end-week {string}")
	public void the_project_manager_edits_the_activity_to_name_start_week_and_end_week(String name, String start, String end) {
	    try {
			projectApp.editActivity(activity, projectHelper.getProject(), pm, name, start, end);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("the Activity has name {string}, start-week {string} and end-week {string}")
	public void the_activity_has_name_start_week_and_end_week(String name, String start, String end) {
	   assertTrue(activity.getName().equals(name));
	   assertTrue(activity.getStartWeek().getYearWeek().equals(start));
	   assertTrue(activity.getEndWeek().getYearWeek().equals(end));
	}
	
}
