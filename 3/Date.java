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

	Date(UnvalidatedDate date) {
		this(date.year, date.month, date.day);
	}
}
