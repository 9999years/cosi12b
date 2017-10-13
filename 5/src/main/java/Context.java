public class Context<T> {
	// we dont need appending or anything, so an array is fine
	T[] stack;

	Context(T[] stack) {
		this.stack = stack;
	}
}
