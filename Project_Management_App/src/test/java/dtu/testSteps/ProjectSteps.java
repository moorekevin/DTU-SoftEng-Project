package dtu.testSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.Main;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectSteps {
	private App projectApp;
	private ProjectHelper ph;
	private ErrorMessage errorMessage;

	public ProjectSteps(App projectApp, ProjectHelper ph, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.ph = ph;
		this.errorMessage = errorMessage;
	}
	

	@When("the Employee creates a Project with name {string}")
	public void the_employee_creates_a_project_with_name(String name) {
		ph.createProject(name);
	}

	@Then("the Project is assigned to the list of Projects")
	public void the_project_is_assigned_to_the_list_of_projects() {
		boolean projectAssigned = false;
		Project checkProject = ph.getProject();
		for (Project proj : projectApp.getIndexer().getProjects()) {
			if (proj.getId() == checkProject.getId() && proj.getName().equals(checkProject.getName())) {
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
		assertEquals(ph.getProject().getProjectManager(), null);
	}

	@Given("there is a Project with id {int}")
	public void there_is_a_project_with_id(Integer id) {
		ph.setProject(new Project("AAAA", id));
		projectApp.getIndexer().getProjects().add(ph.getProject());
	}

	@When("the Employee deletes the Project")
	public void the_employee_deletes_the_project() {
		try {
			projectApp.getIndexer().deleteProject(ph.getProject().getId());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("a Project with id {int} is not in the list of Projects")
	public void a_project_with_id_is_not_in_the_list_of_projects(Integer id) {
		assertFalse(projectApp.getIndexer().getProjects().contains(ph.getProject()));
	}

	@Given("there is not a Project with id {int} in the list of Projects")
	public void there_is_not_a_project_with_id_in_the_list_of_projects(Integer id) {
		ph.setProject(new Project("AAAA", id));
		assertFalse(projectApp.getIndexer().getProjects().contains(ph.getProject()));

	}

	@Then("the error is thrown {string}")
	public void the_error_is_thrown(String error) {
		assertTrue(error.equals(errorMessage.getErrorMessage()));
	}

}
