import java.util.Arrays;
import java.util.Iterator;

public class LLSimple {
	static Integer[] numbers1 = new Integer[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32,
		79, 2, 60, 26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59
	};

	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		list.addAll(Arrays.asList(numbers1));
		for(int i : list) {
			System.out.print(i + ", ");
		}
		System.out.println();

		Iterator<Integer> itr = list.descendingIterator();
		System.out.println("list size: " + list.size());
		while(itr.hasNext()) {
			System.out.print(itr.next() + ", ");
		}
		System.out.println();
	}
}
