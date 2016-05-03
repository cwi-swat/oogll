package oogll;

import java.util.IdentityHashMap;

public abstract class Tree implements Iterable<Tree> {

	public abstract String getDotLabel();
	public abstract String getDotShape();

	private static class Builder {
		private final Tree tree;
		private int n;
		private final IdentityHashMap<Tree, Integer> memo = new IdentityHashMap<Tree, Integer>();

		public Builder(Tree tree) {
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

		private void buildString(Tree tree, StringBuilder builder) {
			if (!memo.containsKey(tree)) {
				memo.put(tree, ++n);
				builder.append("@" + n + ":" + tree.getDotLabel());
				builder.append("[");
				for (Tree t: tree) {
					buildString(t, builder);
					builder.append(" ");
				}
				builder.append("]");
				return;
			}
			builder.append("#" + memo.get(tree));
		}
		
		private void buildDot(Tree tree, StringBuilder builder) {
			if (!memo.containsKey(tree)) {
				memo.put(tree, ++n);
				int me = n;
				builder.append("node" + n + " [label=\"" + tree.getDotLabel() + "\", shape=" + tree.getDotShape() + "]\n");
				for (Tree t: tree) {
					buildDot(t, builder);
					builder.append("\n");
					builder.append("node" + me + " -> node" + memo.get(t) + "\n");
				}
				return;
			}
		}
	}
	
	@Override
	public String toString() {
		return new Builder(this).toString();
	}
	
	public String toDot() {
		return new Builder(this).toDot();
	}
	
}
