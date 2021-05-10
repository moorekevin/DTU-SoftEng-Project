/*
	Made by Kevin Moore s204462
	This class tests the method allocateTimeForEmployee using White Box test 3 from the report
*/
package dtu.whiteBoxTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.testSteps.ErrorMessage;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;
import dtu.projectManagementApp.Indexer;

public class WhiteBox3 {
	App app = new App();
	Indexer indexer = app.getIndexer();
	ErrorMessage error = new ErrorMessage();

	String em = "ABC";
	String pm = "DEF";
	int projectID = 123456;
	String activityName = "coding";
	String yearWeek;
	double hours;

	@Test
	public void testInputDataSetA() throws Exception {
		try {
			app.allocateTimeForEmployee(null, em, null, 0, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));	
	}

	@Test
	public void testInputDataSetB() throws Exception {
		indexer.addEmployee(em);
		try {
			app.allocateTimeForEmployee(pm, em, null, 0, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee does not exist"));	
	}

	@Test
	public void testInputDataSetC() throws Exception {
		indexer.addEmployee(em);
		
		indexer.addEmployee(pm);
		
		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, null, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Project does not exist"));	
	}

	@Test
	public void testInputDataSetD() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		
		indexer.getProjects().add(new Project("project",projectID));

		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, activityName, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Activity is not assigned to the project"));	
	}

	@Test
	public void testInputDataSetE() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project project = new Project("project", projectID);
		indexer.getProjects().add(project);
		
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		
		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, activityName, null);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Employee is not assigned to the Project"));	
	}
	
	@Test
	public void testInputDataSetF() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		
		act.getAssignedEmployees().add(emFound);
		project.assignEmployeeToProject(emFound);
		yearWeek = "2000";
		
		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		
		assertTrue(error.getErrorMessage().equals("Employee is not project manager"));	
	}
	
	@Test
	public void testInputDataSetG() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		project.assignEmployeeToProject(emFound);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		act.getAssignedEmployees().add(emFound);
		
		app.assignProjectManager(projectID, pm);
		yearWeek = "2000";
		
		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Week(s) are invalid"));	
	}
	
	@Test
	public void testInputDataSetH() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		project.assignEmployeeToProject(emFound);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		act.getAssignedEmployees().add(emFound);
		app.assignProjectManager(projectID, pm);
		
		yearWeek = "9010";
		
		
		try {
			app.allocateTimeForEmployee(pm, em, null, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		assertTrue(error.getErrorMessage().equals("Activity has not begun/ended for planned time"));	
	}
	
	@Test
	public void testInputDataSetI() throws Exception {
		indexer.addEmployee(em);
		indexer.addEmployee(pm);
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		project.assignEmployeeToProject(emFound);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		act.getAssignedEmployees().add(emFound);
		app.assignProjectManager(projectID, pm);
		
		yearWeek = "9003";
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		hours = 40.0;
		
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
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		project.assignEmployeeToProject(emFound);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		app.assignProjectManager(projectID, pm);
		yearWeek = "9003";
		act.getAssignedEmployees().add(emFound);
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		
		hours = 150.0;
		
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
		Project project = new Project("project", projectID);
		Employee emFound = indexer.findEmployee(em);
		indexer.getProjects().add(project);
		project.assignEmployeeToProject(emFound);
		WorkActivity act = new WorkActivity(activityName, "9000", "9005");
		project.addActivity(act);
		act.getAssignedEmployees().add(emFound);
		app.assignProjectManager(projectID, pm);
		yearWeek = "9003";
		app.allocateTimeForEmployee(pm, em, 20.0, projectID, activityName, yearWeek);
		
		hours = 20.0;
		
		try {
			app.allocateTimeForEmployee(pm, em, hours, projectID, activityName, yearWeek);
		} catch (Exception e) {
			error.setErrorMessage(e.getMessage());
		}
		System.out.println(error.getErrorMessage());
		assertTrue(error.getErrorMessage().equals(""));	
		assertTrue(app.calculatePlannedHours(em, yearWeek) == 40);
	}
}