public class A {
	public static void main(String[] args) {
		System.out.println(new B().b);
		System.out.println(new B().getB());
		System.out.println(new C().b);
		System.out.println(new C().getB());
	}
}

class B {
	int b = 10;
	int getB() { return b; }
}

class C extends B {
	int b = 20;
	int getB() { return super.getB(); }
}
