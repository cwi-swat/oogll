package oogll.sppf;

import java.util.Arrays;
import java.util.Iterator;

public abstract class Leaf extends Node {
	
	protected Leaf(int i, int j) {
		super(i, j);
	}

	@Override
	public String getLabel() {
		return toString();
	}
	
	@Override
	public String getShape() {
		return "plaintext";
	}
	
	@Override
	public Iterator<INode> iterator() {
		return Arrays.<INode>asList().iterator();
	}

}
