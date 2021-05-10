/*
	Made by Bjørn Laursen - s204451
	This class tests the method assignToNonWorkActivity using White Box test 4 from the report
*/
package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.exceptions.OperationNotAllowedException;
import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Indexer;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.PlannedWeek;
import dtu.testSteps.ErrorMessage;

public class WhiteBox4 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();

	String emInitials = "ABC";
	String activityName;
	String yearWeek;
	Integer days;

	@Test
	public void testInputDataSetA() {
		try {
			app.assignToNonWorkActivity(emInitials, null, 0, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}

	@Test
	public void testInputDataSetB() throws Exception {
		indexer.addEmployee(emInitials);
		days = 8;
		try {
			app.assignToNonWorkActivity(emInitials, null, days, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Invalid amount of days"));
	}

	@Test
	public void testInputDataSetC() throws Exception {
		indexer.addEmployee(emInitials);

		days = 4;
		yearWeek = "yearWeek";
		try {
			app.assignToNonWorkActivity(emInitials, null, days, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Week(s) are invalid"));
	}

	@Test
	public void testInputDataSetD() throws Exception {
		indexer.addEmployee(emInitials);
		days = 4;

		yearWeek = "9005";
		activityName = "Activity";

		try {
			app.assignToNonWorkActivity(emInitials, activityName, days, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Non-work Activity is not found in Planned Week"));
	}

	@Test
	public void testInputDataSetE() throws Exception {
		indexer.addEmployee(emInitials);
		days = 4;
		yearWeek = "9005";
		
		activityName = "Sickness";
		PlannedWeek week = app.checkPlannedWeek(yearWeek, indexer.findEmployee(emInitials));
		try {
			//The employee has 160 hours planned for week "9005"
			week.addHoursForActivity(week.findNonWorkActivity("Other"), 160.0);
		} catch (Exception e) {
			
			try {
				app.assignToNonWorkActivity(emInitials, activityName, days, yearWeek);
			} catch (Exception we) {
				error.setErrorMessage(we.getMessage());
			}
		}
		assertTrue(error.getErrorMessage().equals("Not enough available time for week"));
	}

	@Test
	public void testInputDataSetF() throws Exception {
		indexer.addEmployee(emInitials);
		days = 4;
		yearWeek = "9005";
		activityName = "Sickness";
		PlannedWeek week = app.checkPlannedWeek(yearWeek, indexer.findEmployee(emInitials));
		week.addHoursForActivity(week.findNonWorkActivity("Other"), 40.0);
		try {
			app.assignToNonWorkActivity(emInitials, activityName, days, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals(
				"The total planned work exceeds allowed hours for the week. Time is allocated but please contact a Project Manager"));
	}

	@Test
	public void testInputDataSetG() throws OperationNotAllowedException, Exception {
		indexer.addEmployee(emInitials);
		days = 4;
		yearWeek = "9005";
		activityName = "Sickness";
		
		PlannedWeek week = app.checkPlannedWeek(yearWeek, indexer.findEmployee(emInitials));
		week.addHoursForActivity(week.findNonWorkActivity("Other"), 5.0);
		
		try {
			app.assignToNonWorkActivity(emInitials, activityName, days, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}

		assertTrue(error.getErrorMessage().equals(""));
	}

}
