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
		while (isRunning) {
			ui.start();
			runUserCommand(ui.getUserInput());
		}
	}

	public void runUserCommand(String userInput) {
		if (commandList.containsKey(userInput)) {
			commandList.get(userInput).getCommand().runCommand();
		} else {
			ui.print("\nERROR: Unknown command");
		}
	}

	public String getInput() { // Lets the user exit commands
		String userInput = ui.getUserInput();
		if (userInput.equals("/exit")) {
			return null;
		} else {
			return userInput;
		}
	}

	private void createProject() {
		ui.print("\nWhat should the name of the project be?");
		String nameOfProject = getInput();
		if (nameOfProject != null) {
			app.createProject(nameOfProject);
			ui.print("\nSUCCESS: Project \"" + nameOfProject + "\" created!");
		}
	}

	private void deleteProject() {
		ui.print("\nWhat is the project's ID?");

		String userInput = getInput();
		if (userInput != null) {
			try {
				int projectID = Integer.parseInt(userInput);

				try {
					app.deleteProject(projectID);
					ui.print("\nSUCCESS: Project deleted!");
				} catch (Exception e) {
					ui.print("\n ERROR: " + e.getMessage());
					deleteProject();
				}

			} catch (NumberFormatException e) {
				ui.print("\n ERROR: Please enter numbers as Project ID");
				deleteProject();
			}
		}
	}

	private void viewAllProjects() {
		ui.printProjects(app.getProjects());
	}

	private void addEmployee() {
		ui.print("\nWhat are the initials of the employee?");

		String initials = getInput();
		if (initials != null) {
			try {
				app.addEmployee(initials);
				ui.print("\nSUCCESS: Employee \"" + initials + "\" created!");
			} catch (Exception e) {
				ui.print(e.getMessage());
			}
		}
	}

	private void viewEmployees() {
		ui.print("\nPlease input ID for which project you want to view all employees for.\n TIP: To view all employees for the company just press enter");

		String id = getInput();
		if (id.equals("")) {
			ui.printEmployees(app.getEmployees());
		} else {
			try {
				ui.printEmployees(app.findProject(Integer.parseInt(id)).getAssignedEmployees());
			} catch (NumberFormatException e) {
				ui.print("ERROR: Please enter numbers as Project ID\n");
				viewEmployees();
			} catch (NullPointerException e) {
				ui.print("ERROR: Please enter a Project ID that exists\n");
				viewEmployees();
			}
		}
	}

	private void assignEmployee() {
		ui.print("\nWhat are the initials of the employee which should be assigned?");

		String initials = getInput();
		if (initials != null) {
			Employee em = app.findEmployee(initials);
			
			
		}
	}

	private void assignProjectManager() {
	}

	private void createActivity() {
	}

	private void registerTime() {
	}

	public void setCommands() {
		commandList = new HashMap<String, CommandInfo>();

		commandList.put("/help", new CommandInfo("Print all commands available", new Command() {
			public void runCommand() {
				ui.printCommandList();
			};
		}));
		//////////////////////////////////////////////
		// Project commands //
		commandList.put("/createproject", new CommandInfo("Create a new project", new Command() {
			public void runCommand() {
				createProject();
			};
		}));

		commandList.put("/deleteproject", new CommandInfo("Delete an already existing project", new Command() {
			public void runCommand() {
				deleteProject();
			};
		}));

		commandList.put("/viewprojects", new CommandInfo("Show a list of all projects with name and ID", new Command() {
			public void runCommand() {
				viewAllProjects();
			};
		}));
		//////////////////////////////////////////////
		// Employee commands //
		commandList.put("/addemployee", new CommandInfo("Create a new employee in the system", new Command() {
			public void runCommand() {
				addEmployee();
			};
		}));

		commandList.put("/assignemployee", new CommandInfo("Assign an employee to a project", new Command() {
			public void runCommand() {
				assignEmployee();
			};
		}));

		commandList.put("/viewemployees",
				new CommandInfo("View list of employees for project or whole company", new Command() {
					public void runCommand() {
						viewEmployees();
					};
				}));
		//////////////////////////////////////////////
		// Project Manager commands //
		commandList.put("/assignprojectmanager",
				new CommandInfo("Assign an employee as a project manager to a project", new Command() {
					public void runCommand() {
						assignProjectManager();
					};
				}));

		//////////////////////////////////////////////
		// Activity commands //
		commandList.put("/createactivity", new CommandInfo("Create a new activity", new Command() {
			public void runCommand() {
				createActivity();
			};
		}));

		commandList.put("/registertime", new CommandInfo("Register time for an activity", new Command() {
			public void runCommand() {
				registerTime();
			};
		}));
	}
}