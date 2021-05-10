/*
	Made by Bjørn Laursen s204451
	This class represents a WorkActivity assigned to a Project
*/

package dtu.projectManagementApp;

import java.util.List;

import dtu.exceptions.OperationNotAllowedException;

import java.util.ArrayList;

public class WorkActivity extends Activity {
	private Double expectedHours;
	private String start;
	private String end;
	private List<Employee> assignedEmployees = new ArrayList<>();
	
	public WorkActivity(String name, String startWeek, String endWeek) {
		super(name);
		this.start = startWeek;
		this.end = endWeek;
		expectedHours = 0.0;
	}
	
	public List<Employee> getAssignedEmployees(){
		return assignedEmployees;
	}
	
	public void addEmployee(Employee em) {
		assignedEmployees.add(em);
	}
	
	public void removeEmployee(Employee em) {
		assignedEmployees.remove(em);
	}
	
	public Double getExpectedHours() {
		return expectedHours;
	}
	
	public void setExpectedHours(double expectedHours) throws OperationNotAllowedException {
		if (expectedHours < 0) throw new OperationNotAllowedException("Hours cannot be negative");
		this.expectedHours = expectedHours;
	}
	
	public String getStart() {
		return start;
	}
	
	public void setStart(String startYearWeek) {
		start = startYearWeek;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String endYearWeek) {
		end = endYearWeek;
	}
	
	
}
