package dtu.mvc;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

import dtu.projectManagementApp.Activity;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;
import dtu.projectManagementApp.WorkActivity;

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

	public void print(String methodName, String printingInfo) {
		System.out.println("\n[" + methodName + "] " + printingInfo);
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
		System.out.println("\nType your commands:");
		System.out.println(" (For help type \"/help\")");
	}

	public void printCommandList() {
		System.out.println("\n-------------- List of Commands --------------");
		for (String command : sortedCommandList) { // Prints all available commands
			System.out.format("%-23s%-18s%n", "\"" + command + "\"", " - " + commandList.get(command).getInfo());
		}
	}

	public void printProjects(List<Project> projects) {
		if (projects.size() == 0)
			System.out.println("\nThere are no projects currently");
		else {
			System.out.println("\nThere exist " + projects.size() + " project(s):");
			System.out.format("%-10s%-12s%-10s%n", "  ID", "Name", "Project manager");
			System.out.println("  -----------------------------------");
			for (Project proj : projects) {
				System.out.format("%-10s%-12s%-10s%n", "  " + proj.getId(), proj.getName(), proj.getProjectManager() == null ? "" : proj.getProjectManager().getInitials());
			}
		}
	}
	
	public void printEmployees(List<Employee> employees) {
		if (employees.size() == 0)
			System.out.println("\nThere aren't any employees currently");
		else {
			System.out.println("\nThere exist " + employees.size() + " employee(s):");
			System.out.println("-------");
			for (int i = 0; i < employees.size(); i++) {
				System.out.format("%-4s%-10s%n", " " + (i + 1), " " + employees.get(i).getInitials());
			}
		}
	}
	
	public void printActivities(List<WorkActivity> activities) {
		if (activities.size() == 0)
			System.out.println("\nThere aren't any activities for the project currently");
		else {
			System.out.println("\nThere exist " + activities.size() + " activity(ies) for the project:");
			System.out.format("%-12s%-6s%-6s%-6s%n", "  Name", " Start", " End", " Expected hours");
			System.out.println("  -------------------------------------");
			for (WorkActivity act: activities) {
				System.out.format("%-12s%-6s%-6s%-6s%n", "  " + act.getName(), " " + act.getStartWeek().getYearWeek(), " " + act.getEndWeek().getYearWeek(), " " + act.getExpectedHours());
			}
		}
	}
	
	// TODO Sort
	public void printTimeAllocation(double plannedHours, Map<Activity,Double> plannedActivities) {
		if (plannedHours == 0.0) {
			System.out.println("\nThere aren't any planned hours for the week");
		} else {
			System.out.println("\nThere are a total of " + plannedHours + " hour(s) planned for the chosen week:");
			System.out.format("%-10s%-10s%n", "  Name", " Expected hours");
			System.out.println("  -----------------------");
			for (Activity act : plannedActivities.keySet()) {
				System.out.format("%-10s%-10s%n", "  " + act.getName(), " " + plannedActivities.get(act));
			}
		}
	}
}