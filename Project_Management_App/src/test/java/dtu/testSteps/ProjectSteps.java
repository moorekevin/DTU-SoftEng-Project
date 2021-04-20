package dtu.testSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;

import dtu.dto.EmployeeInfo;
import dtu.dto.ProjectInfo;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

public class ProjectSteps {
	private App projectApp;
	private Project project;
	private ProjectHelper projectHelper;
	private EmployeeInfo employee;
	private EmployeeHelper employeeHelper;
	private ErrorMessage errorMessage;

	public ProjectSteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;
	}

	@Given("there is an Employee with initials {string}")
	public void there_is_an_employee_with_initials(String string) {
		employee = new EmployeeInfo(string);
	}

	@When("the Employee creates a Project with name {string}")
	public void the_employee_creates_a_project_with_name(String name) throws Exception {
		project = new Project(name, 0001);
		projectApp.createProject(project);
		System.out.println(projectApp.getProjects().contains(project));
	}

	@Then("the Project is assigned to the list of Projects")
	public void the_project_is_assigned_to_the_list_of_projects() {
		assertTrue(projectApp.getProjects().contains(project));
	}

	@Then("the Project's first two digits in id is the last two digits in this year")
	public void the_project_s_first_two_digits_in_id_is_the_last_two_digits_in_this_year() {

	}

	@Then("the Project has not a Project Manager")
	public void the_project_has_not_a_project_manager() {
		assertEquals(project.getProjectManger(), null);
	}

	@Given("there is a Project with id {int}")
	public void there_is_a_project_with_id(Integer id) throws Exception {
		projectHelper.setProject("AAAA", id);
		project = projectHelper.getProject();
		projectApp.createProject(project);

	}

	@When("the Employee creates a new Project with id {int}")
	public void the_employee_creates_a_new_project_with_id(Integer id) {
		project = new Project("AAAA", id);
		try {
			projectApp.createProject(project);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

	}

	@When("the Employee deletes the Project")
	public void the_employee_deletes_the_project() {
		try {
			projectApp.deleteProject(project);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("a Project with id {int} is not in the list of Projects")
	public void a_project_with_id_is_not_in_the_list_of_projects(Integer id) {
		assertTrue(id == project.getId());
		assertFalse(projectApp.getProjects().contains(project));
	}

	@Given("there is not a Project with id {int} in the list of Projects")
	public void there_is_not_a_project_with_id_in_the_list_of_projects(Integer id) {
		projectHelper.setProject("AAAA", id);
		assertFalse(projectApp.getProjects().contains(projectHelper.getProject()));

	}

	@Then("the error is thrown {string}")
	public void the_error_is_thrown(String error) {
		assertTrue(error.equals(errorMessage.getErrorMessage()));
	}

}
