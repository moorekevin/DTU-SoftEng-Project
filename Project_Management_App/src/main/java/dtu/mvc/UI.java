package dtu.mvc;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

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
	}

	public void print(String printingInfo) {
		System.out.println(printingInfo);
	}

	public String getUserInput() {
		return reader.nextLine();
	}

	public void start() {
		System.out.println("Welcome to Project Management Application\nType your commands:\n (For help type \"/help\")\n");
	}

	public void printCommandList() {
		System.out.println("\n-------------- List of Commands --------------");
		for (String command : sortedCommandList) { // Prints all available commands
			System.out.format("%-23s%-18s%n", "\"" + command + "\"", " - " + commandList.get(command).getInfo());
		}
		System.out.println();
	}

	public void printProjects(List<Project> projects) {
		if (projects.size() == 0)
			System.out.println("\nThere are no projects currently");
		else {
			System.out.println("\nThere exist " + projects.size() + " project(s):");
			System.out.format("%-10s%-10s%n", "  ID", "Name");
			System.out.println("  ------------");
			for (Project proj : projects) {
				System.out.format("%-10s%-10s%n", "  " + proj.getId(), proj.getName());
			}
			System.out.println();
		}
	}
}