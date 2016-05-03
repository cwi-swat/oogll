package oogll.symbol;

import oogll.GLL;

public interface Terminal extends Symbol {

	public String match(int pos, GLL driver);
	
}
