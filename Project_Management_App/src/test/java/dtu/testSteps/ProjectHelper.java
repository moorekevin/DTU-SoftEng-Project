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
			project = exampleProject();
		}
		return project;
	}

	public Project exampleProject() {
		project = new Project("AAAA", 0001);
		return project;
	}

	public void setProject(String name, int id) {
		project = new Project(name, id);
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
