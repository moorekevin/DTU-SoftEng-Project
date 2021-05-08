package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Indexer;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.testSteps.ErrorMessage;

public class WhiteBox4 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();

	@Test
	public void testInputDataSetA() throws Exception {
		try {
			app.createWorkActivity(999999, null, null, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));
	}

	@Test
	public void testInputDataSetB() throws Exception {
		Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);
		try {
			app.createWorkActivity(999999, "pmInitials", null, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}

	@Test
	public void testInputDataSetC() throws Exception {
		Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);
		indexer.addEmployee("pmInitials");
		try {
			app.createWorkActivity(999999, "pmInitials", null, "9010", "9001");
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Start week cannot be larger than end week"));
	}

	@Test
	public void testInputDataSetD() throws Exception {
		Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);

    	Project proj = new Project("fakeProject", 123456);
        indexer.getProjects().add(proj);
    		
        indexer.addEmployee("pmInitials");
    	app.assignProjectManager(123456, "pmInitials");

        try {
            app.createWorkActivity(999999, "pmInitials", null, "9001", "9010");
        } catch (Exception e) {
            error.setErrorMessage(e.getMessage());
        }
		assertTrue(error.getErrorMessage().equals("Project Manager is not assigned to the Project"));
    }

    @Test
    public void testInputDataSetE() throws Exception {
        Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);
        indexer.addEmployee("pmInitials");
        app.assignProjectManager(999999, "pmInitials");

        WorkActivity activity = app.createWorkActivity(999999, "pmInitials", "coding", "9001", "9010");
        assertTrue(activity.getName().equals("coding") && activity.getStart().equals("9001") && activity.getEnd().equals("9010") );
        }
	
	@Test
	public void testInputDataSetF() throws Exception {
		Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);
        indexer.addEmployee("pmInitials");
        app.assignProjectManager(999999, "pmInitials");
		app.createWorkActivity(999999, "pmInitials", "coding", "9001", "9010");
		try {
			app.createWorkActivity(999999, "pmInitials", "coding", "9001", "9010");
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("This Activity is already assigned to the Project"));
	}

	@Test
	public void testInputDataSetG() throws Exception {
		Project project = new Project("testProject", 999999);
        indexer.getProjects().add(project);
        indexer.addEmployee("pmInitials");
        app.assignProjectManager(999999, "pmInitials");
		app.createWorkActivity(999999, "pmInitials", "testAcitivity", "9001", "9010");

		WorkActivity activity = app.createWorkActivity(999999, "pmInitials", "coding", "9001", "9010");
        assertTrue(activity.getName().equals("coding") && activity.getStart().equals("9001") && activity.getEnd().equals("9010") );
	}

}


