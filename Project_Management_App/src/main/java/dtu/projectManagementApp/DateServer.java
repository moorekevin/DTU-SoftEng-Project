package dtu.projectManagementApp;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateServer {
	private Calendar calendar = new GregorianCalendar();

	public int getWeek() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
}
