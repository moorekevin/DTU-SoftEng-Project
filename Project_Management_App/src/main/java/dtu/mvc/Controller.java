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
			app.assignProjectManager(210001, "pm");
			app.assignEmployeeToProject(210001, "pm", "em");
			app.createWorkActivity(210001, "pm", "act1", "2201", "2210");
			app.assignEmployeeToActivity(210001, "act1", "pm", "em");
			app.allocateTimeForEmployee("pm", "em", 12.0, 210001, "act1", "2202");
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

	private double convertToDouble(String number) throws NumberFormatException {
		try {
			return Double.parseDouble(number);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Please only input a decimal number");
		}
	}

	public void printCommandList() {
		ui.printCommandList();
	}

	// Request methods //
	public String requestProjectManager(String methodName) {
		ui.print(methodName, "What are the initials of the project manager?");
		return ui.getUserInput();
	}

	public String requestEmployee(String methodName) {
		ui.print(methodName, "What are the initials of the employee?");
		return ui.getUserInput();
	}

	public String requestProject(String methodName) {
		ui.print(methodName, "What is the ID of the project?");
		return ui.getUserInput();
	}

	public String requestActivity(String methodName) {
		ui.print(methodName, "What is the name of the activity?");
		return ui.getUserInput();
	}

	public String requestWeek() {
		ui.print("\n Enter week on form yyww (e.g. 2101 for first week of 2021)");
		return ui.getUserInput();
	}
	//////////////////////

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
		String project = requestProject(methodName);
		if (project != null) {
			try {
				indexer.deleteProject(convertToInt(project));
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
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			try {
				indexer.removeEmployee(emInitials);
				ui.printSuccess(methodName);
			} catch (Exception e) {
				ui.printError(methodName);
			}
		}
	}

	public void assignEmployeeToProject() {
		String methodName = "Assign employee to project";
		String pmInitials = requestProjectManager(methodName);
		if (pmInitials != null) { // Continue only if user didn't type /exit
			String emInitials = requestEmployee(methodName);
			if (emInitials != null) {
				String projectID = requestProject(methodName);
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
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			String projectID = requestProject(methodName);
			if (projectID != null) {
				try {
					app.removeEmployeeFromProject(convertToInt(projectID), emInitials);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	public void assignProjectManager() {
		String methodName = "Assign project manager";
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			String projectID = requestProject(methodName);
			if (projectID != null) {
				try {
					app.assignProjectManager(convertToInt(projectID), emInitials);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	public void unassignProjectManager() {
		String methodName = "Remove project manager";
		String pmInitials = requestProjectManager(methodName);
		if (pmInitials != null) {
			String projectID = requestProject(methodName);
			if (projectID != null) {
				try {
					app.unassignProjectManager(convertToInt(projectID), pmInitials);
					ui.printSuccess(methodName);
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		}
	}

	public void createActivity() {
		String methodName = "Create activity";
		String pmInitials = requestProjectManager(methodName);
		if (pmInitials != null) {
			String projectID = requestProject(methodName);
			if (projectID != null) {
				String activityName = requestActivity(methodName);
				if (activityName != null) {
					ui.print(methodName, "What week does the activity begin?");
					String startWeek = requestWeek();
					if (startWeek != null) {
						ui.print(methodName, "What week does the activity end?");
						String endWeek = requestWeek();
						if (endWeek != null) {
							try {
								app.createWorkActivity(convertToInt(projectID), pmInitials, activityName, startWeek,
										endWeek);
								ui.print("\nActivity \"" + activityName + "\" created!");
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
		String pmInitials = requestProjectManager(methodName);
		if (pmInitials != null) {
			String project = requestProject(methodName);
			if (project != null) {
				String activity = requestActivity(methodName);
				if (activity != null) {
					ui.print(methodName,
							"What should the new name of the activity be?\n (Leave blank if name shouldn't be changed)");
					String activityName = ui.getUserInput();
					if (activityName != null) {
						ui.print(methodName,
								"What week does the activity begin? (Leave blank if start week should not be changed)");
						String startWeek = requestWeek();
						if (startWeek != null) {
							ui.print(methodName,
									"What week does the activity end? (Leave blank if end week should not be changed)");
							String endWeek = requestWeek();
							if (endWeek != null) {
								try {
									app.editActivity(activity, convertToInt(project), pmInitials, activityName,
											startWeek, endWeek);
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
		String projectID = requestProject(methodName);
		if (projectID != null) {
			String activityName = requestActivity(methodName);
			if (activityName != null) {
				ui.print(methodName, "How many hours should be assigned?");
				String hours = ui.getUserInput();
				if (hours != null) {
					try {
						app.setExpectedHours(convertToInt(projectID), activityName, convertToDouble(hours));
						ui.printSuccess(methodName);
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		}
	}

	public void assignEmployeeToActivity() {
		String methodName = "Assign To Activity";
		String pmInitials = requestProjectManager(methodName);
		if (pmInitials != null) {
			String emInitials = requestEmployee(methodName);
			if (emInitials != null) {
				String projectID = requestProject(methodName);
				if (projectID != null) {
					String activityName = requestActivity(methodName);
					if (activityName != null) {
						try {
							app.assignEmployeeToActivity(convertToInt(projectID), activityName, pmInitials, emInitials);
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
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			String projectID = requestProject(methodName);
			if (projectID != null) {
				String activityName = requestActivity(methodName);
				if (activityName != null) {
					try {
						app.removeEmployeeFromActivity(convertToInt(projectID), emInitials, activityName);
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
		String pmInitials = requestEmployee(methodName);
		if (pmInitials != null) {
			String emInitials = requestEmployee(methodName);
			if (emInitials != null) {
				String projectID = requestProject(methodName);
				if (projectID != null) {
					String activityName = requestActivity(methodName);
					if (activityName != null) {
						ui.print(methodName, "What week should be planned to?");
						String yearWeek = requestWeek();
						if (yearWeek != null) {
							ui.print(methodName, "How many hours should be assigned to the employee for the week?");
							String hours = ui.getUserInput();
							if (hours != null) {
								try {
									app.allocateTimeForEmployee(pmInitials, emInitials, convertToDouble(hours), convertToInt(projectID), activityName, yearWeek);
									ui.printSuccess(methodName);
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

	}

	public void planNonWorkActivity() {
		String methodName = "Plan non-work activity";
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			ui.print(methodName, "What kind of activity should be planned? Choose between:");
			for (int i = 0; i < PlannedWeek.NON_WORK_ACTIVITIES.length; i++) {
				ui.print(" [" + (i + 1) + "] " + PlannedWeek.NON_WORK_ACTIVITIES[i]);
			}
			String activityChoice = ui.getUserInput();
			if (activityChoice != null) {
				String activityName = null;
				try {
					activityName = PlannedWeek.NON_WORK_ACTIVITIES[convertToInt(activityChoice) - 1];
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}

				if (activityName != null) {
					ui.print(methodName, "What week should be planned to?");
					String week = requestWeek();
					if (week != null) {
						ui.print(methodName, "How many days is the activity?");
						String days = ui.getUserInput();
						if (days != null) {
							try {
								app.assignToNonWorkActivity(emInitials, activityName, convertToInt(days), week);
								ui.printSuccess(methodName);
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
			String projectID = requestProject(methodName);
			if (projectID != null) {
				try {
					ui.printEmployees(indexer.findProject(convertToInt(projectID)).getAssignedEmployees());
				} catch (Exception e) {
					ui.printError(e.getMessage());
				}
			}
		} else if (choice.equals("3")) { // For an activity
			String projectID = requestProject(methodName);
			if (projectID != null) {
				String activityName = requestActivity(methodName);
				if (activityName != null) {
					try {
						ui.printEmployees(indexer.findActivity(convertToInt(projectID), activityName).getAssignedEmployees());
					} catch (Exception e) {
						ui.printError(e.getMessage());
					}
				}
			}
		} else {
			ui.print("Invalid input please try again");
		}
	}

	public void viewActivities() {
		String methodName = "View activities";
		String projectID = requestProject(methodName);
		if (projectID != null) {
			try {
				ui.printActivities(indexer.findProject(convertToInt(projectID)).getActivities());
			} catch (Exception e) {
				ui.printError(e.getMessage());
			}
		}
	}

	
	public void viewTimeAllocation() {
		String methodName = "View time allocation";
		String emInitials = requestEmployee(methodName);
		if (emInitials != null) {
			ui.print(methodName, "What week do you want to view time allocation for?");
			String week = requestWeek();
			if (week != null) {
				try {
					// TODO Maybe find a more permanent solution
					ui.printTimeAllocation(app.calculatePlannedHours(emInitials, week),
							indexer.findPlannedWeek(indexer.findEmployee(emInitials), week).getPlannedActivities());
				} catch (Exception e) {
					System.out.println(e.getStackTrace()[0].getMethodName());
					ui.printError(e.getMessage());
				}
			}
		}
	}

}