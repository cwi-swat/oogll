package oogll.symbol;

import java.util.Arrays;

import oogll.Alt;

public class IterSep extends Regular {

	public IterSep(Symbol symbol, Symbol sep) {
		super(symbol, sep + "+");
		addAlt(new Alt(symbol));
		addAlt(new Alt(this, sep, symbol));
	}

	public IterSep(Symbol symbol, Symbol ...seps) {
		super(symbol, Arrays.toString(seps) + "+");
		addAlt(new Alt(symbol));
		Symbol[] args = new Symbol[seps.length + 2];
		args[0] = this;
		args[seps.length + 1] = symbol;
		for (int i = 1; i < seps.length + 1; i++) {
			args[i] = seps[i - 1];
		}
		addAlt(new Alt(args));
	}
	
	

}
