package becca.edit;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Iterator;
import java.util.List;
import java.util.stream.BaseStream;

import java.lang.Iterable;

/**
 * class for zipping two collections together and iterating over them at the
 * same time; elements past the size of the shorter collection are discarded
 */
public class BiZip<T, U>
		implements Iterable<Tuple<T, U>>, Iterator<Tuple<T, U>> {
	protected Iterator<T> it;
	protected Iterator<U> iu;

	BiZip(Iterator<T> it, Iterator<U> iu) {
		this.it = it;
		this.iu = iu;
	}

	BiZip(List<T> lt, List<U> lu) {
		this(lt.iterator(), lu.iterator());
	}

	<S extends BaseStream<T, S>, R extends BaseStream<U, R>>
	BiZip(BaseStream<T, S> st, BaseStream<U, R> su) {
		this(st.iterator(), su.iterator());
	}

	public boolean hasNext() {
		return it.hasNext() && iu.hasNext();
	}

	public Tuple<T, U> next() {
		return new Tuple<T, U>(it.next(), iu.next());
	}

	public void forEachRemaining(Consumer<? super Tuple<T, U>> action) {
		while(hasNext()) {
			action.accept(new Tuple<T, U>(it.next(), iu.next()));
		}
	}

	public void forEachRemaining(BiConsumer<T, U> action) {
		while(hasNext()) {
			action.accept(it.next(), iu.next());
		}
	}

	public Iterator<Tuple<T, U>> iterator() {
		return this;
	}

	public void remove() {
		it.remove();
		iu.remove();
	}
}
