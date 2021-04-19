package dtu.mvc;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class UI {
	TreeSet<String> sortedCommandList; 
	Map<String, CommandInfo> commandList;
	Scanner reader;
		
	public UI(Map<String, CommandInfo> commandList) {
		this.commandList = commandList;

		sortedCommandList = new TreeSet<String>();
		sortedCommandList.addAll(this.commandList.keySet()); // Sorts the commands
		
		reader = new Scanner(System.in);
	}

	public void print(String printingInfo) {
		System.out.println(printingInfo);
	}

	public String getUserInput() {
		return reader.nextLine();
	}


	
	public void start() {
		System.out.println("Welcome to Project Management Application\nType your commands:\n (For help type /help)\n");
	}
	
	public void printCommandList() {
		System.out.println("-------------- List of Commands --------------");
		for (String command : sortedCommandList) { // Prints all available commands
			System.out.format("%-18s%-18s%n", "\"" + command + "\"", " - " + commandList.get(command).getInfo());
		}
	}
	
	public void deleteProject() {
		
		System.out.println("project deleted");
	}
	
}