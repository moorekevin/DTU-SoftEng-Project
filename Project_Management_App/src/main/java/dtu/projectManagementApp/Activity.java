package dtu.projectManagementApp;

public abstract class Activity {
	private String name;

	public Activity(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
