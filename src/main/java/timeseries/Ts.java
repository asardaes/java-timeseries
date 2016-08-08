package timeseries;

import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public final class Ts {
	
	private static final String ZONE_ID = "America/Chicago";
	
	private Ts(){}
	
	/**
	 * Construct a new TimeSeries object with a cycle length of one year and monthly observations,
	 * starting at the given year and month.
	 * @param startYear The year of the first observation.
	 * @param startMonth The month of the first observation - an integer between
	 *  1 and 12 corresponding to the months January through December.
	 * @param series The sequence of observations constituting this time series.
	 * @return A new TimeSeries object with the given year, month, and series data.
	 */
	public static final TimeSeries newMonthlySeries(final int startYear, final int startMonth,
			final double... series) {
		final Instant startingInstant = ZonedDateTime.of(startYear, startMonth, 1, 0, 0, 0, 0,
				ZoneId.of(ZONE_ID)).toInstant();
		return new TimeSeries(ChronoUnit.YEARS, Period.ofMonths(12), startingInstant, series);
	}
	
	/**
	 * Construct a new TimeSeries object with a cycle length of one year and monthly observations,
	 * starting at the given year, month, and day.
	 * @param startYear The year of the first observation.
	 * @param startMonth The month of the first observation - an integer between
	 *  1 and 12 corresponding to the months January through December.
	 * @param startDay The day of the first observation - an integer between 1 and 31.
	 * @param series The sequence of observations constituting this time series.
	 * @return A new TimeSeries object with the given year, month, and series data.
	 */
	public static final TimeSeries newMonthlySeries(final int startYear, final int startMonth,
			final int startDay, final double... series) {
		final Instant startingInstant = ZonedDateTime.of(startYear, startMonth, startDay, 0, 0, 0, 0,
				ZoneId.of(ZONE_ID)).toInstant();
		return new TimeSeries(ChronoUnit.YEARS, Period.ofMonths(12), startingInstant, series);
	}
	
	/**
	 * Construct a new TimeSeries object with a cycle length of one year and quarterly observations,
	 * starting at the given year and quarter.
	 * @param startYear The year of the first observation.
	 * @param startQuarter The quarter of the first observation - an integer between
	 *  1 and 4 corresponding to the four quarters of a year.
	 * @param series The sequence of observations constituting this time series.
	 * @return A new TimeSeries object with the given year, quarter, and series data.
	 */
	public static final TimeSeries newQuarterlySeries(final int startYear, final int startQuarter,
			final double... series) {
		final int startMonth  = 3*startQuarter - 2;
		final Instant startingInstant = ZonedDateTime.of(startYear, startMonth, 1, 0, 0, 0, 0,
				ZoneId.of(ZONE_ID)).toInstant();
		return new TimeSeries(ChronoUnit.YEARS, Period.ofMonths(3), startingInstant, series);
	}

}
