package dtu.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface Command {
    void runCommand();
}

public class Controller {
	private View view;
	private Model model;
	private Scanner reader;

	private boolean isRunning = true;

	private Map<String, Command> commandList;

	public Controller() {
		view = new View();
		model = new Model();
		reader = new Scanner(System.in);
	}

	public void start() {
		setCommands();

		view.start();
		while (isRunning) {
			runUserCommand(reader.nextLine());
		}
	}

	public void setCommands() {
		commandList = new HashMap<String, Command>();

		// Setting all commands
		commandList.put("/help", new Command() {
            public void runCommand() { help(); };
        });
		// commandList.put("/createproject", "Create a new project with a designated name");
		// commandList.put("/deleteproject", "Delete an already existing project");

		commandList.get("/help");
	}
	
	private void help() {
		System.out.println("help");
	}

	public void runUserCommand(String userInput) {
		commandList.get(userInput);
		
		
//		switch ("/help") {
//        
//		case (String) commandListArray[0]: // help
//			System.out.println("ok");
//			break;
//		}
	}
}