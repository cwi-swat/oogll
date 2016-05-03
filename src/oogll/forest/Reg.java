package oogll.forest;

import java.util.List;

import oogll.symbol.Regular;

public class Reg extends Appl {

	private Regular symbol;

	public Reg(Regular symbol, List<Tree> kids) {
		super(null, kids);
		this.symbol = symbol;
	}

	@Override
	public String getLabel() {
		return symbol.toString();
	}
	
	@Override
	public String getShape() {
		return "house"; //"folder";
	}

}
