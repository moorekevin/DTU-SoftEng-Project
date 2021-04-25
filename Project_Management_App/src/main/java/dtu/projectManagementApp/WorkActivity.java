package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;

public class WorkActivity extends Activity {
	private int projectId;
	private double expectedHours;
	private double workedHours;
	private List<Employee> assignedEmployees;
	public WorkActivity(String name, Week startWeek, Week endWeek) {
		super(name, startWeek, endWeek);
	}
	
	public double calculateResidualHours(){
		return 0;
	}
	
	public void registerTime(Employee em, double time){
		
	}
	
	public void editActivity(String name, Week startWeek, Week endWeek, int something){
		
	}
}
