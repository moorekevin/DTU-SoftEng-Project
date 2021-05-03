package dtu.mvc;

import java.util.HashMap;
import java.util.Map;

import dtu.projectManagementApp.*;

public class Controller {
	private UI ui;
	private App app;
	private Indexer indexer;

	private boolean isRunning = true;

	private Map<String, CommandInfo> commandList;

	public Controller() {
		setCommands();

		ui = new UI(commandList);
		app = new App();
		indexer = app.getIndexer();

		createDataToTest();
	}

	// Is not part of final product
	// Should be deleted once done testing GUI
	private void createDataToTest() {
		try {
			indexer.addEmployee("EM");
			indexer.addEmployee("PM");
			indexer.createProject("Project01");
			indexer.createProject("Number2");
			app.assignProjectManager(indexer.findProject(210001), indexer.findEmployee("pm"));
			app.assignEmployeeToProject(indexer.findProject(210001), indexer.findEmployee("pm"),
					indexer.findEmployee("em"));
			app.createWorkActivity(indexer.findProject(210001), indexer.findEmployee("pm"), "act1", "2201", "2210");
			app.allocateTimeForEmployee(indexer.findEmployee("pm"), indexer.findEmployee("em"), 12.0,
					indexer.findProject(210001), indexer.findActivity(indexer.findProject(210001), "act1"), "2202");
		} catch (Exception e) {
			ui.printError(e.getMessage());
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

	private void createProject() {
		String methodName = "Create project";
		ui.print(methodName, "What should the name of the project be?");
		String nameOfProject = ui.getUserInput();
		if (nameOfProject != null) {
			Project project = indexer.createProject(nameOfProject);
			ui.print("Project \"" + project.getName() + "\" created with ID \"" + project.getId() + "\"!");
			ui.printSuccess(methodName);
		}
	}

	private void deleteProject() {
		String methodName = "Delete project";
		Project project = requestProject(methodName, "What is the project's ID?");
		if (project != null) {
			try {
				indexer.deleteProject(project);
				ui.printSuccess(methodName);
			} catch (Exception e) {
				ui.printError(e.getMessage());
			}
		}
	}

	private void addEmployee() {
		String methodName = "Add employee";
		ui.print(methodName, "What are the initials of the employee?");

		String initials = ui.getUserInput();
		if (initials != null) {
			try {
				indexer.addEmployee(initials);
				ui.print("\n Employee \"" + initials.toUpperCase() + "\" created!");
				ui.printSuccess(methodName);
			} catch (Exception e) {
				ui.printError(e.getMessage());
			}
		}
	}
	
	private void removeEmployee() {
		String methodName = "Remove employee";
		Employee em = requestEmployee(methodName, "What are the initials of the employee?");
		if (em != null) {
			try {
				indexer.removeEmployee(em);
			} catch (Exception e) {
				ui.printError(methodName);
			}
		}
	}

	private void assignEmployeeToProject() {
		String methodName = "Assign employee to project";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager who is assigning?");
		if (pm != null) { // Continue only if user didn't type /exit
			Employee em = requestEmployee(methodName, "What are the initials of the employee who should be assigned?");
			if (em != null) {
				Project project = requestProject(methodName,
						"What is the ID of the project the employee should be assigned to?");
				if (project != null) {
					try {
						app.assignEmployeeToProject(project, pm, em);
						ui.printSuccess(methodName);
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	private void removeEmployeeFromProject() {
		String methodName = "Remove employee from project";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager?");
		if (pm != null) {
			Project project = requestProject(methodName,
					"What is the ID of the project the project manager should be removed from?");
			if (project != null) {
				try {
					app.removeEmployeeFromProject(project, pm);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	private void assignProjectManager() {
		String methodName = "Assign project manager";
		Employee em = requestEmployee(methodName,
				"What are the initials of the employee who should be project manager?");
		if (em != null) {
			Project project = requestProject(methodName,
					"What is the ID of the project the project manager should be assigned to?");
			if (project != null) {
				try {
					app.assignProjectManager(project, em);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	private void removeProjectManager() {
		String methodName = "Remove project manager";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager?");
		if (pm != null) {
			Project project = requestProject(methodName, "What project should the project manager be unassigned from?");
			if (project != null) {
				try {
					app.removeProjectManager(project, pm);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	private void removeEmployeeFromActivity() {
		String methodName = "Remove employee from activity";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project the activity is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(methodName, project, "What is the name of the activity?");
				if (activity != null) {
					try {
						app.removeEmployeeFromActivity(pm, activity);
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	private void createActivity() {
		String methodName = "Create activity";
		Employee pm = requestEmployee(methodName,
				"What are the initials of the project manager who is creating the activity?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project it should be assigned to?");
			if (project != null) {
				ui.print(methodName, "What should the name of the activity be?");
				String name = ui.getUserInput();
				if (name != null) {
					ui.print(methodName,
							"What week does the activity begin?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
					String startWeek = ui.getUserInput();
					if (startWeek != null) {
						ui.print(methodName
								+ "What week does the activity end?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
						String endWeek = ui.getUserInput();
						if (endWeek != null) {
							try {
								app.createWorkActivity(project, pm, name, startWeek, endWeek);
								ui.print("\nActivity \"" + name + "\" created!");
								ui.printSuccess(methodName);
							} catch (Exception e) {
								ui.printError(e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private void editActivity() {
		String methodName = "Edit Activity";
		Employee pm = requestEmployee(methodName,
				"What are the initials of the project manager who is editing the activity?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project it is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(methodName, project, "What is the activity's name?");
				if (activity != null) {
					ui.print(methodName
							+ "What should the new name of the activity be?\n (Leave blank if name shouldn't be changed)");
					String name = ui.getUserInput();
					if (name != null) {
						ui.print(methodName
								+ "What week does the activity begin?\n Enter week on form yyww (leave blank if start week shouldn't be changed)");
						String startWeek = ui.getUserInput();
						if (startWeek != null) {
							ui.print(methodName
									+ "What week does the activity end?\n Enter week on form yyww (leave blank if end week shouldn't be changed)");
							String endWeek = ui.getUserInput();
							if (endWeek != null) {
								try {
									app.editActivity(activity, project, pm, name, startWeek, endWeek);
									ui.printSuccess(methodName);
								} catch (Exception e) {
									ui.printError(e.getMessage());
								}
							}
						}
					}
				}
			}
		}
	}

	private void setExpectedHours() {
		String methodName = "Set expected hours";
		Project project = requestProject(methodName, "What is the ID of the project the activity is assigned to?");
		if (project != null) {
			WorkActivity activity = requestActivity(methodName, project, "What is the name of the activity?");
			if (activity != null) {
				ui.print(methodName, "How many hours should be assigned?");
				String input = ui.getUserInput();
				if (input != null) {
					try {
						double hours = Double.parseDouble(input);
						app.setExpectedHours(activity, hours);
						ui.printSuccess(methodName);
					} catch (NumberFormatException e) {
						ui.printError("Please enter number of hours as a decimal");
					}
				}
			}
		}
	}

	private void assignEmployeeToActivity() {
		String methodName = "Assign To Activity";
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
							ui.printSuccess(methodName);
						} catch (Exception e) {
							ui.printError(e.getMessage());
						}
					}
				}
			}
		}
	}

	private void planTimeAllocation() {
		String methodName = "Time Allocation] ";
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
						String yearWeek = ui.getUserInput();
						if (yearWeek != null) {
							while (true) {
								ui.print(methodName, "How many hours should be assigned to the employee for the week?");
								String input = ui.getUserInput();
								if (input == null) {
									break;
								} else {
									try {
										double hours = Double.parseDouble(input);
										app.allocateTimeForEmployee(pm, em, hours, project, activity, yearWeek);
										ui.printSuccess(methodName);
										break;
									} catch (NumberFormatException e) {
										ui.printError("Please enter number of hours as a decimal");
									} catch (Exception e) {
										ui.printError(e.getMessage());
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

	private void planNonWorkActivity() {
		String methodName = "Plan non-work activity";
		Employee em = requestEmployee(methodName, "What are the initials of the employee who is planning?");
		if (em != null) {
			ui.print(methodName, "What kind of activity should be planned? Choose between:");
			for (int i = 0; i < PlannedWeek.NON_WORK_ACTIVITIES.length; i++) {
				ui.print(" [" + (i + 1) + "] " + PlannedWeek.NON_WORK_ACTIVITIES[i]);
			}
			String activityChoice = ui.getUserInput();
			if (activityChoice != null) {
				String activityName = null;
				try {
					int number = Integer.parseInt(activityChoice);
					activityName = PlannedWeek.NON_WORK_ACTIVITIES[number - 1];
				} catch (Exception e) {
					ui.printError("Please enter a valid choice");
				}

				if (activityName != null) {
					ui.print(methodName,
							"What week should be planned to?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
					String week = ui.getUserInput();
					if (week != null) {
						ui.print(methodName, "How many days is the activity?");
						String input = ui.getUserInput();
						if (input != null) {
							try {
								int days = Integer.parseInt(input);
								app.assignToNonWorkActivity(em, activityName, days, week);
								ui.printSuccess(methodName);
							} catch (NumberFormatException e) {
								ui.printError("Please enter number of days as an integer");
							} catch (Exception e) {
								ui.printError(e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private void viewProjects() {
		ui.printProjects(indexer.getProjects());
	}

	private void viewEmployees() {
		String methodName = "View employees";
		ui.print(methodName
				+ "Please choose one of the following to view a list of all employees:\n [1] For whole company\n [2] For a project\n [3] For an activity");
		String choice = ui.getUserInput();

		if (choice == null)
			return;

		if (choice.equals("1")) { // For whole company
			ui.printEmployees(indexer.getEmployees());
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
		String methodName = "View activities";
		Project project = requestProject(methodName,
				"What is the ID of the project you wish to see the activities for?");
		if (project != null) {
			ui.printActivities(project.getActivities());
		}
	}

	private void viewTimeAllocation() {
		String methodName = "View time allocation";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager viewing?");
		if (pm != null) {
			Employee em = requestEmployee(methodName,
					"What are the initials of the employee who's time allocation should be viewed?");
			if (em != null) {
				ui.print(methodName, "What week do you want to view time allocation for?");
				String week = ui.getUserInput();
				if (week != null) {
					try {

						ui.printTimeAllocation(app.calculatePlannedHours(pm, em, week),
								indexer.findPlannedWeek(em, week).getPlannedActivities());
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	// TODO Reduce redundancy
	private Employee requestEmployee(String methodName, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String initials = ui.getUserInput();
			if (initials == null) { // If user types "/exit" then stop the method
				break;
			} else {
				try {
					return indexer.findEmployee(initials);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
		return null;
	}

	private Project requestProject(String methodName, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String userInput = ui.getUserInput();
			if (userInput == null) {
				break;
			} else {
				try {
					int projectID = Integer.parseInt(userInput);
					return indexer.findProject(projectID);
				} catch (NumberFormatException e) {
					ui.printError("Please enter numbers as Project ID");
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
		return null;
	}

	private WorkActivity requestActivity(String methodName, Project project, String msg) {
		while (true) {
			ui.print(methodName, msg);

			String name = ui.getUserInput();
			if (name == null) {
				break;
			} else {
				try {
					return indexer.findActivity(project, name);
				} catch (Exception e) {
					ui.printError(e.getMessage());
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

		commandList.put("/removeemployee", new CommandInfo("Remove an existing employee from the system", new Command() {
			public void runCommand() {
				removeEmployee();
			};
		}));

		commandList.put("/assigntoproject", new CommandInfo("Assign an employee to a project", new Command() {
			public void runCommand() {
				assignEmployeeToProject();
			};
		}));

		commandList.put("/removefromproject", new CommandInfo("Remove an employee from a project", new Command() {
			public void runCommand() {
				removeEmployeeFromProject();
			};
		}));

		commandList.put("/assigntoactivity", new CommandInfo("Assign an employee to a workactivity", new Command() {
			public void runCommand() {
				assignEmployeeToActivity();
			};
		}));

		commandList.put("/removefromactivity", new CommandInfo("Remove an employee from a workactivity", new Command() {
			public void runCommand() {
				removeEmployeeFromActivity();
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

		commandList.put("/removeprojectmanager",
				new CommandInfo("Remove a project manager from a project", new Command() {
					public void runCommand() {
						removeProjectManager();
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

		commandList.put("/viewactivities",
				new CommandInfo("View list of all workactivities or for a specific project", new Command() {
					public void runCommand() {
						viewActivities();
					};
				}));

		//////////////////////////////////////////////
		// Time planning related commands
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