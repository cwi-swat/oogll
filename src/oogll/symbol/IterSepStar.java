package oogll.symbol;

import oogll.Alt;

public class IterSepStar extends Regular {

	public IterSepStar(Symbol symbol, Symbol sep) {
		super(symbol, sep + "*");
		addAlt(new Alt(new IterSep(symbol, sep)));
		addAlt(new Alt());
	}

	public IterSepStar(Symbol symbol, Symbol ...seps) {
		super(symbol, seps + "*");
		addAlt(new Alt(new IterSep(symbol, seps)));
		addAlt(new Alt());
	}

	
}
