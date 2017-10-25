/**
 * Performs calculations on a birthday and outputs potentially useful
 * information.
 *
 * @author Rebecca Turner
 * @version 1.0.0
 * @license AGPL3.0 gnu.org/licenses/agpl.html
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Birthday {
	public static UnvalidatedDate getDate(Scanner in) {
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

	public static UnvalidatedDate getDate() {
		return getDate(new Scanner(System.in));
	}

	private static UnvalidatedDate simpleGetDate(String promptText) {
		System.out.print(promptText);
		return getDate();
	}

	public static Date parseDate(String input) {
		UnvalidatedDate d = getDate(new Scanner(input));
		if(!d.isValid()) {
			System.err.println(d.errorMessage());
			return null;
		} else {
			return d.validate();
		}
	}

	public static Date promptDate(String promptText) {
		UnvalidatedDate d = simpleGetDate(promptText);
		while(!d.isValid()) {
			System.err.println(d.errorMessage());
			d = simpleGetDate(promptText);
		}
		return d.validate();
	}

	public static Date getToday(String[] args) {
		Date today;
		if(args.length >= 2 && (
				args[0].equals("-d")
				|| args[0].equals("--date"))) {
			if(args.length == 2) {
				today = parseDate(args[1]);
			} else if(args.length == 4) {
				today = parseDate(String.join(" ",
					Arrays.asList(args).subList(1, 4)));
			}

			if(today == null) {
				System.exit(-1);
			}
			System.out.println("Let's pretend today is "
				+ today.getDayOfWeek()
				+ ", "
				+ today.getMonthName()
				+ " "
				+ today.getDay()
				+ ", "
				+ today.getYear()
				+ ".");
			return today;
		} else {
			today = new Date();
		}
		return today;
	}

	public static void main(String[] args) {
		Date today = getToday(args);

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
		// next birthday is this year
		nextBirthday.setYear(today.getYear());

		if(nextBirthday.compareTo(today) == 0) {
			System.out.println("Happy birthday! You are now age "
				+ (today.getYear() - birthday.getYear())
				+ ".");
		} else {
			// birthday is not today
			if(nextBirthday.compareTo(today) == -1) {
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
