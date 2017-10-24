package becca.smp;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Iterator;
import java.util.List;

/**
 * class for zipping multiple collections; elements past the size of the
 * shorter collection are discarded
 */
public class QuadZip<T, U, V, W> {
	// pneumonic: [i]terator of t, u
	protected Iterator<T> it;
	protected Iterator<U> iu;
	protected Iterator<V> iv;
	protected Iterator<W> iw;

	class QuadTuple<T, U, V, W> {
		T t;
		U u;
		V v;
		W w;

		QuadTuple(T t, U u, V v, W w) {
			this.t = t;
			this.u = u;
			this.v = v;
			this.w = w;
		}
	}

	QuadZip(
		Iterator<T> it,
		Iterator<U> iu,
		Iterator<V> iv,
		Iterator<W> iw
	) {
		this.it = it;
		this.iu = iu;
		this.iv = iv;
		this.iw = iw;
	}

	QuadZip(List<T> lt, List<U> lu, List<V> lv, List<W> lw) {
		this(
			lt.iterator(),
			lu.iterator(),
			lv.iterator(),
			lw.iterator()
		);
	}

	public boolean hasNext() {
		return it.hasNext()
			&& iu.hasNext()
			&& iv.hasNext()
			&& iw.hasNext();
	}

	public QuadTuple<T, U, V, W> next() {
		return new QuadTuple<T, U, V, W>(
			it.next(),
			iu.next(),
			iv.next(),
			iw.next());
	}

	public void forEachRemaining(
		Function<T, Function<U, Function<V, Consumer<W>>>> c) {
		while(hasNext()) {
			c
				.apply(it.next())
				.apply(iu.next())
				.apply(iv.next())
				.accept(iw.next());
		}
	}

	public void remove() {
		it.remove();
		iu.remove();
		iv.remove();
		iw.remove();
	}
}
