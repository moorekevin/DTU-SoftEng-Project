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
	
	@Given("there is an Employee with initials {string} assigned to the WorkActivity")
	public void there_is_an_employee_with_initials_assigned_to_the_work_activity(String name) throws Exception {
	    activity = activityHelper.getWorkActivity();
	    em = employeeHelper.createEmployee(name);
	    pm = employeeHelper.getAdditionalEmployee();
	    activity.getAssignedEmployees().add(em);
	    
	}
	
	@Given("the Employee has {double} total hours Planned Work for Week {string}")
	public void the_employee_has_total_hours_planned_work_for_week(Double hours, String yearWeek) {
	    double check = projectApp.calculatePlannedHours(pm, em, yearWeek);
	    assertTrue(hours == check);
	    
	}

	@When("the Project Manager allocates {double} hours for the Employee for the WorkActivity for Week {string}")
	public void the_project_manager_allocates_hours_for_the_employee_for_the_work_activity_for_week(Double hours, String yearWeek) {
	
		projectApp.allocateTimeForEmployee(pm, em, hours, activity, yearWeek);
	}

	@Then("the Employee has a total of {double} hours Planned Work for Week {string}")
	public void the_employee_has_a_total_of_hours_planned_work_for_week(Double hours, String yearWeek) {
		the_employee_has_total_hours_planned_work_for_week(hours, yearWeek);
	
	}

	
}
