package oogll;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import oogll.Todo.Descriptor;
import oogll.sppf.EmptyNode;
import oogll.sppf.ItemNode;
import oogll.sppf.Node;
import oogll.sppf.PackNode;
import oogll.sppf.Parent;
import oogll.sppf.SymbolNode;
import oogll.sppf.TerminalNode;
import oogll.symbol.NonTerminal;
import oogll.symbol.Terminal;

public class GLL {
	
	private Todo todo;
	private final GSS start;
	
	private final Map<GSS, Set<Node>> toPop;
	private final NonTerminal startSymbol;

	private final Map<GSS,GSS> gssNodes = new HashMap<GSS, GSS>();
	private final Map<Node,Node> nodes = new HashMap<Node, Node>();
	
	private String src;
		
	
	public GLL(NonTerminal symbol) {
		this.startSymbol = symbol;
		NonTerminal s = new NonTerminal("S'");
		Alt alt = new Alt(symbol);
		s.addAlt(alt);
		this.start = makeGSS(new Item(alt, 1), 0);
		this.toPop = new HashMap<GSS, Set<Node>>();
	}

	public String getSrc() {
		return src;
	}
	

	public Node parse(String src) {
		init(src);

		for (Alt alt: startSymbol) {
			add(alt, start, 0, null);
		}
		
		while (!todo.isEmpty()) {
			Descriptor desc = todo.next();
			desc.parse(this);
		}

		return getResult();
	}

	private Node getResult() {
		for (Node n: nodes.keySet()) {
			if (n.hasSymbol(startSymbol) && n.covers(0, src.length())) {
				return n;
			}
		}
		return null;
	}
	
	private void init(String src) {
		this.src = src;
		todo = new Todo(src.length() + 1);
		toPop.clear();
		nodes.clear();
		gssNodes.clear();
		gssNodes.put(start, start);
	}

	public void add(Parser parser, GSS cu, int ci, Node cn) {
		todo.add(parser, cu, ci, cn);
	}
	
	public void pop(GSS cu, Node cn, int ci) {
		if (!cu.equals(start)) {
			if (!toPop.containsKey(cu)) {
				toPop.put(cu, new HashSet<Node>());
			}
			toPop.get(cu).add(cn);
			Item cnt = cu.getItem();
			for (Entry<Node, Set<GSS>> e: cu.getEdges().entrySet()) {
				Node w = e.getKey();
				for (GSS u: e.getValue()) {
					Node x = getNodeP(cnt, w, cn);
					add(cnt, u, ci, x);
				}
			}
		}
	}

	public GSS create(Item item, GSS cu, Node cn, int ci) {
		Node w = cn;
		GSS v = makeGSS(item, ci);
		
		if (v.addEdge(w, cu)) {
			if (toPop.containsKey(v)) {
				for (Node z: toPop.get(v)) {
					Node x = getNodeP(item, w, z);
					add(item, cu, z.getRightExtent(), x);
				}
			}
		}
		return v;
	}

	private GSS makeGSS(Item item, int ci) {
		GSS v = new GSS(item, ci);
		if (!gssNodes.containsKey(v)) {
			gssNodes.put(v, v);
		}
		return gssNodes.get(v);
	}

	public char charAt(int pos) {
		return src.charAt(pos);
	}
	
	public Node getNodeP(Item item, Node current, Node next) {
		if (item.getDot() == 1 && item.arity() > 1) {
			assert current == null;
			return next;
		}

		int k = next.getLeftExtent();  // next (k, i)
		int i = next.getRightExtent();
		int j = k;

		if (current != null) {
			j = current.getLeftExtent(); // current (j, k)
			assert current.getRightExtent() == k;
		}
		
		Parent y = item.isAtEnd() 
				? makeSymbolNode(item.getNonTerminal(), j, i)  
				: makeItemNode(item, j, i);
		y.add(new PackNode(item, k, current, next));
		return y;
	}

	public TerminalNode getNodeT(Terminal terminal, String value, int i, int l) {
		TerminalNode y = new TerminalNode(terminal, value, i, i + l);
		if (!nodes.containsKey(y)) {
			nodes.put(y, y);
		}
		return (TerminalNode) nodes.get(y);
	}
	
	public EmptyNode getNodeE(int i) {
		EmptyNode y = new EmptyNode(i);
		if (!nodes.containsKey(y)) {
			nodes.put(y, y);
		}
		return (EmptyNode) nodes.get(y);
	}
	
	private ItemNode makeItemNode(Item item, int i, int j) {
		ItemNode y = new ItemNode(item, i, j);
		if (!nodes.containsKey(y)) {
			nodes.put(y, y);
		}
		return (ItemNode) nodes.get(y);
	}

	private SymbolNode makeSymbolNode(NonTerminal nonTerminal, int i, int j) {
		SymbolNode y = new SymbolNode(nonTerminal, i, j);
		if (!nodes.containsKey(y)) {
			nodes.put(y, y);
		}
		return (SymbolNode) nodes.get(y);
	}

	
	public boolean isAtEnd(int pos) {
		return pos >= getSrc().length();
	}

	public void printStatistics() {
		Node theForest = getResult();
		Stats stats = new Stats(theForest, gssNodes, nodes);
		System.out.println(stats.toString());	
	}

}
