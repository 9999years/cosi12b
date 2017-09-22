import java.util.Scanner;
import java.util.NoSuchElementException;

public class Birthday {
	public static UnvalidatedDate getDate() {
		Scanner in = new Scanner(System.in);
		int month, day, year;
		try {
			month = in.nextInt();
			day   = in.nextInt();
			year  = in.nextInt();
		} catch(NoSuchElementException e) {
			System.err.println(
				"\nInput terminated unexpectedly! Exiting!"
			);
			System.exit(-1);
			return null;
		}
		return new UnvalidatedDate(year, month, day);
	}

	private static UnvalidatedDate simpleGetDate(String promptText) {
		System.out.print(promptText);
		return getDate();
	}

	public static Date promptDate(String promptText) {
		UnvalidatedDate d = simpleGetDate(promptText);
		while(!d.isValid()) {
			System.err.println(d.errorMessage());
			d = simpleGetDate(promptText);
		}
		return d.validate();
	}

	public static void main(String[] args) {
		Date birthday = promptDate("What month, day, and year were you born? ");

		System.out.println(
			"You were born on "
			+ birthday
			+ ", which was a "
			+ birthday.getDayOfWeek()
			+ "."
		);

		if(birthday.isLeapYear()) {
			System.out.println(
				birthday.getYear()
				+ " was a leap year."
			);
		}

		Date nextBirthday = birthday.copy();
		Date today = new Date();
		// testing!
		today = new Date(2010, 1, 30);

		// next birthday is this year
		nextBirthday.setYear(today.getYear());

		if(nextBirthday.compare(today) == 0) {
			System.out.println("Happy birthday! You are now age "
				+ (today.getYear() - birthday.getYear())
				+ ".");
		} else {
			// birthday is not today
			if(nextBirthday.compare(today) == -1) {
				// birthday already happened this year,
				// increment the year!
				nextBirthday.nextYear();
			}
			System.out.println("It will be your birthday in "
				+ today.daysBetween(nextBirthday)
				+ " days.");
		}
		System.out.println("You are " + birthday.daysBetween(today)
			+ " days old.");
	}
}
