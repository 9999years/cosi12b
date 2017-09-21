public class UnvalidatedDate extends AbstractDate {
	UnvalidatedDate(int year, int month, int day) {
		super(year, month, day);
	}

	UnvalidatedDate(Date date) {
		this(date.getYear(), date.getMonth(), date.getDay());
	}

	public boolean validateYear() {
		return year >= MIN_YEAR;
	}

	public boolean validateMonth() {
		return getMonth() > 0 && getMonth() <= MONTHS_IN_YEAR;
	}

	/**
	 * only works on valid months and years
	 */
	public boolean validateDay() {
		return validateMonth()
			&& day > 0
			&& (
				day <= getRichMonth().getDays(getYear())
				|| isLeapDay()
			);
	}

	public String errorMessage() {
		if(!validateMonth()) {
			return getMonth() + " is not a valid month!";
		} else if(!validateDay()) {
			// valid month
			if(getMonth() == Month.February.toInt()) {
				return getDay()
					+ " is not a valid day in month "
					+ getMonthName()
					+ " during year "
					+ getYear() + "!";
			} else {
				return getDay()
					+ " is not a valid day in month "
					+ getMonthName() + "!";
			}
		} else if(!validateYear()) {
			// valid day and month
			return getYear() + " is not a valid year; the Gregorian"
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
