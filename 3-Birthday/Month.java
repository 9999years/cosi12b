/**
 * Stores a month and performs a couple month-related calculations.
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

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

	public Month next() {
		return vals[(this.ordinal() + 1) % vals.length];
	}

	public static Month get(int number) {
		for(Month m : vals) {
			if(m.toInt() == number) { return m; }
		}
		throw new IllegalArgumentException("No month "
			+ number + " exists!");
	}

	public int getDays(int year) {
		if(Date.isLeapYear(year)
			&& this == February) {
			return Date.FEBRUARY_LEAP_DAY;
		} else {
			return days;
		}
	}

	public String toString() {
		return name();
	}

	public int toInt() {
		return value;
	}
};
