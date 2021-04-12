package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class Project {
	private String name;
	private int id;
	private List<Employee> assignedEmployees;
	private static int count;

	public Project(String name, int id) {
		this.name = name;
		this.id = id;
		assignedEmployees = new ArrayList<Employee>();
	}

}
