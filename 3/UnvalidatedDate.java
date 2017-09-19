public class UnvalidatedDate extends AbstractDate {
	UnvalidatedDate(int year, int month, int day) {
		super(year, month, day);
	}

	UnvalidatedDate(Date date) {
		this(date.year, date.month, date.day);
	}

	public boolean validateYear() {
		return year >= MIN_YEAR;
	}

	public boolean validateMonth() {
		return month > 0 && month <= MONTHS_IN_YEAR;
	}

	/**
	 * only works on valid months
	 */
	public boolean validateDay() {
		return validateMonth()
			&& day > 0
			&& (
				day <= Month.get(month).days
				|| isLeapDay()
			);
	}

	public String errorMessage() {
		if(!validateMonth()) {
			return month + " is not a valid month!";
		} else if(!validateDay()) {
			if(month == Month.February.value) {
				return day
					+ " is not a valid day in month "
					+ month
					+ " during year "
					+ year + "!";
			} else {
				return day
					+ " is not a valid day in month "
					+ month + "!";
			}
		} else if(!validateYear()) {
			return year + " is not a valid year; the Gregorian"
				+ " calendar had yet to be adopted and"
				+ " inconsistencies in year-length make"
				+ " processing Julian dates out of the scope of"
				+ " this program!";
		}
		// no error!
		return null;
	}

	public boolean isValid() {
		return validateMonth() && validateDay() && validateYear();
	}

	public Date validate() {
		return isValid()
			? new Date(this)
			: null;
	}
}
