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
			app.addEmployee("EM");
			app.addEmployee("PM");
			app.createProject("Project01");
			app.createProject("Number2");
			app.assignProjectManager(app.findProject(210001), app.findEmployee("pm"));
			app.assignEmployeeToProject(app.findProject(210001), app.findEmployee("pm"), app.findEmployee("em"));
			app.createWorkActivity(app.findProject(210001), app.findEmployee("pm"), "act1", "2201", "2210");
			app.allocateTimeForEmployee(app.findEmployee("pm"), app.findEmployee("em"), 12.0, app.findProject(210001),
					app.findActivity(app.findProject(210001), "act1"), "2202");
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
		String methodName = "\n[Create project] ";
		ui.print(methodName, "What should the name of the project be?");
		String nameOfProject = getInput();
		if (nameOfProject != null) {
			Project project = app.createProject(nameOfProject);
			ui.print("\nSUCCESS: Project \"" + project.getName() + "\" created with ID \"" + project.getId() + "\"!");
		}
	}

	private void deleteProject() {
		String methodName = "\n[Delete project] ";
		Project project = requestProject(methodName, "What is the project's ID?");
		if (project != null) {
			try {
				app.deleteProject(project);
				ui.print("\nSUCCESS: Project deleted!");
			} catch (Exception e) {
				ui.print(e.getMessage());
			}
		}
	}

	private void addEmployee() {
		String methodName = "\n[Add employee] ";
		ui.print(methodName, "What are the initials of the employee?");

		String initials = getInput();
		if (initials != null) {
			try {
				app.addEmployee(initials);
				ui.print("\nSUCCESS: Employee \"" + initials.toUpperCase() + "\" created!");
			} catch (Exception e) {
				ui.print(e.getMessage());
			}
		}
	}

	private void assignEmployee() {
		String methodName = "\n[Assign to project] ";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager who is assigning?");
		if (pm != null) { // Continue only if user didn't type /exit
			Employee em = requestEmployee(methodName, "What are the initials of the employee who should be assigned?");
			if (em != null) {
				Project project = requestProject(methodName,
						"What is the ID of the project the employee should be assigned to?");
				if (project != null) {
					try {
						app.assignEmployeeToProject(project, pm, em);
						System.out.println("\nSUCCESS: Employee assigned to project!");
					} catch (Exception e) {
						ui.print(e.getMessage());
					}
				}
			}
		}
	}

	private void assignProjectManager() {
		String methodName = "\n[Assign project manager] ";
		Employee em = requestEmployee(methodName,
				"What are the initials of the employee who should be project manager?");
		if (em != null) {
			Project project = requestProject(methodName,
					"What is the ID of the project the project manager should be assigned to?");
			if (project != null) {
				try {
					app.assignProjectManager(project, em);
					ui.print("\nSUCCESS: Project manager assigned!");
				} catch (Exception e) {
					ui.print(e.getMessage());
				}
			}
		}
	}

	private void createActivity() {
		String methodName = "\n[Create activity] ";
		Employee pm = requestEmployee(methodName,
				"What are the initials of the project manager who is creating the activity?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project it should be assigned to?");
			if (project != null) {
				ui.print(methodName, "What should the name of the activity be?");
				String name = getInput();
				if (name != null) {
					ui.print(methodName,
							"What week does the activity begin?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
					String startWeek = getInput();
					if (startWeek != null) {
						ui.print(methodName
								+ "What week does the activity end?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
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
		String methodName = "\n[Edit Activity] ";
		Employee pm = requestEmployee(methodName,
				"What are the initials of the project manager who is editing the activity?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project it is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(methodName, project, "What is the activity's name?");
				if (activity != null) {
					ui.print(methodName
							+ "What should the new name of the activity be?\n (Leave blank if name shouldn't be changed)");
					String name = getInput();
					if (name != null) {
						ui.print(methodName
								+ "What week does the activity begin?\n Enter week on form yyww (leave blank if start week shouldn't be changed)");
						String startWeek = getInput();
						if (startWeek != null) {
							ui.print(methodName
									+ "What week does the activity end?\n Enter week on form yyww (leave blank if end week shouldn't be changed)");
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

	private void setExpectedHours() {
		String methodName = "\n[Set expected hours] ";
		Project project = requestProject(methodName, "What is the ID of the project the activity is assigned to?");
		if (project != null) {
			WorkActivity activity = requestActivity(methodName, project, "What is the name of the activity?");
			if (activity != null) {
				ui.print(methodName, "How many hours should be assigned?");
				String input = getInput();
				if (input != null) {
					try {
						double hours = Double.parseDouble(input);
						app.setExpectedHours(activity, hours);
						ui.print("\nSUCCESS: Expected hours updated!");
					} catch (NumberFormatException e) {
						ui.print("ERROR: Please enter number of hours as a decimal");
					}
				}
			}
		}
	}

	private void assignEmployeeToActivity() {
		String methodName = "\n[Assign To Activity] ";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager who is assigning?");
		if (pm != null) {
			Employee em = requestEmployee(methodName, "What are the initials of the employee who should be assigned?");
			if (em != null) {
				Project project = requestProject(methodName,
						"What is the ID of the project the activity is assigned to?");
				if (project != null) {
					WorkActivity activity = requestActivity(methodName, project,
							"What is the activity the employee should be assigned to?");
					if (activity != null) {
						try {
							app.assignEmployeeToActivity(project, activity, pm, em);
							System.out.println("\nSUCCESS: Employee assigned to activity!");
						} catch (Exception e) {
							ui.print(e.getMessage());
						}
					}
				}
			}
		}
	}

	private void planTimeAllocation() {
		String methodName = "\n[Time Allocation] ";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager who is planning?");
		if (pm != null) {
			Employee em = requestEmployee(methodName,
					"What are the initials of the employee who's time should be planned?");
			if (em != null) {
				Project project = requestProject(methodName,
						"What is the ID of the project the activity is assigned to?");
				if (project != null) {
					WorkActivity activity = requestActivity(methodName, project,
							"What is the activity the employee's time should be planned to?");
					if (activity != null) {
						ui.print(methodName
								+ "What week should be planned to?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
						String yearWeek = getInput();
						if (yearWeek != null) {
							while (true) {
								ui.print(methodName, "How many hours should be assigned to the employee for the week?");
								String input = getInput();
								if (input == null) {
									break;
								} else {
									try {
										double hours = Double.parseDouble(input);
										app.allocateTimeForEmployee(pm, em, hours, project, activity, yearWeek);
										ui.print("\nSUCCESS: Time allocated!");
										break;
									} catch (NumberFormatException e) {
										ui.print("ERROR: Please enter number of hours as a decimal");
									} catch (Exception e) {
										ui.print(e.getMessage());
										break;
									}
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
	
	private void planNonWorkActivity() {
		String methodName = "\n[Plan non-work activity] ";
		Employee em = requestEmployee(methodName, "What are the initials of the employee who is planning?");
		if (em != null) {
			
		}
	}

	private void viewProjects() {
		ui.printProjects(app.getProjects());
	}

	private void viewEmployees() {
		String methodName = "\n[View employees] ";
		ui.print(methodName
				+ "Please choose one of the following to view a list of all employees:\n [1] For whole company\n [2] For a project\n [3] For an activity");
		String choice = getInput();

		if (choice == null)
			return;

		if (choice.equals("1")) { // For whole company
			ui.printEmployees(app.getEmployees());
		} else if (choice.equals("2")) { // For a project
			Project project = requestProject(methodName,
					"Please input the ID of the project you wish to see assigned employees for");
			if (project != null) {
				ui.printEmployees(project.getAssignedEmployees());
			}
		} else if (choice.equals("3")) { // For an activity
			Project project = requestProject(methodName,
					"Please input the ID of the project the activity is assigned to");
			if (project != null) {
				WorkActivity act = requestActivity(methodName, project, "Please input the name of the activity");
				if (act != null) {
					ui.printEmployees(act.getAssignedEmployees());
				}
			}
		} else {
			ui.print("Invalid input please try again");
		}
	}

	private void viewActivities() {
		String methodName = "\n[View activities] ";
		Project project = requestProject(methodName,
				"What is the ID of the project you wish to see the activities for?");
		if (project != null) {
			ui.printActivities(project.getActivities());
		}
	}
	
	private void viewTimeAllocation() {
		String methodName = "\n[View time allocation] ";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager viewing?");
		if (pm != null) {
			Employee em = requestEmployee(methodName, "What are the initials of the employee who's time allocation should be viewed?");
			if (em != null) {
				ui.print(methodName, "What week do you want to view time allocation for?");
				String week = getInput();
				if (week != null) {
					try {
						
						ui.printTimeAllocation(app.calculatePlannedHours(pm, em, week), app.findPlannedWeek(em, week).getPlannedActivities());
					} catch (Exception e) {
						ui.print(e.getMessage());
					}
				}
			}
		}
	}

	// TODO Reduce redundancy
	private Employee requestEmployee(String methodName, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String initials = getInput();
			if (initials == null) { // If user types "/exit" then stop the method
				break;
			} else {
				try {
					return app.findEmployee(initials);
				} catch (Exception e) {
					ui.print(e.getMessage());
				}
			}
		}
		return null;
	}

	private Project requestProject(String methodName, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String userInput = getInput();
			if (userInput == null) {
				break;
			} else {
				try {
					int projectID = Integer.parseInt(userInput);
					return app.findProject(projectID);
				} catch (NumberFormatException e) {
					ui.print("ERROR: Please enter numbers as Project ID");
				} catch (Exception e) {
					ui.print(e.getMessage());
				}
			}
		}
		return null;
	}

	private WorkActivity requestActivity(String methodName, Project project, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String name = getInput();
			if (name == null) {
				break;
			} else {
				try {
					return app.findActivity(project, name);
				} catch (Exception e) {
					ui.print(e.getMessage());
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
		// Workactivity commands //
		commandList.put("/createactivity", new CommandInfo("Create a new workactivity", new Command() {
			public void runCommand() {
				createActivity();
			};
		}));

		commandList.put("/editactivity", new CommandInfo("Edit a workactivity's information", new Command() {
			public void runCommand() {
				editActivity();
			};
		}));

		commandList.put("/setexpectedhours",
				new CommandInfo("Set an activity's expected amount of work hours", new Command() {
					public void runCommand() {
						setExpectedHours();
					};
				}));

		commandList.put("/assigntoactivity", new CommandInfo("Assign an employee to a workactivity", new Command() {
			public void runCommand() {
				assignEmployeeToActivity();
			};
		}));

		commandList.put("/viewactivities",
				new CommandInfo("View list of all workactivities or for a specific project", new Command() {
					public void runCommand() {
						viewActivities();
					};
				}));

		//////////////////////////////////////////////
		// Time planning related commands
		commandList.put("/registertime", new CommandInfo("Register time for a workactivity", new Command() {
			public void runCommand() {
				registerTime();
			};
		}));
		
		commandList.put("/plannonwork", new CommandInfo("Register time for a non-work activity", new Command() {
			public void runCommand() {
				planNonWorkActivity();
			};
		}));

		commandList.put("/plantimeallocation", new CommandInfo("Plan an employees time allocation", new Command() {
			public void runCommand() {
				planTimeAllocation();
			};
		}));

		commandList.put("/viewtimeallocation", new CommandInfo("View time allocation for employees", new Command() {
			public void runCommand() {
				viewTimeAllocation();
			};
		}));
	}
}