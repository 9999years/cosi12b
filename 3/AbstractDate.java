abstract class AbstractDate {
	static final int MONTHS_IN_YEAR = 12;
	static final int LEAP_YEAR_FREQUENCY = 4;
	static final int FEBRUARY_LEAP_DAY = 29;

	static final int MIN_YEAR = 1753;
	// love to live in the year 2,147,483,647
	static final int MAX_YEAR = Integer.MAX_VALUE;

	static final Month MIN_MONTH = Month.January;
	static final Month MAX_MONTH = Month.December;

	protected int year;
	/**
	 * months are unique in having names and a variable number of days
	 */
	protected Month month;
	protected int day;

	AbstractDate(int year, int month, int day) {
		this.year  = year;
		this.month = Month.get(month);
		this.day   = day;
	}

	/**
	 * this does nothing!!!
	 * java subclassed constructors inherently call super(args) so this
	 * method must exist
	 */
	AbstractDate() { }

	public int getYear()  { return year;  }
	public int getMonth() { return month.value; }
	public int getDay()   { return day;   }

	public Month getRichMonth() { return month; }
	public String getMonthName() { return month.name(); }

	public String ISO8601() {
		return String.format("%4d-%2d-%2d", year, month, day);
	}

	public boolean isLeapYear() {
		return year % LEAP_YEAR_FREQUENCY == 0;
	}

	public boolean isLeapDay() {
		return isLeapYear()
			&& getRichMonth() == Month.February
			&& getDay() == FEBRUARY_LEAP_DAY;
	}

	/**
	 * COSI 12B compliant representation of a Date object
	 */
	public String toString() {
		return String.format("%d/%d/%d", year, month, day);
	}

	public boolean equals(Object o) {
		if(o instanceof AbstractDate) {
			AbstractDate d = (AbstractDate) o;
			return year      == d.getYear()
				&& month == d.getRichMonth()
				&& day   == d.getDay();
		} else {
			return false;
		}
	}
}
