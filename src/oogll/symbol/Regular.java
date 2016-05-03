package oogll.symbol;

public abstract class Regular extends NonTerminal {

	private final Symbol symbol;

	public Regular(Symbol symbol, String suffix) {
		super(symbol + suffix);
		this.symbol = symbol;
	}

	public Symbol getSymbol() {
		return symbol;
	}	
	

}
