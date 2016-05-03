package oogll.forest;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import oogll.Alt;
import oogll.sppf.EmptyNode;
import oogll.sppf.INode;
import oogll.sppf.ItemNode;
import oogll.sppf.Node;
import oogll.sppf.PackNode;
import oogll.sppf.SymbolNode;
import oogll.sppf.TerminalNode;
import oogll.symbol.Iter;
import oogll.symbol.IterSep;
import oogll.symbol.IterSepStar;
import oogll.symbol.IterStar;
import oogll.symbol.Opt;
import oogll.symbol.Regular;

public class Flattener {

	private Node root;
	private IdentityHashMap<Node, Tree> done = new IdentityHashMap<Node, Tree>();

	public static Tree flatten(Node node) {
		return new Flattener(node).flatten();
	}
	
	private Flattener(Node node) {
		this.root = node;
	}
	
	private Tree flatten() {
		for (Tree t: flattenRec(root)) {
			return t;
		}
		return null;
	}
	
	private List<Tree> elt(Tree t) {
		return Collections.singletonList(t);
	}

	private Set<List<Tree>> one(List<Tree> elt) {
		return singleton(elt);
	}


	private Set<List<Tree>> flattenRegular(Tree t, IdentityHashMap<Tree, Tree> done) {
		if (done.containsKey(t)) {
			return one(elt(t));
		}
		done.put(t, t);
		
		if (t instanceof Symbol && ((Symbol)t).getSymbol() instanceof Regular) {
			Symbol sn = (Symbol)t;
			
			oogll.symbol.Symbol s = sn.getSymbol();
			if (s instanceof Iter) {
				// X+ ::= X | X+ X
				Iter iter = (Iter)s;
				Set<List<Tree>> amb = new HashSet<List<Tree>>();
				for (Tree k: sn) {
					Appl appl = (Appl)k;
					List<Tree> l = new ArrayList<Tree>();
					if (iter.isBase(appl.getAlt())) {
						// X+ ::= X
						Set<List<Tree>> x = flattenRegular(appl.getKids().get(0), done);
						for (List<Tree> kid: x) {
							l.addAll(kid);
						}
					}
					if (iter.isRec(appl.getAlt())) {
						// X+ ::= X+ X
						Set<List<Tree>> x = flattenRegular(appl.getKids().get(0), done);
						assert x.size() == 1;
						for (List<Tree> kid: x) {
							l.addAll(kid);
						}
					}
				}
			}
			if (s instanceof IterStar) {
				
			}
			if (s instanceof IterSep) {
				
			}
			if (s instanceof IterSepStar) {
				
			}
			if (s instanceof Opt) {
				
			}
		}
		return null;
	}

	private Set<Tree> one(Tree tree) {
		return singleton(tree);
	}
	
	private Set<Tree> flattenRec(Node node) {
		if (node instanceof TerminalNode) {
			TerminalNode trm = (TerminalNode)node;
			return one(new Term(trm.getTerminal(), trm.getValue()));
		}
		if (node instanceof EmptyNode) {
			return one(new Nil());
		}
		
		if (node instanceof ItemNode) {
			ItemNode in = (ItemNode)node;
			Set<Tree> amb = new HashSet<Tree>();
			for (INode alt: in.getKids()) {
				amb.addAll(flatten((PackNode)alt));
			}
			return amb;
		}
		
		if (done.containsKey(node)) {
			return one(done.get(node));
		}
		
		SymbolNode sn = (SymbolNode)node;
		
		Symbol symNode = new Symbol(sn.getSymbol());
		done.put(sn, symNode);
		
		Set<Tree> amb = new HashSet<Tree>();
		for (INode alt: sn.getKids()) {
			amb.addAll(flatten((PackNode)alt));
		}
		
		symNode.setAlternatives(amb);
		return one(symNode);
	}

	private Set<Tree> flatten(PackNode pn) {
		Set<Tree> result = new HashSet<Tree>();
		Alt alt = pn.getAlt();
		
		if (pn.getLeft() == null) {
			Set<Tree> amb = new HashSet<Tree>();
			for (Tree t: flattenRec((Node) pn.getRight())) {
				amb.add(new Appl(alt, Collections.singletonList(t)));
			}
			return amb;
		}
		
		Set<Tree> l = flattenRec((Node) pn.getLeft());
		Set<Tree> r = flattenRec((Node) pn.getRight());
		
		
		for (Tree t1: l) {
			List<Tree> args = new ArrayList<Tree>();
			addKids(alt, t1, args);
			for (Tree t2: r) {
				addKids(alt, t2, args);
				result.add(new Appl(alt, args));
			}
		}
		return result;
	}

	private void addKids(Alt alt, Tree t1, List<Tree> args) {
		if (t1 instanceof Appl && alt.equals(((Appl)t1).getAlt())) {
			args.addAll(((Appl)t1).getKids());
		}
		else {
			args.add(t1);
		}
	}

	
}
