package oogll;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import oogll.sppf.Node;

public class Todo {
	// implementing R and U_i
	
	private final ArrayDeque<Descriptor> todo;
	private final Set<Configuration>[] done;
	
	@SuppressWarnings("unchecked")
	public Todo(int size) {
		this.todo = new ArrayDeque<Descriptor>();
		this.done = new Set[size];   
	}
	
	
	public static class Configuration {
		final Parser parser;
		final GSS gss;
		final Node node;
		private final int hashCode;
		
		public Configuration(Parser parser, GSS gss, Node node) {
			this.parser = parser;
			this.gss = gss;
			this.node = node;
			this.hashCode = parser.hashCode() * 17 + gss.hashCode() * 19 + (node == null ? 1 : node.hashCode()) * 11;
		}
		
		@Override
		public String toString() {
			return "<" + parser + ", " + gss + ", " + node + ">";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Configuration)) {
				return false;
			}
			Configuration d = (Configuration)obj;
			return parser.equals(d.parser) && gss == d.gss && 
				(node == null ? node == d.node : node.equals(d.node));
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}

		public void parse(int pos, GLL driver) {
			parser.parse(pos, gss, node, driver);
		}
	}
	
	public static class Descriptor {
		final Configuration desc;
		final int pos;
		private final int hashCode;
		
		public Descriptor(Configuration desc, int pos) {
			this.desc = desc;
			this.pos = pos;
			this.hashCode = desc.hashCode() * 7 + pos * 13;
		}
		
		@Override
		public String toString() {
			return "<" + desc + ", " + pos + ">";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Descriptor)) {
				return false;
			}
			Descriptor d = (Descriptor)obj;
			return desc.equals(d.desc) && pos == d.pos;
		}
		
		@Override
		public int hashCode() {
			return hashCode;
		}

		public void parse(GLL driver) {
			desc.parse(pos, driver);
		}
		

	}

	
	public void add(Parser parser, GSS u, int pos, Node w) {
		if (done[pos] == null) {
			done[pos] = new HashSet<Configuration>();
		}
		Configuration desc = new Configuration(parser, u, w);
		if (!done[pos].contains(desc)) {
			done[pos].add(desc);
			todo.add(new Descriptor(desc, pos));
		}
	}
	
	public void clear() {
		todo.clear();
		Arrays.fill(done, null);
	}

	public boolean isEmpty() {
		return todo.isEmpty();
	}

	public Descriptor next() {
		return todo.pop();
	}
	
	

	
}
