package dtu.projectManagementApp;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateServer {
	private Calendar calendar = new GregorianCalendar();

	public Calendar getDate() {
		Calendar calendar = new GregorianCalendar();
		Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
		return c;
	}
	
	public int getWeek() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}
}
