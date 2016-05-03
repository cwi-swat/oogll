package oogll.symbol;

import oogll.Alt;


public class Iter extends Regular {

	// it's the client's responsibility to make sure
	// iter nonterminals are shared. So it will not do
	// to instantiate Iter objects multiple times for the
	// same argument symbol.
	
	public Iter(Symbol symbol) {
		super(symbol, "+");
		addAlt(new Alt(symbol));
		// Left recursion is faster than right recursion.
		addAlt(new Alt(this, symbol));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Iter)) {
			return false;
		}
		return getSymbol() == ((Iter)obj).getSymbol();
	}

	public boolean isBase(Alt alt) {
		return alt.equals(getAlts().get(0));
	}

	public boolean isRec(Alt alt) {
		return alt.equals(getAlts().get(1));
	}

	
}
