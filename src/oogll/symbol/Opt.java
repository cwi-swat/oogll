package oogll.symbol;

import oogll.Alt;

public class Opt extends Regular {

	public Opt(Symbol symbol) {
		super(symbol, "?");
		addAlt(new Alt(symbol));
		addAlt(new Alt());
	}

}
