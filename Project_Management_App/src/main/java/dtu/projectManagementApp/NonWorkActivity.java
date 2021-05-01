package dtu.projectManagementApp;

public class NonWorkActivity extends Activity {
	private int expectedDays;

	public NonWorkActivity(String name) {
		super(name);
	}
	
	public int getExpectedDays(){
		return expectedDays;
	}
	
	public void setExpectedDays(int expectedDays){
		this.expectedDays = expectedDays;
	}
	
	
}
