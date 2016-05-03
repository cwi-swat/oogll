package oogll.symbol;

import oogll.GLL;

public class Lit implements Terminal, Symbol {

	private String literal;

	public Lit(String literal) {
		this.literal = literal;
	}
	
	@Override
	public String match(int pos, GLL ctx) {
		if (ctx.getSrc().startsWith(literal, pos)) {
			return literal;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return literal;
	}


}
