/**
 * Stores a date and performs various date-related calculations.
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.TimeZone;
import java.lang.Math;

public class Date extends AbstractDate {
	/**
	 * ms/s * s/min * min/hr * hr/day
	 * = 1000 * 60 * 60 * 24
	 */
	static final int MILLISECONDS_PER_DAY = 86400000;

	static final int DAYS_PER_YEAR = 365;
	static final int DAYS_PER_WEEK = 7;

	static final int EPOCH_YEAR = 1970;
	static final Month EPOCH_MONTH = Month.January;
	static final int EPOCH_DAY = 1;

	public enum Day {
		Sunday,
		Monday,
		Tuesday,
		Wednesday,
		Thursday,
		Friday,
		Saturday;

		private static Day[] vals = values();

		public Day next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}

		public static Day get(int number) {
			for(Day d : vals) {
				if(d.toInt() == number) { return d; }
			}
			throw new IllegalArgumentException("No day "
				+ number + " exists!");
		}

		public String toString() {
			return name();
		}

		public int toInt() {
			return ordinal();
		}
	};

	/**
	 * months have names so we override the field for richer interface
	 */
	protected Month month;

	public int getMonth() { return month.toInt(); }
	protected Month getRichMonth() { return month; }
	public String getMonthName() { return month.toString(); }

	public void setMonth(int month) {
		this.month = Month.get(month);
		enforceMonthInvariants();
	}
	public void setMonth(Month month) {
		this.month = month;
		enforceMonthInvariants();
	}

	Date(int year, int month, int day) {
		super(year, month, day);

		UnvalidatedDate invalid = new UnvalidatedDate(year, month, day);
		if(!invalid.isValid()) {
			throw new IllegalArgumentException(
				invalid.errorMessage()
			);
		}
	}

	Date(UnvalidatedDate date) {
		this(date.getYear(), date.getMonth(), date.getDay());
	}

	Date(int daysSinceEpoch) {
		if(daysSinceEpoch < 0) {
			throw new IllegalArgumentException(
				"days since 1970 must be greater than 0; "
				+ "epoch fail!"
			);
		}

		// fencepost to include fraction of current day (truncated by
		// int division)
		int extraDays = daysSinceEpoch + 1;
		int year = EPOCH_YEAR;

		// reduce days to < days in the current year by iterating
		// im not sure if theres a better alg for this
		// you could approximate it by using 365.25 as the days per year
		// which would work out to be almost correct in the limit
		// daysSinceEpoch -> infinity, but for weird ranges it might
		// fail.  who knows!
		while(extraDays > daysInYear(year)) {
			extraDays -= daysInYear(year);
			year++;
		}
		setYear(year);

		setMonth(EPOCH_MONTH);
		while(extraDays > month.getDays(getYear())) {
			extraDays -= month.getDays(getYear());
			setMonth(month.next());
		}

		setDay(extraDays);
	}

	Date() {
		this(daysSinceEpoch());
	}

	public String getDayOfWeek() {
		// get correct offset for jan 1 current year
		int dayOffset = getYear()
			- MIN_YEAR
			+ leapYearsBetween(MIN_YEAR, getYear());
		return Day.get(
			(dayOfYear() + dayOffset) % DAYS_PER_WEEK
			).toString();
		// formula from alex lopez-ortiz of university of waterloo
		// https://cs.uwaterloo.ca/~alopez-o/math-faq/node73.html
		//int month = (getMonth() + 10) % MONTHS_PER_YEAR;
		//int year = getYear() - (month > 11 ? 1 : 0);
		//return Day.get((
			//getDay()
			//+ (int) (2.6 * month - 0.2)
			////+ 493 * year / 400
			//+ 5 * year / 4
			//- 7 * year / 400
			//+ 6
			//) % DAYS_PER_WEEK).toString();
	}

	public int dayOfYear() {
		int day = getDay();
		for(Month m = Month.January; m.toInt() < getMonth(); m = m.next()) {
			day += m.getDays(getYear());
		}
		return day;
	}

	public void nextYear() {
		if(isLeapDay()) {
			nextDay();
		}

		setYear(getYear() + 1);
	}

	protected void enforceMonthInvariants() {
		// if you call nextMonth on eg. jan 31, you go to feb 31,
		// an invalid date. this makes jan 31 -> mar 3 instead
		if(getDay() > daysInMonth()) {
			setDay(getDay() - daysInMonth());
			// recurse!
			nextMonth();
		}
	}

	public void nextMonth() {
		Month nextMonth = getRichMonth().next();
		setMonth(nextMonth);
		if(nextMonth == Month.January) {
			// roll over year
			nextYear();
		}
		enforceMonthInvariants();
	}

	public void nextDay() {
		setDay(getDay() + 1);
		if(getDay() > daysInMonth()) {
			setDay(1);
			nextMonth();
		}
	}

	public int daysInMonth() {
		return getRichMonth().getDays(getYear());
	}

	public Date copy() {
		return new Date(getYear(), getMonth(), getDay());
	}

	protected static int daysBetweenYears(int a, int b) {
		if(a > b) {
			return daysBetweenYears(b, a);
		} else {
			return (b - a) * DAYS_PER_YEAR
				+ leapYearsBetween(a, b);
		}
	}

	public int daysBetween(Date o) {
		if(compareTo(o) == 0) {
			return 0;
		} else if(compareTo(o) == 1) {
			// flip around
			return o.daysBetween(this);
		} else {
			// o > this
			return daysBetweenYears(getYear(), o.getYear())
				+ o.dayOfYear() - dayOfYear();
		}
	}

	public static int daysSinceEpoch() {
		return (int) ((System.currentTimeMillis()
			+ TimeZone.getDefault().getRawOffset())
			/ MILLISECONDS_PER_DAY);
	}

	public int daysInYear() {
		return daysInYear(getYear());
	}

	public static int daysInYear(int year) {
		return DAYS_PER_YEAR + (isLeapYear(year) ? 1 : 0);
	}

	public static int leapYearsBetween(int min, int max) {
		int years = 0;
		for(int i = min; i <= max; i++) {
			if(isLeapYear(i)) {
				years++;
			}
		}
		return years;
	}
}
