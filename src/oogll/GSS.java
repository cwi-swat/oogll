package oogll;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oogll.sppf.Node;

public class GSS {
	private final Item item;
	private final int k;
	private final Map<Node, Set<GSS>> edges = new HashMap<Node, Set<GSS>>();

	public GSS(Item parser, int k) {
		this.item = parser;
		this.k = k;
	}

	public Item getItem() {
		return item;
	}
	
	public Map<Node, Set<GSS>> getEdges() {
		return edges;
	}
	
	public boolean addEdge(Node node, GSS gss) {
		if (!edges.containsKey(node)) {
			edges.put(node, new HashSet<GSS>());
		}
		return edges.get(node).add(gss);
	}
	
	public Set<GSS> getEdgesFor(Node node) {
		return edges.get(node);
	}
	
	@Override
	public String toString() {
		return "{" + item + ", " + k + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GSS)) {
			return false;
		}
		GSS gss = (GSS)obj;
		return item.equals(gss.item) && k == gss.k;
	}
	
	@Override
	public int hashCode() {
		return item.hashCode() * 3 + k * 17;
	}
	
}
