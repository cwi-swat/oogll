package oogll.sppf;

import oogll.symbol.NonTerminal;
import oogll.symbol.Symbol;

public class SymbolNode extends Parent {
	
	private NonTerminal symbol;

	public SymbolNode(NonTerminal symbol, int i, int j) {
		super(i, j);
		this.symbol = symbol;
	}

	@Override
	public String getLabel() {
		return symbol.toString() + "(" + getLeftExtent() + ", " + getRightExtent() + ")";
	}

	@Override
	public String getShape() {
		if (width() > 1) {
			return "diamond";
		}
		return "box";
	}

	@Override
	public boolean hasSymbol(Symbol symbol) {
		return symbol.equals(this.symbol);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SymbolNode)) {
			return false;
		}
		SymbolNode node = (SymbolNode)obj;
		return symbol.equals(node.symbol) && equalExtent(node);
	}
	
	@Override
	public int hashCode() {
		return symbol.hashCode() * 31 + super.hashCode();
	}

	public Symbol getSymbol() {
		return symbol;
	}
	
}
