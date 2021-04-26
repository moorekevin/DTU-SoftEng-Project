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
		if (project == null) {
			project = this.createProject("AAAA");
		}
		return project;
	}

	public Project createProject(String name) {
		project = app.createProject(name);
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
