package no.interview.tradinginfo;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class Utils {
	static int getEpochOfYearStart(int year) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(year, 0, 1, 0, 0, 0);
		return (int) (calendar.getTimeInMillis() / 1000);
	}

	static int readChar() throws IOException {
		int c = System.in.read();

		while (System.in.read() != '\n') {
			;
		}

		return c;
	}
}
