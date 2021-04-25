package dtu.testSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;

import dtu.dto.EmployeeInfo;
import dtu.dto.ProjectInfo;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

public class EmployeeSteps {
	private App projectApp;
	private Project project;
	private ProjectHelper projectHelper;
	private EmployeeHelper employeeHelper;
	private Employee pm;
	private Employee em;
	private ErrorMessage errorMessage;

	public EmployeeSteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;
	}
	
	@Given("a Project Manager with initials {string} is assigned to a Project")
	public void a_project_manager_with_initials_is_assigned_to_a_project(String string) throws Exception {
		
	    pm = new Employee(string);
	    
	    project = projectHelper.exampleProject();
	    projectApp.createProject(project);
	    projectApp.addEmployee(pm);
	  
	    projectApp.assignProjectManager(project, pm);
	    
	    assertTrue(pm.isProjectManger());
	    assertTrue(project.getProjectManger() == pm);
	}
	
	
	@When("the Project Manager assigns the Employee to the Project")
	public void the_project_manager_assigns_the_employee_to_the_project() throws Exception {
		em = employeeHelper.getEmployee();
	
			projectApp.assignEmployeeToProject(project, pm, em);
		
	  
	}

	@Then("the Employee is assigned to the Project")
	public void the_employee_is_assigned_to_the_project() {
		assertTrue(employeeHelper.getEmployee().getAssignedProjects().contains(project));
		
	}
	
	@Given("there is not an Employee with initials {string}")
	public void there_is_not_an_employee_with_initials(String string) {
		boolean check = false;
		for (Employee emp : projectApp.getEmployees()) {
			if (emp.getInitials() == string) check = true;
		}
		assertFalse(check);
	}
	
	@Given("a Project Manager with initials {string} is not assigned to the Project")
	public void a_project_manager_with_initials_is_not_assigned_to_the_project(String string) {
	    pm = new Employee(string);
	    project = projectHelper.getProject();
	    assertFalse(pm.getAssignedProjects().contains(project));
	}

	@Then("the Employee with initials {string} is not assigned to the Project")
	public void the_employee_with_initials_is_not_assigned_to_the_project(String string) {
		assertTrue(employeeHelper.getEmployee().getInitials().equals(string));
		assertFalse(employeeHelper.getEmployee().getAssignedProjects().contains(project));
	}
	
	@Given("the Project does not have a Project Manager")
	public void the_project_does_not_have_a_project_manager() {
		System.out.println(projectHelper.getProject().getProjectManger() == null);
		assertTrue(projectHelper.getProject().getProjectManger() == null);
	}
	
	@When("the Employee assigns the Employee as the Project Manager to the Project")
	public void the_employee_assigns_the_employee_as_the_project_manager_to_the_project() {
		em = employeeHelper.getEmployee();
		project = projectHelper.getProject();
		
		projectApp.assignEmployeeToProject(project, em, em);
		projectApp.assignProjectManager(project, em);
	}
	
	@Then("the Project has the Project Manager with initials {string}")
	public void the_project_has_the_project_manager_with_initials(String string) {
		assertTrue(project.getProjectManger().getInitials().equals(string));  
	}
	
	@When("the Project Manager is removed from the Project")
	public void the_project_manager_is_removed_from_the_project() {
	   projectApp.removeEmployeeFromProject(project, pm);
	}




	



}
