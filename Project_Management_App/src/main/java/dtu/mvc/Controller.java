package dtu.mvc;

import java.util.HashMap;
import java.util.Map;

import dtu.projectManagementApp.*;

public class Controller {
	private UI ui;
	private App app;

	private boolean isRunning = true;

	private Map<String, CommandInfo> commandList;

	public Controller() {
		setCommands();

		ui = new UI(commandList);
		app = new App();

		start();
	}

	public void start() {

		ui.start();

		while (isRunning) {
			runUserCommand(ui.getUserInput());
		}
	}

	public void setCommands() {
		commandList = new HashMap<String, CommandInfo>();

		commandList.put("/help", new CommandInfo("Print all commands available", 
			new Command() { public void runCommand() {
				ui.printCommandList();
			};})
		);

		commandList.put("/createproject", new CommandInfo("Create a new project", 
			new Command() { public void runCommand() {
				createProject();
			};})
		);

		commandList.put("/deleteproject", new CommandInfo("Delete an already existing project", 
			new Command() { public void runCommand() {
				deleteProject();
			};})
		);
		
		commandList.put("/viewprojects", new CommandInfo("Show a list of all projects with name and ID", 
			new Command() { public void runCommand() {
				viewAllProjects();
			};})
		);

		commandList.put("/assignemployee", new CommandInfo("Assign an employee to a project", 
			new Command() { public void runCommand() {
				assignEmployee();
			};})
		);

		commandList.put("/assignprojectmanager", new CommandInfo("Assign an employee as a project manager to a project", 
			new Command() { public void runCommand() {
				assignProjectManager();
			};})
		);
	}

	public void runUserCommand(String userInput) {
		if (commandList.containsKey(userInput)) {
			commandList.get(userInput).getCommand().runCommand();
		} else {
			ui.print("\nUnknown command. Type \"/help\" to get a list of all commands.");
		}
	}

	// Commands
	private void createProject() {
		ui.print("\nWhat should the name of the project be?");
		String nameOfProject = ui.getUserInput();
//		Project projectCreated = new Project(nameOfProject, app.makeProjectId());
		app.createProject(nameOfProject);
		ui.print("\nProject created!");
	}
	
	private void deleteProject() {
		ui.print("\nWhat is the project's ID?");
		int projectID = Integer.parseInt(ui.getUserInput());

		app.deleteProject(projectID);
		
		ui.print("\nProject deleted!");
	}
	
	private void viewAllProjects() {
		ui.printProjects(app.getProjects());
	}
	
	private void assignEmployee() {
		
	}

	private void assignProjectManager() {
	}
}