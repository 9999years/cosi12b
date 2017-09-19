abstract class AbstractDate {
	static final int MONTHS_IN_YEAR = 12;
	static final int LEAP_YEAR_FREQUENCY = 4;
	static final int FEBRUARY_LEAP_DAY = 29;

	static final int MIN_YEAR = 1753;
	// love to live in the year 2,147,483,647
	static final int MAX_YEAR = Integer.MAX_VALUE;

	static final Month MIN_MONTH = Month.January;
	static final Month MAX_MONTH = Month.December;

	int year;
	int month;
	int day;

	AbstractDate(int year, int month, int day) {
		this.year  = year;
		this.month = month;
		this.day   = day;
	}

	public boolean isLeapYear() {
		return year % LEAP_YEAR_FREQUENCY == 0;
	}

	public boolean isLeapDay() {
		return isLeapYear() &&
			Month.get(month) == Month.February
			&& day == FEBRUARY_LEAP_DAY;
	}

	public String ISO8601() {
		return String.format("%4d-%2d-%2d", year, month, day);
	}
}
