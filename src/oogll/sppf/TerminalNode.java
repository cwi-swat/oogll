package oogll.sppf;

import oogll.symbol.Terminal;


public class TerminalNode extends Leaf {

	private Terminal terminal;
	private String value;

	public TerminalNode(Terminal terminal, String value, int i, int j) {
		super(i, j);
		this.terminal = terminal;
		this.value = value;
	}
	
	public Terminal getTerminal() {
		return terminal;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return terminal + ": " + getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TerminalNode)) {
			return false;
		}
		TerminalNode node = (TerminalNode)obj;
		return terminal.equals(node.terminal) && equalExtent(node);
	}
	
	@Override
	public int hashCode() {
		return terminal.hashCode() * 7 + super.hashCode();
	}
	
}
