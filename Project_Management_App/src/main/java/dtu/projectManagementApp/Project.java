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
	
	public Employee getProjectManger() {
		for (int i = 0; i < assignedEmployees.size(); i++) {
			if (assignedEmployees.get(i).isProjectManger()) return assignedEmployees.get(i);
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
