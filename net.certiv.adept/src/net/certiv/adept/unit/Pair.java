package net.certiv.adept.unit;

public class Pair<A, B> {

	public A a;
	public B b;

	public Pair() {}

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", a, b);
	}
}
