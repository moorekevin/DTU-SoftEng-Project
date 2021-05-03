package dtu.mvc;
import java.util.HashMap;
import java.util.Map;

public class CommandController {
    private Map<String, CommandInfo> commandList;
    Controller controller;

    public CommandController () {
        setCommands();
        controller = new Controller(commandList);
    }

    public void start() {
        controller.start();
    }

    public void setCommands() {
		commandList = new HashMap<String, CommandInfo>();

		commandList.put("/help", new CommandInfo("Print all commands available", new Command() {
			public void runCommand() {
				controller.printCommandList();
			};
		}));
		//////////////////////////////////////////////
		// Project commands //
		commandList.put("/createproject", new CommandInfo("Create a new project", new Command() {
			public void runCommand() {
				controller.createProject();
			};
		}));

		commandList.put("/deleteproject", new CommandInfo("Delete an already existing project", new Command() {
			public void runCommand() {
				controller.deleteProject();
			};
		}));

		commandList.put("/viewprojects", new CommandInfo("Show a list of all projects with name and ID", new Command() {
			public void runCommand() {
				controller.viewProjects();
			};
		}));
		//////////////////////////////////////////////
		// Employee commands //
		commandList.put("/addemployee", new CommandInfo("Create a new employee in the system", new Command() {
			public void runCommand() {
				controller.addEmployee();
			};
		}));

		commandList.put("/removeemployee", new CommandInfo("Remove an existing employee from the system", new Command() {
			public void runCommand() {
				controller.removeEmployee();
			};
		}));

		commandList.put("/assigntoproject", new CommandInfo("Assign an employee to a project", new Command() {
			public void runCommand() {
				controller.assignEmployeeToProject();
			};
		}));

		commandList.put("/removefromproject", new CommandInfo("Remove an employee from a project", new Command() {
			public void runCommand() {
				controller.removeEmployeeFromProject();
			};
		}));

		commandList.put("/assigntoactivity", new CommandInfo("Assign an employee to a workactivity", new Command() {
			public void runCommand() {
				controller.assignEmployeeToActivity();
			};
		}));

		commandList.put("/removefromactivity", new CommandInfo("Remove an employee from a workactivity", new Command() {
			public void runCommand() {
				controller.removeEmployeeFromActivity();
			};
		}));

		commandList.put("/viewemployees",
				new CommandInfo("View list of employees for a project or whole company", new Command() {
					public void runCommand() {
						controller.viewEmployees();
					};
				}));
		//////////////////////////////////////////////
		// Project Manager commands //
		commandList.put("/assignprojectmanager",
				new CommandInfo("Assign an employee as a project manager to a project", new Command() {
					public void runCommand() {
						controller.assignProjectManager();
					};
				}));

		commandList.put("/removeprojectmanager",
				new CommandInfo("Remove a project manager from a project", new Command() {
					public void runCommand() {
						controller.removeProjectManager();
					};
				}));

		//////////////////////////////////////////////
		// Workactivity commands //
		commandList.put("/createactivity", new CommandInfo("Create a new workactivity", new Command() {
			public void runCommand() {
				controller.createActivity();
			};
		}));

		commandList.put("/editactivity", new CommandInfo("Edit a workactivity's information", new Command() {
			public void runCommand() {
				controller.editActivity();
			};
		}));

		commandList.put("/setexpectedhours",
				new CommandInfo("Set an activity's expected amount of work hours", new Command() {
					public void runCommand() {
						controller.setExpectedHours();
					};
				}));

		commandList.put("/viewactivities",
				new CommandInfo("View list of all workactivities or for a specific project", new Command() {
					public void runCommand() {
						controller.viewActivities();
					};
				}));

		//////////////////////////////////////////////
		// Time planning related commands
		commandList.put("/plannonwork", new CommandInfo("Register time for a non-work activity", new Command() {
			public void runCommand() {
				controller.planNonWorkActivity();
			};
		}));

		commandList.put("/plantimeallocation", new CommandInfo("Plan an employees time allocation", new Command() {
			public void runCommand() {
				controller.planTimeAllocation();
			};
		}));

		commandList.put("/viewtimeallocation", new CommandInfo("View time allocation for employees", new Command() {
			public void runCommand() {
				controller.viewTimeAllocation();
			};
		}));
	}
}