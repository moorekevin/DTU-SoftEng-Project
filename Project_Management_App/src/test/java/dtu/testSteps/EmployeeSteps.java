package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSteps {
	private App projectApp;
	private Project project;
	private ProjectHelper projectHelper;
	private EmployeeHelper employeeHelper;
	private Employee pm;
	private Employee em;
	private ErrorMessage errorMessage;

	public EmployeeSteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,
			ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;

		this.project = projectHelper.getProject();
	}

	@Given("there is an Employee with initials {string}")
	public void there_is_an_employee_with_initials(String string) {
		projectApp.addEmployee(string);
		em = projectApp.findEmployee(string);
	}

	@Given("a Project Manager with initials {string} is assigned to a Project")
	public void a_project_manager_with_initials_is_assigned_to_a_project(String name) {
		project = projectApp.createProject(projectHelper.exampleProject().getName());
		projectApp.addEmployee(name);
		pm = projectApp.findEmployee(name);

		projectApp.assignProjectManager(project.getId(), pm.getInitials());

		assertTrue(pm.isProjectManger());
		assertTrue(project.getProjectManger() == pm);
	}

	@When("the Project Manager assigns the Employee to the Project")
	public void the_project_manager_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(project, pm, em);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

	}

	@Then("the Employee is assigned to the Project")
	public void the_employee_is_assigned_to_the_project() {
		assertTrue(project.getAssignedEmployees().contains(em));
	}

	@Given("there is not an Employee with initials {string}")
	public void there_is_not_an_employee_with_initials(String string) {
		em = projectApp.findEmployee(string);
		assertTrue(em == null);
	}

	@Given("a Project Manager with initials {string} is not assigned to the Project")
	public void a_project_manager_with_initials_is_not_assigned_to_the_project(String string) {
		pm = new Employee(string);
		project = projectHelper.getProject();
		assertFalse(pm.getAssignedProjects().contains(project));
	}

	@Then("the Employee is not assigned to the Project")
	public void the_employee_is_not_assigned_to_the_project() {
		assertFalse(project.getAssignedEmployees().contains(em));
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



	@Given("the Employee is not a Project Manager")
	public void the_employee_is_not_a_project_manager() {
		assertFalse(em.isProjectManger());
	}

	@When("the Employee assigns the Employee to the Project")
	public void the_employee_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(project, em, em);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

}
