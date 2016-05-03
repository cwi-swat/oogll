package oogll.forest;

import java.util.Iterator;
import java.util.Set;

import oogll.symbol.Regular;

public class Symbol extends Tree {

	private Set<Tree> alternatives;
	private oogll.symbol.Symbol symbol;

	public Symbol(oogll.symbol.Symbol nt, Set<Tree> alternatives) {
		this.symbol = nt;
		this.alternatives = alternatives;
	}

	public Symbol(oogll.symbol.Symbol symbol) {
		this.symbol = symbol;
	}
	
	public void setAlternatives(Set<Tree> alternatives) {
		this.alternatives = alternatives;
	}

	@Override
	public String getLabel() {
		return symbol.toString();
	}

	@Override
	public String getShape() {
		if (alternatives.size() > 1) {
			return "diamond";
		}
		return "box";
	}
	
	@Override
	public Iterator<Tree> iterator() {
		return alternatives.iterator();
	}

	public oogll.symbol.Symbol getSymbol() {
		return symbol;
	}
	
}
