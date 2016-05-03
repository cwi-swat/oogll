package oogll.symbol;

public class Empty implements Symbol {
	public static final Empty EMPTY = new Empty();
	
	private Empty() {
	}
	
	@Override
	public String toString() {
		return "<>"; //"Û";
	}
}
