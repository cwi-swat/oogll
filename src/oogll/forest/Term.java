package oogll.forest;

import java.util.Iterator;

import oogll.symbol.Terminal;

public class Term extends Tree {
	
	private Terminal terminal;
	private String value;

	public Term(Terminal terminal, String value) {
		this.terminal = terminal;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return terminal + ": " + value;
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public String getShape() {
		return "plaintext";
	}	

}
