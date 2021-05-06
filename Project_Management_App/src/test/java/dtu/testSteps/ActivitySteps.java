package dtu.testSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActivitySteps {
	private App projectApp;
	private ProjectHelper ph;
	private ActivityHelper ah;
	private EmployeeHelper eh;
	private ErrorMessage errorMessage;

	public ActivitySteps(App projectApp, ProjectHelper projectHelper, EmployeeHelper employeeHelper,
			ActivityHelper activityHelper, ErrorMessage errorMessage) {
		this.projectApp = projectApp;
		this.ph = projectHelper;
		this.ah = activityHelper;
		this.eh = employeeHelper;
		this.errorMessage = errorMessage;
	}

	@Given("a WorkActivity with name {string} is assigned to the Project")
	public void a_work_activity_with_name_is_assigned_to_the_project(String name) throws Exception {
			Employee pm = eh.getAdditionalEmployee();
			String start = ah.getCurrentYearWeek();
			String end = ah.addToYearWeek(1, 0);	
			Project project = ph.getProject();

			ah.setWorkActivity(projectApp.createWorkActivity(project.getId(), pm.getInitials(), name, start, end));
			assertTrue(ph.getProject().getActivities().contains(ah.getWorkActivity()));
	}
	
	@Given("there is a WorkActivity with name {string} that is not assigned to the Project")
	public void there_is_a_work_activity_with_name_that_is_not_assigned_to_the_project(String activityName) {
		WorkActivity nonAssignedWA = new WorkActivity(activityName, ah.getCurrentYearWeek(), ah.addToYearWeek(1, 0));
	    ah.setWorkActivity(nonAssignedWA);
		assertFalse(ph.getProject().getActivities().contains(nonAssignedWA));
	}


	@When("the Project Manager assigns the Employee to the WorkActivity")
	public void the_project_manager_assigns_the_employee_to_the_work_activity() {
		try {
			projectApp.assignEmployeeToActivity(ph.getProject().getId(), ah.getWorkActivity().getName(),
					eh.getAdditionalEmployee().getInitials(), eh.getEmployee().getInitials());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee is now assigned to the WorkActivity")
	public void the_employee_is_now_assigned_to_the_work_activity() {
		Employee em = eh.getEmployee();
		assertTrue(ah.getWorkActivity().getAssignedEmployees().contains(em));
	}

	@Then("the Employee with initials {string} is not assigned to the WorkActivity")
	public void the_employee_with_initials_is_not_assigned_to_the_work_activity(String initials) throws Exception {
		assertTrue(eh.getEmployee().getInitials().equals(initials));
		assertFalse(ah.getWorkActivity().getAssignedEmployees().contains(eh.getEmployee()));
	}

	// Feature Plan Project-Related Activities
	@When("the Project Manager creates a WorkActivity with name {string}, start-week {string} and end-week {string}")
	public void the_project_manager_creates_a_work_activity_with_name_start_week_and_end_week(String name,
			String startWeek, String endWeek) throws Exception {
		try {
			Employee pm = eh.getAdditionalEmployee();
			ah.setWorkActivity(projectApp.createWorkActivity(ph.getProject().getId(), pm.getInitials(), name, startWeek, endWeek));
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@When("the Project Manager sets the expected hours to {double} for the WorkActivity")
	public void the_project_manager_sets_the_expected_hours_to_for_the_work_activity(Double expectedHours) {
		try {
			projectApp.setExpectedHours(ph.getProject().getId(), ah.getWorkActivity().getName(), expectedHours);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the WorkActivity is assigned to the Project")
	public void the_work_activity_is_assigned_to_the_project() throws Exception {
		assertTrue(ph.getProject().getActivities().contains(ah.getWorkActivity()));
	}

	@Then("the expected hours is {double} for the WorkActivity")
	public void the_expected_hours_is_for_the_work_activity(Double expectedHours) throws Exception {
		assertTrue(ah.getWorkActivity().getExpectedHours().equals(expectedHours));
	}

	@Then("the WorkActivity is not assigned to the Project")
	public void the_work_activity_is_not_assigned_to_the_project() throws Exception {
		assertFalse(ph.getProject().getActivities().contains(ah.getWorkActivity()));
	}

	@Given("a WorkActivity with name {string}, start-week {string} and end-week {string} is assigned to the Project")
	public void a_work_activity_with_name_start_week_and_end_week_is_assigned_to_the_project(String name, String start,
			String end) throws Exception {
		Project project = ph.getProject();
		Employee pm = eh.getAdditionalEmployee();
		
		ah.setWorkActivity(projectApp.createWorkActivity(project.getId(), pm.getInitials(), name, start, end));
		ah.setWorkActivity(ah.getWorkActivity());
	}

	@Given("the WorkActivity's expected hours is set to {double}")
	public void the_work_activity_s_expected_hours_is_set_to(Double expectedHours) throws Exception {
		projectApp.setExpectedHours(ph.getProject().getId(), ah.getWorkActivity().getName(), expectedHours);
	}

	@When("the Project Manager edits the WorkActivity to name {string}, start-week {string} and end-week {string}")
	public void the_project_manager_edits_the_work_activity_to_name_start_week_and_end_week(String name, String start,
			String end) {
		try {
			Employee pm = eh.getAdditionalEmployee();
			projectApp.editActivity(ah.getWorkActivity().getName(), ph.getProject().getId(), pm.getInitials(), name, start, end);
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the WorkActivity has start-week {string} and end-week {string}")
	public void the_work_activity_has_start_week_and_end_week(String start, String end) {
		assertTrue(ah.getWorkActivity().getStart().equals(start));
		assertTrue(ah.getWorkActivity().getEnd().equals(end));
	}

	@Then("the WorkActivity has name {string}, start-week {string} and end-week {string}")
	public void the_work_activity_has_name_start_week_and_end_week(String name, String start, String end) {
		assertTrue(ah.getWorkActivity().getName().equals(name));
		assertTrue(ah.getWorkActivity().getStart().equals(start));
		assertTrue(ah.getWorkActivity().getEnd().equals(end));
	}

	@Given("the Employee is assigned to the WorkActivity")
	public void the_employee_is_assigned_to_the_work_activity() throws Exception {
		projectApp.assignEmployeeToActivity(ph.getProject().getId(), ah.getWorkActivity().getName(),
				eh.getAdditionalEmployee().getInitials(), eh.getEmployee().getInitials());
	}
	
	@Given("the Employee is not assigned to the WorkActivity")
	public void the_employee_is_not_assigned_to_the_work_activity() throws Exception {
		the_employee_is_unassigned_from_the_work_activity();
	}


	@When("the Employee is removed from the WorkActivity")
	public void the_employee_is_removed_from_the_work_activity() {
		try {
			projectApp.removeEmployeeFromActivity(ph.getProject().getId(), eh.getEmployee().getInitials(), ah.getWorkActivity().getName());
		} catch (Exception e) {
			errorMessage.setErrorMessage(e.getMessage());
		}
	}

	@Then("the Employee is unassigned from the WorkActivity")
	public void the_employee_is_unassigned_from_the_work_activity() throws Exception {
		assertFalse(ah.getWorkActivity().getAssignedEmployees().contains(eh.getEmployee()));
	}

}
