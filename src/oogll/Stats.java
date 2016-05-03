package oogll;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import oogll.sppf.INode;
import oogll.sppf.Leaf;
import oogll.sppf.Node;
import oogll.sppf.PackNode;
import oogll.sppf.Parent;
import oogll.symbol.Terminal;

public class Stats {

	private final Node result;
	private final Map<GSS, GSS> gssNodes;
	private final Map<Node, Node> nodes;

	private int numOfSPPFEdges = 0;
	private int numOfPackNodes = 0;
	private int numOfNodes = 0;
	private int numOfTerminals = 0;
	private int numOfNonTerminals = 0;
	private int numOfSinglePacks = 0;
	private int numOfNonSingleKids = 0; 
	private int numOfPackNodesMade = 0;
	private int numOfEdgesMade = 0;
	private int numOfGSSEdges = 0;
	
	public Stats(Node result, Map<GSS, GSS> gssNodes, Map<Node, Node> nodes) {
		this.result = result;
		this.gssNodes = gssNodes;
		this.nodes = nodes;
		compute();
	}
	
	private void compute() {
		Set<INode> done = new HashSet<INode>();
		Set<INode> todo = new HashSet<INode>();
		todo.add(result);
		while (!todo.isEmpty()) {
			INode current = todo.iterator().next();
			todo.remove(current);
			done.add(current);
			sppfStats(done, todo, current);
		}
		sppfEdgeStats();
		gssStats();
	}

	private void sppfEdgeStats() {
		for (INode node: nodes.keySet()) {
			if (node instanceof Parent && ((Parent)node).getKids() == null) {
				continue;
			}
			if (node instanceof Leaf) {
				continue;
			}
			Parent parent = (Parent)node;
			numOfPackNodesMade += parent.width();
			for (INode t: parent.getKids()) {
				numOfEdgesMade++;
				PackNode pn = ((PackNode)t);
				if (pn.getLeft() != null) {
					numOfEdgesMade++;
				}
				numOfEdgesMade++;
			}
		}
	}

	private void gssStats() {
		for (GSS n: gssNodes.keySet()) {
			for (Node nod: n.getEdges().keySet()) {
				numOfGSSEdges += n.getEdgesFor(nod).size();
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "#GSS: " + gssNodes.size();
		s += "\n#GSSEdges: " + numOfGSSEdges;
		s += "\n#numOfNodesMade: " + nodes.size();
		s += "\n#numOfPackNodesMade: " + numOfPackNodesMade;
		s += "\n#numOfEdgesMade: " + numOfEdgesMade;
		return s;
	}

	private void sppfStats(Set<INode> done, Set<INode> todo, INode current) {
		numOfNodes++;
		if (current instanceof Terminal) {
			numOfTerminals++;
		}
		else {
			numOfNonTerminals++;
		}
		boolean first = true;
		for (INode p: current) {
			if (first) {
				first = false;
			}
			else {
				numOfNonSingleKids++;
			}
			numOfSPPFEdges++;
			PackNode pn = (PackNode)p;
			numOfPackNodes++;
			if (pn.getLeft() != null) {
				numOfSPPFEdges++;
				if (!done.contains(pn.getLeft())) {
					todo.add(pn.getLeft());
				}
			}
			else {
				numOfSinglePacks++;
			}
			numOfSPPFEdges++;
			if (!done.contains(pn.getRight())) {
				todo.add((INode) pn.getRight());
			}
		}
	}


}
