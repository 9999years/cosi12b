public enum Month {
	January   ( 1, 31),
	February  ( 2, 28),
	March     ( 3, 31),
	April     ( 4, 30),
	May       ( 5, 31),
	June      ( 6, 30),
	July      ( 7, 31),
	August    ( 8, 31),
	September ( 9, 30),
	October   (10, 31),
	November  (11, 30),
	December  (12, 31);

	final int value;
	final int days;

	private static Month[] vals = values();

	Month(int value, int days) {
		this.value = value;
		this.days = days;
	}

	/**
	 * don't call this on December!
	 */
	public Month next() throws IndexOutOfBoundsException {
		return vals[this.ordinal() + 1];
	}

	public static Month get(int number) {
		for(Month m : Month.values()) {
			if(m.value == number) { return m; }
		}
		throw new IllegalArgumentException("No such month!");
	}

	public int getDays(int year) {
		if(new Date(year, 1, 1).isLeapYear()
			&& this == February) {
			return Date.FEBRUARY_LEAP_DAY;
		} else {
			return days;
		}
	}
};
