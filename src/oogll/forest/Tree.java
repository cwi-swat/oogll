package oogll.forest;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;

public abstract class Tree implements Iterable<Tree> {

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
			builder.append("digraph forest {\nordering=out;\n");
			buildDot(tree, builder);
			builder.append("}\n");
			return builder.toString();
		}

		private void buildString(Tree tree, StringBuilder builder) {
			if (!memo.containsKey(tree)) {
				memo.put(tree, ++n);
				builder.append("@" + n + ":" + tree.getLabel());
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
				builder.append("node" + n + " [label=\"" + tree.getLabel() + "\", shape=" + tree.getShape() + "]\n");
				for (Tree t: tree) {
					buildDot(t, builder);
					builder.append("\n");
					builder.append("node" + me + " -> node" + memo.get(t) + "\n");
				}
				return;
			}
		}
	}
	
	public abstract String getLabel();
	public abstract String getShape();
	
	@Override
	public String toString() {
		return new Builder(this).toString();
	}
	
	@Override
	public Iterator<Tree> iterator() {
		return Collections.<Tree>emptyList().iterator();
	}
	public String toDot() {
		return new Builder(this).toDot();
	}
	

	
}
