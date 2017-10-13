import java.util.HashSet;

/**
 * node with an index and probabilities from its context to the next tokens
 */
public class Node<T> {
	int inx;
	HashSet<Edge<T>> possibilities;
}
