package oogll.sppf;

public final class EmptyNode extends Leaf {
	
	public EmptyNode(int i) {
		super(i, i);
	}

	@Override
	public String toString() {
		return "<>";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EmptyNode)) {
			return false;
		}
		return equalExtent((Node)obj);
	}
	
	@Override
	public int hashCode() {
		return 3;
	}
}
