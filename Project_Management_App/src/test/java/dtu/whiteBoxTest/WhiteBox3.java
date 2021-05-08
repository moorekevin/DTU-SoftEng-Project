package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Indexer;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.PlannedWeek;
import dtu.testSteps.ErrorMessage;

public class WhiteBox3 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();

	String emInitials = "EM";
	String yearWeek = "9005";

	@Test
	public void testInputDataSetA() {
		try {
			app.assignToNonWorkActivity("newEmployee", null, 0, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}

	@Test
	public void testInputDataSetB() {
		try {
			indexer.addEmployee(emInitials);
			app.assignToNonWorkActivity(emInitials, null, 8, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Invalid amount of days"));
	}

	@Test
	public void testInputDataSetC() {
		try {
			indexer.addEmployee(emInitials);
			app.assignToNonWorkActivity(emInitials, null, 4, "yearWeek");
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Week(s) are invalid"));
	}

	@Test
	public void testInputDataSetD() {
		try {
			indexer.addEmployee(emInitials);
			app.assignToNonWorkActivity(emInitials, "Activity", 4, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Non-work Activity is not found in Planned Week"));
	}

	@Test
	public void testInputDataSetE() throws Exception {
		try {
			indexer.addEmployee(emInitials);
			Employee em = indexer.findEmployee(emInitials);
			PlannedWeek week = app.checkPlannedWeek(yearWeek, em);
			week.addHoursForActivity(week.findNonWorkActivity("Other"), 160.0);
		} catch (Exception e) {
			try {
				app.assignToNonWorkActivity(emInitials, "Sickness", 4, yearWeek);
			} catch (Exception we) {
				error.setErrorMessage(we.getMessage());
			}
		}
		assertTrue(error.getErrorMessage().equals("Not enough available time for week"));
	}

	@Test
	public void testInputDataSetF() {
		try {
			indexer.addEmployee(emInitials);
			Employee em = indexer.findEmployee(emInitials);
			PlannedWeek week = app.checkPlannedWeek(yearWeek, em);
			week.addHoursForActivity(week.findNonWorkActivity("Other"), 40.0);
			app.assignToNonWorkActivity(emInitials, "Sickness", 4, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals(
				"The total planned work exceeds allowed hours for the week. Time is allocated but please contact a Project Manager"));
	}

	@Test
	public void testInputDataSetG() {
		try {
			indexer.addEmployee(emInitials);
			Employee em = indexer.findEmployee(emInitials);
			PlannedWeek week = app.checkPlannedWeek(yearWeek, em);
			week.addHoursForActivity(week.findNonWorkActivity("Other"), 5.0);
			app.assignToNonWorkActivity(emInitials, "Sickness", 4, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}

		assertTrue(error.getErrorMessage().equals(""));
	}

}
