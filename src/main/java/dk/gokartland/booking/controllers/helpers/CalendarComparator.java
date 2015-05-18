package dk.gokartland.booking.controllers.helpers;

import java.util.Calendar;
import java.util.Comparator;

public class CalendarComparator implements Comparator<String> {
	@Override
	public int compare(String date1, String date2) {

		Calendar calendar1 = CalendarFormatHelper.toCalendar(date1);
		Calendar calendar2 = CalendarFormatHelper.toCalendar(date2);

		return Long.compare(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());
	}
}
