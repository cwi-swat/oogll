package oogll.forest;

public class Nil extends Tree {
	
	@Override
	public String toString() {
		return "<>";
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public String getShape() {
		return "plaintext";
	}

}
