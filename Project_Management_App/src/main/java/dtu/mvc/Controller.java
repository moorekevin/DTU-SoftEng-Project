package dtu.mvc;

import java.util.Map;

import dtu.exceptions.WarningException;
import dtu.projectManagementApp.*;

public class Controller {
	private UI ui;
	private App app;
	private Indexer indexer;
	private Map<String, CommandInfo> commandList;

	private boolean isRunning = true;

	public Controller(Map<String, CommandInfo> commandList) {
		this.commandList = commandList;

		ui = new UI(this.commandList);
		app = new App();
		indexer = app.getIndexer();

		createDataToTest();
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

	// Is not part of final product
	// Should be deleted once done testing GUI
	private void createDataToTest() {
		try {
			indexer.addEmployee("EM");
			indexer.addEmployee("PM");
			indexer.createProject("Project01");
			indexer.createProject("Number2");
			app.assignProjectManager(indexer.findProject(210001), indexer.findEmployee("pm"));
			app.assignEmployeeToProject(210001, "pm", "em");
			app.createWorkActivity(indexer.findProject(210001), indexer.findEmployee("pm"), "act1", "2201", "2210");
			app.assignEmployeeToActivity(indexer.findProject(210001),
					indexer.findProject(210001).getActivities().get(0), indexer.findEmployee("pm"),
					indexer.findEmployee("em"));
			app.allocateTimeForEmployee(indexer.findEmployee("pm"), indexer.findEmployee("em"), 12.0,
					indexer.findProject(210001), indexer.findActivity(indexer.findProject(210001), "act1"), "2202");
		} catch (Exception e) {
			ui.printError(e.getMessage());
		}
	}

	private int convertToInt(String number) throws NumberFormatException {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Please only input integers");
		}
	}

	public void printCommandList() {
		ui.printCommandList();
	}

	public void createProject() {
		String methodName = "Create project";
		ui.print(methodName, "What should the name of the project be?");
		String nameOfProject = ui.getUserInput();
		if (nameOfProject != null) {
			Project project = indexer.createProject(nameOfProject);
			ui.print("Project \"" + project.getName() + "\" created with ID \"" + project.getId() + "\"!");
			ui.printSuccess(methodName);
		}
	}

	public void deleteProject() {
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

	public void addEmployee() {
		String methodName = "Add employee";
		ui.print(methodName, "What are the initials of the employee?");

		String initials = ui.getUserInput();
		if (initials != null) {
			try {
				indexer.addEmployee(initials);
				ui.printSuccess(methodName);
			} catch (Exception e) {
				ui.printError(e.getMessage());
			}
		}
	}

	public void removeEmployee() {
		String methodName = "Remove employee";
		Employee em = requestEmployee(methodName, "What are the initials of the employee?");
		if (em != null) {
			try {
				indexer.removeEmployee(em);
				ui.printSuccess(methodName);
			} catch (Exception e) {
				ui.printError(methodName);
			}
		}
	}

	public void assignEmployeeToProject() {
		String methodName = "Assign employee to project";
		ui.print(methodName, "What are the initials of the project manager who is assigning?");
		String pmInitials = ui.getUserInput();
		if (pmInitials != null) { // Continue only if user didn't type /exit
			ui.print(methodName, "What are the initials of the employee who should be assigned?");
			String emInitials = ui.getUserInput();
			if (emInitials != null) {
				ui.print(methodName, "What is the ID of the project the employee should be assigned to?");
				String projectID = ui.getUserInput();
				if (projectID != null) {
					try {
						app.assignEmployeeToProject(convertToInt(projectID), pmInitials, emInitials);
						ui.printSuccess(methodName);
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	public void removeEmployeeFromProject() {
		String methodName = "Remove employee from project";
		Employee em = requestEmployee(methodName, "What are the initials of the employee?");
		if (em != null) {
			Project project = requestProject(methodName,
					"What is the ID of the project the project manager should be removed from?");
			if (project != null) {
				try {
					app.removeEmployeeFromProject(project, em);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	public void assignProjectManager() {
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

	public void unassignProjectManager() {
		String methodName = "Remove project manager";
		Employee pm = requestEmployee(methodName, "What are the initials of the project manager?");
		if (pm != null) {
			Project project = requestProject(methodName, "What project should the project manager be unassigned from?");
			if (project != null) {
				try {
					app.unassignProjectManager(project, pm);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	public void createActivity() {
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
						ui.print(methodName,
								"What week does the activity end?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
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

	public void editActivity() {
		String methodName = "Edit Activity";
		Employee pm = requestEmployee(methodName,
				"What are the initials of the project manager who is editing the activity?");
		if (pm != null) {
			Project project = requestProject(methodName, "What is the ID of the project it is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(methodName, project, "What is the activity's name?");
				if (activity != null) {
					ui.print(methodName,
							"What should the new name of the activity be?\n (Leave blank if name shouldn't be changed)");
					String name = ui.getUserInput();
					if (name != null) {
						ui.print(methodName,
								"What week does the activity begin?\n Enter week on form yyww (leave blank if start week shouldn't be changed)");
						String startWeek = ui.getUserInput();
						if (startWeek != null) {
							ui.print(methodName,
									"What week does the activity end?\n Enter week on form yyww (leave blank if end week shouldn't be changed)");
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

	public void setExpectedHours() {
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
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	public void assignEmployeeToActivity() {
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

	public void removeEmployeeFromActivity() {
		String methodName = "Remove employee from activity";
		Employee em = requestEmployee(methodName, "What are the initials of the employee?");
		if (em != null) {
			Project project = requestProject(methodName, "What is the ID of the project the activity is assigned to?");
			if (project != null) {
				WorkActivity activity = requestActivity(methodName, project, "What is the name of the activity?");
				if (activity != null) {
					try {
						app.removeEmployeeFromActivity(em, activity);
						ui.printSuccess(methodName);
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	public void planTimeAllocation() {
		String methodName = "Time Allocation";
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
						ui.print(methodName,
								"What week should be planned to?\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
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
									} catch (WarningException e) {
										ui.printWarning(e.getMessage());
										ui.printSuccess(methodName);
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

	public void planNonWorkActivity() {
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
							} catch (WarningException e) {
								ui.printWarning(e.getMessage());
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

	public void viewProjects() {
		ui.printProjects(indexer.getProjects());
	}

	public void viewEmployees() {
		String methodName = "View employees";
		ui.print(methodName,
				"Please choose one of the following to view a list of all employees:\n [1] For whole company\n [2] For a project\n [3] For an activity");
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

	public void viewActivities() {
		String methodName = "View activities";
		Project project = requestProject(methodName,
				"What is the ID of the project you wish to see the activities for?");
		if (project != null) {
			ui.printActivities(project.getActivities());
		}
	}

	public void viewTimeAllocation() {
		String methodName = "View time allocation";
		Employee em = requestEmployee(methodName,
				"What are the initials of the employee who's time allocation should be viewed?");
		if (em != null) {
			ui.print(methodName, "What week do you want to view time allocation for?");
			String week = ui.getUserInput();
			if (week != null) {
				try {
					ui.printTimeAllocation(app.calculatePlannedHours(em, week),
							indexer.findPlannedWeek(em, week).getPlannedActivities());
				} catch (Exception e) {
					System.out.println(e.getStackTrace()[0].getMethodName());
					ui.printError(e.getMessage());
				}
			}
		}
	}

	// TODO Reduce redundancy
	public Employee requestEmployee(String methodName, String msg) {
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

	public Project requestProject(String methodName, String msg) {
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

	public WorkActivity requestActivity(String methodName, Project project, String msg) {
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
}