package oogll.sppf;

import java.util.IdentityHashMap;

import oogll.symbol.Symbol;

public abstract class Node implements INode {

	private static class Builder {
		private final INode tree;
		private int n;
		private final IdentityHashMap<INode, Integer> memo = new IdentityHashMap<INode, Integer>();

		public Builder(INode tree) {
			this.tree = tree;
			this.n = 0;
		}
	
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			buildString(tree, builder);
			return builder.toString();
		}
		
		public String toDot() {
			StringBuilder builder = new StringBuilder();
			builder.append("digraph forest {\n");
			buildDot(tree, builder);
			builder.append("}\n");
			return builder.toString();
		}

		private void buildString(INode tree, StringBuilder builder) {
			if (!memo.containsKey(tree)) {
				memo.put(tree, ++n);
				builder.append("@" + n + ":" + tree.getLabel());
				builder.append("[");
				for (INode t: tree) {
					buildString(t, builder);
					builder.append(" ");
				}
				builder.append("]");
				return;
			}
			builder.append("#" + memo.get(tree));
		}
		
		private void buildDot(INode tree, StringBuilder builder) {
			if (!memo.containsKey(tree)) {
				memo.put(tree, ++n);
				int me = n;
				builder.append("node" + n + " [label=\"" + tree.getLabel() + "\", shape=" + tree.getShape() + "]\n");
				for (INode t: tree) {
					buildDot(t, builder);
					builder.append("\n");
					builder.append("node" + me + " -> node" + memo.get(t) + "\n");
				}
				return;
			}
		}
	}
	
	
	private final int leftExtent;
	private final int rightExtent;
	
	protected Node(int i, int j) {
		this.leftExtent = i;
		this.rightExtent = j;
	}
	
	public int getLeftExtent() { 
		return leftExtent; 
	}

	public int getRightExtent() {
		return rightExtent;
	}
	
	protected boolean equalExtent(Node node) {
		return leftExtent == node.leftExtent && rightExtent == node.rightExtent;
	}
	
	@Override
	public int hashCode() {
		return leftExtent * 3 + rightExtent * 5;
	}
	
	public boolean hasSymbol(Symbol symbol) {
		return false;
	}
	
	@Override
	public String toString() {
		return new Builder(this).toString();
	}
	
	public String toDot() {
		return new Builder(this).toDot();
	}

	public boolean covers(int i, int j) {
		return getLeftExtent() == i && getRightExtent() == j;
	}

	
}
