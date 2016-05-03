package oogll.symbol;

import oogll.Alt;

public class IterStar extends Regular {

	public IterStar(Symbol symbol) {
		super(symbol, "*");
		addAlt(new Alt(new Iter(symbol)));
		addAlt(new Alt());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof IterStar)) {
			return false;
		}
		return getSymbol() == ((IterStar)obj).getSymbol();
	}
}
