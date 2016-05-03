package oogll;

import oogll.sppf.Node;

public interface Parser {
	void parse(int pos, GSS cu, Node cn, GLL ctx);
}
