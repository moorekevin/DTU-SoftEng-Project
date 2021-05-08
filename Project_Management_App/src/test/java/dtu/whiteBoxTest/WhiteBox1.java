package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.testSteps.ErrorMessage;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.Indexer;

public class WhiteBox1 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();
	Project project = new Project("project", 999999);
	Employee employee = new Employee("pmInitials");
	
	@Test
	public void testInputDataSetA()  {
		//
		try {
			app.unassignProjectManager(project.getId(), employee.getInitials());
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));
}
	
	@Test
	public void testInputDataSetB() {
		
		try {
			indexer.addProject(project);
			app.unassignProjectManager(project.getId(), employee.getInitials());
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));
	}
	
	@Test
	public void testInputDataSetC() {
	
	
		try {
			//Create a new project and make the employee project manager for that project
			Project project2 = new Project("project2");
			indexer.addProject(project2);
			indexer.addProject(project);
			indexer.addEmployee(employee.getInitials());
			app.assignProjectManager(project2.getId(), employee.getInitials());
			
			//Unassign the employee from the original project
			app.unassignProjectManager(project.getId(), employee.getInitials());
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project Manager is not assigned to the Project"));
	}
	
	@Test
	public void testInputDataSetD() {
		try {
			indexer.addProject(project);
			indexer.addEmployee(employee.getInitials());
			app.assignProjectManager(project.getId(), employee.getInitials());
			
			app.unassignProjectManager(project.getId(), employee.getInitials());
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals(""));
	}


}
