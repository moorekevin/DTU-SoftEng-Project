/*
	Made by Kevin Moore s204462
	This class tests the methods surrounding the employee
*/
package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import dtu.exceptions.EmployeeNotFoundException;

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
	private Employee em;
	private ErrorMessage errorMessage;

	public EmployeeSteps(App projectApp, ProjectHelper ph, EmployeeHelper eh, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.ph = ph;
		this.eh = eh;
		this.errorMessage = errorMessage;

	}
	
	@Given("there is an Employee with initials {string} assigned to the Project")
	public void there_is_an_employee_with_initials_assigned_to_the_project(String initials) throws Exception {
		pm = eh.getAdditionalEmployee();
		em = eh.createEmployee(initials);

		projectApp.assignEmployeeToProject(ph.getProject().getId(), pm.getInitials(), em.getInitials());
	}
	
	@When("there is added an Employee with initials {string}")
	public void there_is_added_an_employee_with_initials(String initials) {
	    try {
			projectApp.getIndexer().addEmployee(initials);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}
	
	@Then("there is {int} Employee\\(s) with initials {string} in the Employee repository")
	public void there_is_employee_s_with_initials_in_the_employee_repository(Integer amount, String initials) {
		Integer counted = 0;
		for (Employee employeeToCheck :projectApp.getIndexer().getEmployees()) {
			if (employeeToCheck.getInitials().equals(initials)){
				counted ++;
			}				
		}
	    assertEquals(counted, amount);
	}
	

	@When("an Employee with initials {string} is removed from the app")
	public void an_employee_with_initials_is_removed_from_the_app(String initials) {
	   try {
		   projectApp.getIndexer().removeEmployee(initials);
	   } catch (Exception e) {
		   errorMessage.setErrorMessage(e.getMessage());
	   }
	}

	@Given("there is an Employee with initials {string}")
	public void there_is_an_employee_with_initials(String name) throws Exception {
		em = eh.createEmployee(name);
	}

	@Given("a Project Manager with initials {string} is assigned to a Project")
	public void a_project_manager_with_initials_is_assigned_to_a_project(String name) throws Exception {
		pm = eh.createAdditionalEmployee(name);
		ph.createProject("Project");

		projectApp.assignProjectManager(ph.getProject().getId(), pm.getInitials());

		assertTrue(pm.isProjectManger());
		assertTrue(ph.getProject().getProjectManager().equals(pm));
	}

	@When("the Project Manager assigns the Employee to the Project")
	public void the_project_manager_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(ph.getProject().getId(), pm.getInitials(), em.getInitials());
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
		em = eh.createNonExistingEmployee(initials);
		assertFalse(projectApp.getIndexer().getEmployees().contains(em));
	}

	@Given("a Project Manager with initials {string} is not assigned to the Project")
	public void a_project_manager_with_initials_is_not_assigned_to_the_project(String initials) throws Exception {
		eh.createAdditionalEmployee(initials);
		eh.makeEmployeeProjectManager(eh.getAdditionalEmployee());
		pm = eh.getAdditionalEmployee();
		assertFalse(ph.getProject().getAssignedEmployees().contains(pm));
	}

	@Then("the Employee is not assigned to the Project")
	public void the_employee_is_not_assigned_to_the_project() throws Exception {
		assertFalse(ph.getProject().getAssignedEmployees().contains(eh.getEmployee()));
	}

	@Given("the Project does not have a Project Manager")
	public void the_project_does_not_have_a_project_manager() {
		assertEquals(ph.getProject().getProjectManager(), null);
	}

	@When("the Employee assigns the Employee as the Project Manager to the Project")
	public void the_employee_assigns_the_employee_as_the_project_manager_to_the_project() {
		try {
			projectApp.assignProjectManager(ph.getProject().getId(), eh.getEmployee().getInitials());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@When("the Project Manager is unassigned from the Project")
	public void the_project_manager_is_unassigned_from_the_project() {
		try {
			projectApp.unassignProjectManager(ph.getProject().getId(), pm.getInitials());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@When("the Employee is removed from the Project")
	public void the_employee_is_removed_from_the_project() throws Exception {
		projectApp.removeEmployeeFromProject(ph.getProject().getId(), eh.getEmployee().getInitials());
	}

	@Given("the Employee is not a Project Manager")
	public void the_employee_is_not_a_project_manager() throws Exception {
		assertFalse(eh.getEmployee().isProjectManger());
	}

	@When("the Employee assigns the Employee to the Project")
	public void the_employee_assigns_the_employee_to_the_project() {
		try {
			projectApp.assignEmployeeToProject(ph.getProject().getId(), eh.getEmployee().getInitials(),
					eh.getEmployee().getInitials());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee is the Project Manager for the Project")
	public void the_employee_is_the_project_manager_for_the_project() throws Exception {
		assertTrue(ph.getProject().getProjectManager().equals(eh.getEmployee()));
	}

}
