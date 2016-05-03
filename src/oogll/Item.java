package oogll;

import oogll.sppf.Node;
import oogll.symbol.NonTerminal;


public class Item implements Parser {

	private final Alt alt;
	private final int dot;

	public Item(Alt alt, int dot) {
		this.alt = alt;
		this.dot = dot;
	}
	
	
	@Override
	public void parse(int pos, GSS cu, Node cn, GLL ctx) {
		alt.parseAt(dot, pos, cu, cn, ctx);
	}
	
	public int getDot() {
		return dot;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < alt.arity(); i++) {
			if (i == dot) {
				s += " .";
			}
			s += " " + alt.get(i) ;
		}
		if (dot == alt.arity()) {
			s += " .";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return alt.hashCode() * 31 + dot * 7;
	}

	public boolean isAtEnd() {
		return dot == alt.arity();
	}

	public NonTerminal getNonTerminal() {
		return alt.getNonTerminal();
	}
	
	public int arity() {
		return alt.arity();
	}


	public Alt getAlt() {
		return alt;
	}

	
}

