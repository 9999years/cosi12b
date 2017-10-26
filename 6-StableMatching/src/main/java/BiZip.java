package becca.smp;

import java.util.function.BiConsumer;
import java.util.Iterator;
import java.util.List;

/**
 * class for zipping two collections together and iterating over them at the
 * same time; elements past the size of the shorter collection are discarded
 */
public class BiZip<T, U> {
	protected Iterator<T> it;
	protected Iterator<U> iu;

	class Tuple<T, U> {
		T t;
		U u;

		Tuple(T t, U u) {
			this.t = t;
			this.u = u;
		}
	}

	BiZip(Iterator<T> it, Iterator<U> iu) {
		this.it = it;
		this.iu = iu;
	}

	BiZip(List<T> lt, List<U> lu) {
		this(lt.iterator(), lu.iterator());
	}

	public boolean hasNext() {
		return it.hasNext() && iu.hasNext();
	}

	public Tuple<T, U> next() {
		return new Tuple<T, U>(it.next(), iu.next());
	}

	public void forEachRemaining(BiConsumer<T, U> c) {
		while(hasNext()) {
			c.accept(it.next(), iu.next());
		}
	}

	public void remove() {
		it.remove();
		iu.remove();
	}
}
