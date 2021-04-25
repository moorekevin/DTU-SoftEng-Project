package dtu.projectManagementApp;

public abstract class Activity {
	private String name;
	private Week startWeek;
	private Week endWeek;

	public Activity(String name, Week startWeek, Week endWeek) {
		this.name = name;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
	}
	
}
