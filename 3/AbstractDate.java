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
	protected int month;
	protected int day;

	AbstractDate(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
	}

	/**
	 * this does nothing!!!
	 * java subclassed constructors inherently call super(args) so this
	 * method must exist
	 */
	AbstractDate() { }

	public int getYear()  { return year;  }
	public int getMonth() { return month; }
	public int getDay()   { return day;   }

	public void setYear(int year)   { this.year  = year;  }
	public void setMonth(int month) { this.month = month; }
	public void setDay(int day)     { this.day   = day;   }

	public Month getRichMonth() { return Month.get(month); }
	public String getMonthName() { return Month.get(month).toString(); }

	/**
	 * every 4 years excluding multiples of 100 that arent multiples of 300
	 */
	public boolean isLeapYear() {
		return isLeapYear(year);
	}

	public static boolean isLeapYear(int year) {
		return year % LEAP_YEAR_FREQUENCY == 0
			&& (
				   year % 100 != 0
				|| year % 400 == 0);
	}

	public boolean isLeapDay() {
		return isLeapYear()
			&& getMonth() == Month.February.toInt()
			&& getDay() == FEBRUARY_LEAP_DAY;
	}

	/**
	 * COSI 12B compliant representation of a Date object
	 */
	public String toString() {
		return String.format("%d/%d/%d",
			getYear(), getMonth(), getDay());
	}

	public boolean equals(Object o) {
		if(o instanceof AbstractDate) {
			AbstractDate d = (AbstractDate) o;
			return getYear()      == d.getYear()
				&& getMonth() == d.getMonth()
				&& getDay()   == d.getDay();
		} else {
			return false;
		}
	}
}
