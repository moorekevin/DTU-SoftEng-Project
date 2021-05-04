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
			createProject("Example Project");
		}
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
