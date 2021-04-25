package dtu.mvc;

import java.util.HashMap;
import java.util.Map;

public class Controller {
	private UI ui;
	private Model model;

	private boolean isRunning = true;

	private Map<String, CommandInfo> commandList;

	public Controller() {
		setCommands();

		ui = new UI(commandList);
		model = new Model();

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
				ui.deleteProject();
			};})
		);
	}

	public void runUserCommand(String userInput) {
		if (commandList.containsKey(userInput)) {
			commandList.get(userInput).getCommand().runCommand();
		} else {
			ui.print("Unknown command. Type \"/help\" to get a list of all commands.");
		}
	}

	// Commands
	public void createProject() {
		ui.print("What should the name of the project be?");
		String nameOfProject = ui.getUserInput();
		model.createProject(nameOfProject);
	}
}