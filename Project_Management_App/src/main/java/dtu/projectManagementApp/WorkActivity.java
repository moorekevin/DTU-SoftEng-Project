package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class WorkActivity extends Activity {
	private int projectId;
	private double expectedHours;
	private double workedHours;
	private Week start;
	private Week end;
	private List<Employee> assignedEmployees = new ArrayList<>();
	
	public WorkActivity(String name, Week startWeek, Week endWeek) {
		super(name);
		this.start = startWeek;
		this.end = endWeek;
		expectedHours = 0;
	}
	
	public Week getStartWeek() {
		return start;
	}
	
	public double calculateResidualHours(){
		return 0;
	}
	
	public void registerTime(Employee em, double time){
		
	}
	
	public void editActivity(String name, Week startWeek, Week endWeek, int something){
		
	}
	
	public List<Employee> getAssignedEmployees(){
		return assignedEmployees;
	}
	
	public void addEmployee(Employee em) {
		assignedEmployees.add(em);
	}
	
	public double getExpectedHours(){
		return expectedHours;
	}
	
	public void setExpectedHours(double expectedHours){
		this.expectedHours = expectedHours;
	}
	public Week getStart() {
		return start;
	}
	public void setStart(String startYearWeek) {
		start.setYearWeek(startYearWeek);
	}
	
	public Week getEndWeek() {
		return end;
	}
	
	public void setEnd(String endYearWeek) {
		end.setYearWeek(endYearWeek);
	}
	
	
}
