import java.util.TimeZone;

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
	public Month getRichMonth() { return month; }
	public String getMonthName() { return month.toString(); }

	public void setMonth(int month)   { this.month = Month.get(month); }
	public void setMonth(Month month) { this.month = month; }

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

	public void nextMonth() {
		Month nextMonth = getRichMonth().next();
		setMonth(nextMonth);
		if(nextMonth == Month.January) {
			// roll over year
			nextYear();
		}
		// if you call nextMonth on eg. dec 31, you go to january 31,
		// an invalid date. this makes dec 31 -> feb 3 instead
		if(getDay() > daysInMonth()) {
			setDay(getDay() - daysInMonth());
			// recurse!
			nextMonth();
		}
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

	public int daysBetween(Date o) {
		if(compare(o) == 0) {
			return 0;
		} else if(compare(o) == 1) {
			// flip around
			return o.daysBetween(this);
		} else {
			int days = 0;
			Date counter = copy();
			while(counter.getYear() < o.getYear()) {
				days += counter.daysInYear();
				counter.setYear(counter.getYear() + 1);
			}
			while(counter.getMonth() < o.getMonth()) {
				days += counter.daysInMonth();
				counter.setMonth(counter.getMonth() + 1);
			}
			days += o.getDay() - counter.getDay();
			return days;
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
