/**
 * a simple tuple; two elements tied up; used with BiZip to wrap pairs of
 * elements together
 */
public class Tuple<T, U> {
	T t;
	U u;

	Tuple(T t, U u) {
		this.t = t;
		this.u = u;
	}
}
