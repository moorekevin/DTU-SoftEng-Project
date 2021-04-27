package dtu.projectManagementApp;

public class Week {
	private String weekYear;
	private DateServer dateServer = new DateServer();
	// Week 0121 = 1. Uge i 2021
			// Week logik:
				// Tjek for at ugen og året ikke er tilbage i tiden (Dette skal gøres i week constructoren).
				// Tjek også for gyldig ugenr osv. og tiløj relevante fejl i test.
	public Week(String weekYear) {
		this.weekYear = weekYear;
	}

}
