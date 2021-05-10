/*
	Made by Jakob Jacobsen s204502
	This class tests the method unassignProjectManager using White Box test 1 from the report
*/
package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.testSteps.ErrorMessage;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.Indexer;

public class WhiteBox1 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();
	int projectId = 123456;
	String pmInitials = "ABC";
	
	@Test
	public void testInputDataSetA()  {
		
		try {
			app.unassignProjectManager(projectId, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));
}
	
	@Test
	public void testInputDataSetB() {
		
		try {
			indexer.getProjects().add(new Project("project", projectId));
			
			app.unassignProjectManager(projectId, pmInitials);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}
	
	@Test
	public void testInputDataSetC() {
		
		try {	
			indexer.getProjects().add(new Project("project", projectId));
			indexer.addEmployee(pmInitials);
			
			//Create a new project and make the employee project manager for that project
			indexer.getProjects().add(new Project("temp", 000000));
			app.assignProjectManager(000000, pmInitials);
			
			app.unassignProjectManager(projectId, pmInitials);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project Manager is not assigned to the Project"));
	}
	
	@Test
	public void testInputDataSetD() {
		try {
			indexer.getProjects().add(new Project("project", projectId));
			indexer.addEmployee(pmInitials);
			app.assignProjectManager(projectId, pmInitials);
			
			app.unassignProjectManager(projectId, pmInitials);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals(""));
	}


}
