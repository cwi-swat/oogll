package oogll.symbol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oogll.Alt;

public class NonTerminal implements Symbol, Iterable<Alt> {

	// TODO: put in factory
	
	// Using this table makes everything really really slow...!
	// (this was to ensure clients do not make duplicate nonterminals
	// so that pointer equality could be used).
//	private static Map<String,NonTerminal> nts = new ShareableHashMap<String, NonTerminal>();
//	public static NonTerminal nt(String name) {
//		if (!nts.containsKey(name)) {
//			nts.put(name, new NonTerminal(name));
//		}
//		return nts.get(name);
//		return new NonTerminal(name);
//	}
	
	private final String name;
	private final List<Alt> alts = new ArrayList<Alt>();
	private int hashCode;
	
	public NonTerminal(String name) {
		this.name = name;
		this.hashCode = name.hashCode();
	}

	public void addAlt(Alt alt) {
		getAlts().add(alt);
		alt.setNonTerminal(this);
	}


	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public Iterator<Alt> iterator() {
		return getAlts().iterator();
	}

	protected List<Alt> getAlts() {
		return alts;
	}

	public String getName() {
		return name;
	}
	
}
