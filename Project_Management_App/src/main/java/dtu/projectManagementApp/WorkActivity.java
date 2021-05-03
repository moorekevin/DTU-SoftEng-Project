package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class WorkActivity extends Activity {
	private double expectedHours;
	private String start;
	private String end;
	private List<Employee> assignedEmployees = new ArrayList<>();
	
	public WorkActivity(String name, String startWeek, String endWeek) {
		super(name);
		this.start = startWeek;
		this.end = endWeek;
		expectedHours = 0;
	}
	
	
	public double calculateResidualHours(){
		return 0;
	}
	
	public void registerTime(Employee em, double time){
		
	}
	
	public void editActivity(String name, String startWeek, String endWeek, int something){
		
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
	
	public double getExpectedHours() {
		return expectedHours;
	}
	
	public void setExpectedHours(double expectedHours){
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
