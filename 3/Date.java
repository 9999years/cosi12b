import java.util.TimeZone;

public class Date extends AbstractDate {
	/**
	 * ms/s * s/min * min/hr * hr/day
	 * = 1000 * 60 * 60 * 24
	 */
	static final int MILLISECONDS_PER_DAY = 86400000;

	static final int DAYS_PER_YEAR = 365;

	static final int EPOCH_YEAR = 1970;
	static final Month EPOCH_MONTH = Month.January;
	static final int EPOCH_DAY = 1;

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

	Date() {
		int days = daysSinceEpoch();
		year = days / DAYS_PER_YEAR + EPOCH_YEAR;
		int extraDays = days % DAYS_PER_YEAR;
		month = EPOCH_MONTH;
		while(extraDays > month.days) {
			extraDays -= month.days;
			month = month.next();
		}
	}

	public static int daysSinceEpoch() {
		return (int) ((System.currentTimeMillis()
			+ TimeZone.getDefault().getRawOffset())
			/ MILLISECONDS_PER_DAY);
	}
}
