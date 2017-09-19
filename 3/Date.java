public class Date extends AbstractDate {
	Date(int year, int month, int day) {
		super(year, month, day);

		UnvalidatedDate invalid = new UnvalidatedDate(year, month, day);
		if(!invalid.isValid()) {
			throw new IllegalArgumentException(
				invalid.errorMessage()
			);
		}
	}

	public String ISO8601() {
		return String.format("%4d-%2d-%2d", year, month, day);
	}

	public static boolean isLeapYear(int year) {
		return year % LEAP_YEAR_FREQUENCY == 0;
	}
}
