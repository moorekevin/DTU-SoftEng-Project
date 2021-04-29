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

public class TimeAllocationSteps {
	private App projectApp;
	private Project project;
	private WorkActivity activity;
	private ProjectHelper projectHelper;
	private ActivityHelper activityHelper;
	private Employee pm;
	private Employee em;
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

	@Given("the Employee has {double} total hours Planned Work for Week {string}")
	public void the_employee_has_total_hours_planned_work_for_week(Double hours, String yearWeek) {
	    
	}

	@When("the Project Manager allocates {double} hours for the Employee for the WorkActivity for Week {string}")
	public void the_project_manager_allocates_hours_for_the_employee_for_the_work_activity_for_week(Double hours, String yearWeek) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("the Employee has a total of {double} hours Planned Work for Week {string}")
	public void the_employee_has_a_total_of_hours_planned_work_for_week(Double hours, String yearWeek) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("the Employee is assigned only to that Activity")
	public void the_employee_is_assigned_only_to_that_activity() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("the Project Manager views the Employees Time Allocation for week {string}")
	public void the_project_manager_views_the_employees_time_allocation_for_week(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("the Time Allocation for the Employee shows the Employee has {double} total hours Planned Work for Week {string}")
	public void the_time_allocation_for_the_employee_shows_the_employee_has_total_hours_planned_work_for_week(Double double1, String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
}
