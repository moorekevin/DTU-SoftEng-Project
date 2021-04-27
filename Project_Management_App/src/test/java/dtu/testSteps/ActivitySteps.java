package dtu.testSteps;

import org.junit.runner.RunWith;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.Activity;

public class ActivitySteps {
	private App projectApp;
	private Project project;
	private ProjectHelper projectHelper;
	private ActivityHelper activityHelper;
	private Employee employee;
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

	@Given("an WorkActivity with name {string} is assigned to the Project")
	public void an_workactivity_with_name_is_assigned_to_the_project(String string) {
		try {
			projectApp.createWorkActivity(string, "1721", "1721");
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Given("there is an Employee with initials {string} assigned to the project")
	public void there_is_an_employee_with_initials_assigned_to_the_project(String string) {
//	    employeeHelper.setEmployee(string);
//	    employee = employeeHelper.getEmployee();

	}

	@When("the Project Manager assigns the Employee to the Activity")
	public void the_project_manager_assigns_the_employee_to_the_activity() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the Employee is assigned to the Activity")
	public void the_employee_is_assigned_to_the_activity() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
}
