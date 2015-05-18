package dk.gokartland.booking.controllers.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarFormatHelper {

	private static String format = "d-m-y HH:mm";

	public static Calendar toCalendar(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		Date date = new Date();

		try {
			date = simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);

		return new GregorianCalendar();
	}

	public static String toFormattedString(Calendar calendar) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(calendar.getTime());
	}

}
