package dtu.projectManagementApp;

public abstract class Activity {
	private String name;
	private Week start;
	private Week end;

	public Activity(String name, Week start, Week end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public Week getStartWeek() {
		return start;
	}
	
	public Week getEndWeek() {
		return end;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStart(String startYearWeek) {
		start.setYearWeek(startYearWeek);
	}
	
	public void setEnd(String endYearWeek) {
		end.setYearWeek(endYearWeek);
	}
	
}
