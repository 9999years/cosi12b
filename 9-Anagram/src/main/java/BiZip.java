import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.Iterator;
import java.util.List;

import java.lang.Iterable;

/**
 * class for zipping two collections together and iterating over them at the
 * same time; elements past the size of the shorter collection are discarded
 */
public class BiZip<T, U> implements Iterable<Tuple<T, U>>, Iterator<Tuple<T, U>> {
	protected Iterator<T> it;
	protected Iterator<U> iu;

	BiZip(Iterable<T> it, Iterable<U> iu) {
		this.it = it.iterator();
		this.iu = iu.iterator();
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

	public void forEachRemaining(Consumer<? super Tuple<T, U>> c) {
		while(hasNext()) {
			c.accept(new Tuple<>(it.next(), iu.next()));
		}
	}

	public void forEachRemaining(BiConsumer<T, U> c) {
		forEachRemaining(t -> c.accept(t.t, t.u));
	}

	public void remove() {
		it.remove();
		iu.remove();
	}

	public Iterator<Tuple<T, U>> iterator() {
		return this;
	}
}
