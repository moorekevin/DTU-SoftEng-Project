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
	
	public static void validateYearWeek(String yearWeek) throws Exception {
		boolean correctFormat = true;
		if (yearWeek.length() > 4)
			correctFormat = false;

		DateServer date = new DateServer();
		int actualYear = date.getYear() % 100;
		int actualWeek = date.getWeek();
		
		try {
			int year = Integer.parseInt((yearWeek).substring(0, 2));

			int week;
			if (yearWeek.charAt(0) == '0')
				week = Integer.parseInt((yearWeek).substring(3, 4));
			else
				week = Integer.parseInt((yearWeek).substring(2, 4));
			if (!correctFormat || year < actualYear || year == actualYear && week < actualWeek || week < 1
					|| week > 52) {
				throw new Exception("Week(s) are invalid");
			}
		} catch (NumberFormatException e) {
			throw new Exception("Week(s) are invalid");
		}

	}

	public static void validateWeekInterval(String start, String end) throws Exception {
		validateYearWeek(start);
		validateYearWeek(end);
		if (Integer.parseInt(start) > Integer.parseInt(end))
			throw new Exception("Start week cannot be larger than end week");
	}
}
