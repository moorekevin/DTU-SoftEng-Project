package dtu.mvc;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;

public class UI {
	TreeSet<String> sortedCommandList;
	Map<String, CommandInfo> commandList;
	Scanner reader;

	public UI(Map<String, CommandInfo> commandList) {
		this.commandList = commandList;

		sortedCommandList = new TreeSet<String>();
		sortedCommandList.addAll(this.commandList.keySet()); // Sorts the commands

		reader = new Scanner(System.in);
		
		System.out.println("Welcome to Project Management Application v1.0");
		System.out.println(" TIP: If you ever want to exit a command type \"/exit\"");
	}

	public void print(String printingInfo) {
		System.out.println(printingInfo);
	}

	public String getUserInput() {
		String input = reader.nextLine();
		input.trim(); // Removes spaces in users input
		return input;
	}

	public void start() {
		System.out.println("\nType your commands:\n (For help type \"/help\")");
	}

	public void printCommandList() {
		System.out.println("\n-------------- List of Commands --------------");
		for (String command : sortedCommandList) { // Prints all available commands
			System.out.format("%-23s%-18s%n", "\"" + command + "\"", " - " + commandList.get(command).getInfo());
		}
	}

	public void printProjects(List<Project> projects) {
		if (projects.size() == 0)
			System.out.println("There are no projects currently");
		else {
			System.out.println("There exist " + projects.size() + " project(s):");
			System.out.format("%-10s%-10s%n", "  ID", "Name");
			System.out.println("  ------------");
			for (Project proj : projects) {
				System.out.format("%-10s%-10s%n", "  " + proj.getId(), proj.getName());
			}
		}
	}
	
	public void printEmployees(List<Employee> employees) {
		if (employees.size() == 0)
			System.out.println("There are no employees currently");
		else {
			System.out.println("There exist " + employees.size() + " employee(s):");
			System.out.println("-------");
			for (int i = 0; i < employees.size(); i++) {
				System.out.format("%-4s%-10s%n", " " + i, " " + employees.get(i).getInitials());
			}
		}
	}
}