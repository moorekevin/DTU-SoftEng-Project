/*
	Made by Bjørn Laursen s204451
	This class controls the projects used in the tests
*/
package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Project;

public class ProjectHelper {
	private App app;
	private Project project;

	public ProjectHelper(App app) {
		this.app = app;
	}

	public Project getProject() {
		return project;
	}

	public Project createProject(String name) {
		project = app.getIndexer().createProject(name);
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
