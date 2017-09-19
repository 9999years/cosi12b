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
	}
}
