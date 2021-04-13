package dtu.dto;

import java.util.ArrayList;
import java.util.List;
import dtu.projectManagementApp.Project;

import dtu.projectManagementApp.Employee;


public class ProjectInfo {
	private String name;
	private int id;
	private List<Employee> assignedEmployees;
	
	public ProjectInfo(String name, int id) {
		this.name = name;
		this.id = id;
		assignedEmployees = new ArrayList<>();
	}
	
	
	public Project asProject() {
		return new Project(name, id);
	}


	public String getName() {
		return name;
	}


	public int getId() {
		return id;
	}

	
	
}
