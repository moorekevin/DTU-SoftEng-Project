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

		createDataToTest();
	}

	// Is not part of final product
	// Should be deleted once done testing GUI
	private void createDataToTest() {
		try {
			app.addEmployee("em");
			app.addEmployee("pm");
			app.createProject("Project01");
			app.createProject("Number2");
			app.assignProjectManager(app.findProject(210001), app.findEmployee("pm"));
			app.assignEmployeeToProject(app.findProject(210001), app.findEmployee("pm"), app.findEmployee("em"));
			app.createWorkActivity(app.findProject(210001), app.findEmployee("pm"), "Activity01", "2201", "2210");
		} catch (Exception e) {
			ui.print(e.getMessage());
		}
	}

	public void start() {
		while (isRunning) {
			ui.start();
			runUserCommand(ui.getUserInput());
		}
	}

	private void runUserCommand(String userInput) {
		if (commandList.containsKey(userInput)) {
			commandList.get(userInput).getCommand().runCommand();
		} else {
			ui.print("\nERROR: Unknown command");
		}
	}

	private String getInput() { // Lets the user exit commands
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
			Project project = app.createProject(nameOfProject);
			ui.print("\nSUCCESS: Project \"" + project.getName() + "\" created with ID \"" + project.getId() + "\"!");
		}
	}

	private void deleteProject() {
		Project project = requestProject("\nWhat is the project's ID?");
		if (project != null) {
			try {
				app.deleteProject(project.getId());
			} catch (Exception e) {
				ui.print(e.getMessage());
			}
		}
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

	private void assignEmployee() {
		Employee pm = requestEmployee("\nWhat are the initials of the project manager who is assigning?");
		if (pm != null) { // Continue only if user didn't type /exit
			Employee em = requestEmployee("\nWhat are the initials of the employee who should be assigned?");
			if (em != null) {
				Project project = requestProject("\nWhat is the ID of the project the employee should be assigned to?");
				try {
					app.assignEmployeeToProject(project, pm, em);
					System.out.println("\nSUCCESS: Employee assigned to project!");
				} catch (Exception e) {
					ui.print(e.getMessage());
				}
			}
		}
	}

	private void assignProjectManager() {
		Employee em = requestEmployee("\nWhat are the initials of the employee who should be project manager?");
		if (em != null) {
			Project project = requestProject(
					"\nWhat is the ID of the project the project manager should be assigned to?");
			try {
				app.assignProjectManager(project, em);
				ui.print("\nSUCCESS: Project manager assigned!");
			} catch (Exception e) {
				ui.print(e.getMessage());
			}
		}
	}

	private void createActivity() {
		Employee pm = requestEmployee("\nWhat are the initials of the project manager who is creating the activity?");
		if (pm != null) {
			Project project = requestProject("\nWhat is the ID of the project it should be assigned to?");
			if (project != null) {
				ui.print("\nWhat should the name of the activity be?");
				String name = getInput();
				if (name != null) {
					ui.print(
							"\nWhat week does the activity begin?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
					String startWeek = getInput();
					if (startWeek != null) {
						ui.print(
								"\nWhat week does the activity end?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
						String endWeek = getInput();
						if (endWeek != null) {
							try {
								app.createWorkActivity(project, pm, name, startWeek, endWeek);
								ui.print("\nSUCCESS: Activity created!");
							} catch (Exception e) {
								ui.print(e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private void editActivity() {
		Employee pm = requestEmployee("\nWhat are the initials of the project manager who is editing the activity?");
		if (pm != null) {
			Project project = requestProject("\nWhat is the ID of the project it is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(project, "\nWhat is the activity's name?");
				if (activity != null) {
					ui.print(
							"\nWhat should the new name of the activity be?\n (Leave blank if name shouldn't be changed)");
					String name = getInput();
					if (name != null) {
						ui.print(
								"\nWhat week does the activity begin?\n Enter week on form yyww (leave blank if start week shouldn't be changed)");
						String startWeek = getInput();
						if (startWeek != null) {
							ui.print(
									"\nWhat week does the activity end?\n Enter week on form yyww (leave blank if end week shouldn't be changed)");
							String endWeek = getInput();
							if (endWeek != null) {
								try {
									app.editActivity(activity, project, pm, name, startWeek, endWeek);
									ui.print("\nSUCCESS: Activity information updated!");
								} catch (Exception e) {
									ui.print(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
	}

	private void registerTime() {
	}

	private void viewProjects() {
		ui.printProjects(app.getProjects());
	}

	private void viewEmployees() {
		// TODO use requestproject() somehow

		boolean tryAgain = true;
		while (tryAgain) {
			tryAgain = false;
			ui.print(
					"\nPlease input ID for which project you want to view all employees for.\n TIP: To view all employees for the company just press enter");

			String id = getInput();
			if (id != null) {
				if (id.equals("")) {
					ui.printEmployees(app.getEmployees());
				} else {
					try {
						ui.printEmployees(app.findProject(Integer.parseInt(id)).getAssignedEmployees());
					} catch (NumberFormatException e) {
						ui.print("ERROR: Please enter numbers as Project ID");
						tryAgain = true;
					} catch (Exception e) {
						ui.print(e.getMessage());
						tryAgain = true;
					}
				}
			}
		}
	}

	private void viewActivities() {
		Project project = requestProject("\nWhat is the ID of the project you wish to see the activities for?");
		if (project != null) {
			ui.printActivities(project.getActivities());
		}
	}

	// TODO Reduce redundancy
	private Employee requestEmployee(String msg) {
		boolean tryAgain = true;
		while (tryAgain) {
			tryAgain = false;
			ui.print(msg);

			String initials = getInput();
			if (initials != null) {
				try {
					return app.findEmployee(initials);
				} catch (Exception e) {
					ui.print(e.getMessage());
					tryAgain = true;
				}
			}
		}
		return null;
	}

	private Project requestProject(String msg) {
		boolean tryAgain = true;
		while (tryAgain) {
			tryAgain = false;
			ui.print(msg);

			String userInput = getInput();
			if (userInput != null) {
				try {
					int projectID = Integer.parseInt(userInput);

					try {
						return app.findProject(projectID);
					} catch (Exception e) {
						ui.print(e.getMessage());
						tryAgain = true;
					}
				} catch (NumberFormatException e) {
					ui.print("ERROR: Please enter numbers as Project ID");
					tryAgain = true;
				}
			}
		}
		return null;
	}

	private WorkActivity requestActivity(Project project, String msg) {
		boolean tryAgain = true;
		while (tryAgain) {
			tryAgain = false;
			ui.print(msg);

			String name = getInput();
			if (name != null) {
				try {
					return app.findActivity(project, name);
				} catch (Exception e) {
					ui.print(e.getMessage());
					tryAgain = true;
				}
			}
		}
		return null;
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
				viewProjects();
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
				new CommandInfo("View list of employees for a project or whole company", new Command() {
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

		commandList.put("/editactivity", new CommandInfo("Edit an activity's information", new Command() {
			public void runCommand() {
				editActivity();
			};
		}));

		commandList.put("/viewactivities", new CommandInfo("View list of activities for a project", new Command() {
			public void runCommand() {
				viewActivities();
			};
		}));

		commandList.put("/registertime", new CommandInfo("Register time for an activity", new Command() {
			public void runCommand() {
				registerTime();
			};
		}));
	}
}