package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSteps {
	private App projectApp;
	private ProjectHelper ph;
	private EmployeeHelper eh;
	private Employee pm;
	private ErrorMessage errorMessage;

	public EmployeeSteps(App projectApp, ProjectHelper ph, EmployeeHelper eh, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.ph = ph;
		this.eh = eh;
		this.errorMessage = errorMessage;

	}

	@Given("there is an Employee with initials {string}")
	public void there_is_an_employee_with_initials(String name) throws Exception {
		eh.createEmployee(name);
	}

	@Given("a Project Manager with initials {string} is assigned to a Project")
	public void a_project_manager_with_initials_is_assigned_to_a_project(String name) throws Exception {
		pm = eh.createEmployee(name);

		try {
			projectApp.assignProjectManager(ph.getProject(), pm);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}

		assertTrue(pm.isProjectManger());
		assertTrue(ph.getProject().getProjectManager() == pm);
	}

	@When("the Project Manager assigns the Employee to the Project")
	public void the_project_manager_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(ph.getProject(), pm, eh.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee is assigned to the Project")
	public void the_employee_is_assigned_to_the_project() throws Exception {
		assertTrue(ph.getProject().getAssignedEmployees().contains(eh.getEmployee()));
	}

	@Given("there is not an Employee with initials {string}")
	public void there_is_not_an_employee_with_initials(String initials) {
		eh.setEmployee(new Employee(initials));
		assertTrue(projectApp.findEmployee(initials) == null);
	}

	@Given("a Project Manager with initials {string} is not assigned to the Project")
	public void a_project_manager_with_initials_is_not_assigned_to_the_project(String initials) throws Exception {
		eh.createEmployee(initials);
		eh.makeEmployeeProjectManager();
		pm = eh.getEmployee();
		assertFalse(pm.getAssignedProjects().contains(ph.getProject()));
	}

	@Then("the Employee is not assigned to the Project")
	public void the_employee_is_not_assigned_to_the_project() throws Exception {
		assertFalse(ph.getProject().getAssignedEmployees().contains(eh.getEmployee()));
	}

	@Given("the Project does not have a Project Manager")
	public void the_project_does_not_have_a_project_manager() {
		assertTrue(ph.getProject().getProjectManager() == null);
	}

	@When("the Employee assigns the Employee as the Project Manager to the Project")
	public void the_employee_assigns_the_employee_as_the_project_manager_to_the_project() {
		try {
			projectApp.assignProjectManager(ph.getProject(), eh.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
			;
		}
	}

	@Then("the Project has the Project Manager with initials {string}")
	public void the_project_has_the_project_manager_with_initials(String initials) {
		assertTrue(ph.getProject().getProjectManager().getInitials().equals(initials));
	}

	@When("the Project Manager is removed from the Project")
	public void the_project_manager_is_removed_from_the_project() {
		try {
			projectApp.removeProjectManager(ph.getProject(), pm);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Given("the Employee is not a Project Manager")
	public void the_employee_is_not_a_project_manager() throws Exception {
		assertFalse(eh.getEmployee().isProjectManger());
	}

	@When("the Employee assigns the Employee to the Project")
	public void the_employee_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(ph.getProject(), eh.getEmployee(), eh.getEmployee());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

}
