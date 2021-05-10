/*
/*
	Made by Aryan Mirzazadeh - s204489
	This class tests the method createWorkActivity using White Box test 2 from the report
*/
package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Indexer;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.testSteps.ErrorMessage;

public class WhiteBox2 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();
	int projectId = 123456;
	String pmInitials = "ABC";
	String activityName = "Coding";
	String start;
	String end;

	@Test
	public void testInputDataSetA() throws Exception {
		try {
			app.createWorkActivity(projectId, null, null, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));
	}

	@Test
	public void testInputDataSetB() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));

		try {
			app.createWorkActivity(projectId, pmInitials, null, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}

	@Test
	public void testInputDataSetC() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));

		indexer.addEmployee(pmInitials);
		start = "9010";
		end = "9001";

		try {
			app.createWorkActivity(projectId, pmInitials, null, start, end);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Start week cannot be larger than end week"));
	}

	@Test
	public void testInputDataSetD() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));
		indexer.addEmployee(pmInitials);
		start = "9001";
		end = "9010";

		// Create a new project and make the employee project manager for that project
		indexer.getProjects().add(new Project("temp", 000000));
		app.assignProjectManager(000000, pmInitials);

		try {
			app.createWorkActivity(projectId, pmInitials, null, start, end);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project Manager is not assigned to the Project"));
	}

	@Test
	public void testInputDataSetE() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));
		indexer.addEmployee(pmInitials);
		start = "9001";
		end = "9010";
		app.assignProjectManager(projectId, pmInitials);

		activityName = "coding";
		WorkActivity workActivity = null;
		
		try {
			workActivity = app.createWorkActivity(projectId, pmInitials, activityName, start, end);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		
		assertTrue(error.getErrorMessage().equals(""));
		assertTrue(workActivity.getName().equals(activityName) 
				   && workActivity.getStart().equals(start)
				   && workActivity.getEnd().equals(end));
		assertTrue(indexer.findProject(projectId).getActivities().contains(workActivity));
	}

	@Test
	public void testInputDataSetF() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));
		indexer.addEmployee(pmInitials);
		start = "9001";
		end = "9010";
		app.assignProjectManager(projectId, pmInitials);
		activityName = "coding";

		app.createWorkActivity(projectId, pmInitials, activityName, start, end);

		try {
			app.createWorkActivity(projectId, pmInitials, activityName, start, end);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("This Activity is already assigned to the Project"));
	}

	@Test
	public void testInputDataSetG() throws Exception {
		indexer.getProjects().add(new Project("project", projectId));
		indexer.addEmployee(pmInitials);
		start = "9001";
		end = "9010";
		app.assignProjectManager(projectId, pmInitials);
		activityName = "coding";
		
		WorkActivity workActivity = null;
		app.createWorkActivity(projectId, pmInitials, "programming", start, end);
		try {
			workActivity = app.createWorkActivity(projectId, pmInitials, activityName, start, end);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals(""));
		assertTrue(workActivity.getName().equals(activityName) 
				   && workActivity.getStart().equals(start)
				   && workActivity.getEnd().equals(end));
		assertTrue(indexer.findProject(projectId).getActivities().contains(workActivity));
	}

}
