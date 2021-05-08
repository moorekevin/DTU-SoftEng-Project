package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.testSteps.ErrorMessage;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Indexer;

public class WhiteBox2 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();

	String em = "emInitials";
	String pm = "pmInitials";
	int projectID = 999999;
	String activityName = "coding";
	String yearWeek;
	double hours;

	@Test
	public void testInputDataSetA() throws Exception {
		// There is no Employee em with the given initials
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));	
	}

	@Test
	public void testInputDataSetB() throws Exception {
		// There is no Employee pm with the given initials
		indexer.addEmployee(em);
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));	
	}

	@Test
	public void testInputDataSetC() throws Exception {
		// There is no Employee pm with the given initials
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));	
	}

	@Test
	public void testInputDataSetD() throws Exception {
		// There is no Employee pm with the given initials
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project",projectID);
		indexer.getProjects().add(proj);

		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Activity is not assigned to the project"));	
	}

	@Test
	public void testInputDataSetE() throws Exception {
		// There is no Employee pm with the given initials
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9040", "9050");
		proj.addActivity(act);
		
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee is not assigned to the Project"));	
	}
	
	@Test
	public void testInputDataSetF() throws Exception {
		// There is no Employee pm with the given initials
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9040", "9050");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee is not project manager"));	
	}
	
	@Test
	public void testInputDataSetG() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9040", "9050");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		app.assignProjectManager(projectID, pm);
		yearWeek = "2000";
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Week(s) are invalid"));	
	}
	
	@Test
	public void testInputDataSetH() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		app.assignProjectManager(projectID, pm);
		yearWeek = "9010";
		
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Activity has not begun/ended for planned time"));	
	}
	
	@Test
	public void testInputDataSetI() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9000", "9020");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		app.assignProjectManager(projectID, pm);
		yearWeek = "9010";
		hours = 40.0;
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("The total planned work exceeds allowed hours for the week. Time is allocated but please contact a Project Manager"));	
	}
	
	@Test
	public void testInputDataSetJ() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9000", "9020");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		app.assignProjectManager(projectID, pm);
		yearWeek = "9010";
		hours = 150.0;
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Not enough available time for week"));	
	}
	
	@Test
	public void testInputDataSetK() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project proj = new Project("project", projectID);
		indexer.getProjects().add(proj);
		WorkActivity act = new WorkActivity(activityName, "9000", "9020");
		proj.addActivity(act);
		proj.assignEmployeeToProject(indexer.findEmployee(em));
		app.assignProjectManager(projectID, pm);
		yearWeek = "9010";
		hours = 20.0;
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		System.out.println(error.getErrorMessage());
		assertTrue(error.getErrorMessage().equals(""));	
	}
}