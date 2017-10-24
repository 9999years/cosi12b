package becca.smp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public class StableMatching {
	public static void main(String args[]) {
		List<Integer> intPriorities = new ArrayList(Arrays.asList(
			new Integer[] {0, 1, 2, 3, 4, 5, 6}
		));
		List<NodePriority> priorities = new ArrayList<NodePriority>();
		Iterator<Integer> itr = intPriorities.iterator();
		for(int i = 0; itr.hasNext(); i++) {
			priorities.add(new NodePriority(i,
				new Node(null, null, itr.next())));
		}
		System.out.println("priorities 4: " + priorities.get(4));
		System.out.println(priorities.get(4).equals(new Node(null, null, 4)));
		priorities.remove(new Node(null, null, 4));
		System.out.println(priorities);
	}
}
