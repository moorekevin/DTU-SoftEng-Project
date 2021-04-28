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
			String start, end;
			WorkActivity temp = activityHelper.createWorkActivity(name);
			start = temp.getStartWeek().getYearWeek();
			end = temp.getEndWeek().getYearWeek();
			activity = projectApp.createWorkActivity(name, start, end);
			projectApp.addActivityToProject(projectHelper.getProject(), activity);
			
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Given("there is an Employee with initials {string} assigned to the project")
	public void there_is_an_employee_with_initials_assigned_to_the_project(String name) throws Exception {
		pm = employeeHelper.getAdditionalEmployee();
		em = employeeHelper.createEmployee(name);
	 
	    try {
			projectApp.assignEmployeeToProject(projectHelper.getProject(), employeeHelper.getAdditionalEmployee(), employeeHelper.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

	}

	@When("the Project Manager assigns the Employee to the Activity")
	public void the_project_manager_assigns_the_employee_to_the_activity() {
		
		try {
			projectApp.assignEmployeeToActivity(projectHelper.getProject(), activity, employeeHelper.getAdditionalEmployee(), employeeHelper.getEmployee());
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
}
