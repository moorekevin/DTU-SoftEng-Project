package dtu.testSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectSteps {
	private App projectApp;
	private Project project;
	private ProjectHelper projectHelper;
	private Employee employee;
	private EmployeeHelper employeeHelper;
	private ErrorMessage errorMessage;

	public ProjectSteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,
			ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.projectHelper = projectHelper;
		this.employeeHelper = employeeHelper;
		this.errorMessage = errorMessage;
	}

	@When("the Employee creates a Project with name {string}")
	public void the_employee_creates_a_project_with_name(String name) {
		int size = projectApp.getProjects().size();
		projectApp.createProject(name);
		projectHelper.setProject(name, projectApp.getProjects().get(size).getId());
		project = projectHelper.getProject();
	}

	@Then("the Project is assigned to the list of Projects")
	public void the_project_is_assigned_to_the_list_of_projects() {
		boolean projectAssigned = false;
		for (Project proj: projectApp.getProjects()) {
			if (proj.getId() == project.getId() && proj.getName() == project.getName()) {
				projectAssigned = true;
			}
		}

		assertTrue(projectAssigned);
	}

	@Then("the Project's first two digits in id is the last two digits in this year")
	public void the_project_s_first_two_digits_in_id_is_the_last_two_digits_in_this_year() {

	}

	@Then("the Project has not a Project Manager")
	public void the_project_has_not_a_project_manager() {
		assertEquals(project.getProjectManger(), null);
	}

	@Given("there is a Project with id {int}")
	public void there_is_a_project_with_id(Integer id) {
		projectHelper.setProject("AAAA", id);
		project = projectHelper.getProject();
		projectApp.getProjects().add(project);

	}

	@When("the Employee creates a new Project with id {int}")
	public void the_employee_creates_a_new_project_with_id(Integer id) {
		project = new Project("AAAA", id);
		projectApp.getProjects().add(project);

	}

	@When("the Employee deletes the Project")
	public void the_employee_deletes_the_project() {
		projectApp.deleteProject(project.getId());
	}

	@Then("a Project with id {int} is not in the list of Projects")
	public void a_project_with_id_is_not_in_the_list_of_projects(Integer id) {
		assertFalse(projectApp.getProjects().contains(project));
	}

	@Given("there is not a Project with id {int} in the list of Projects")
	public void there_is_not_a_project_with_id_in_the_list_of_projects(Integer id) {
		projectHelper.setProject("AAAA", id);
		project = projectHelper.getProject();
		assertFalse(projectApp.getProjects().contains(projectHelper.getProject()));
	}

	@Then("the error is thrown {string}")
	public void the_error_is_thrown(String error) {
		assertTrue(error.equals(projectApp.getError()));
	}

}
