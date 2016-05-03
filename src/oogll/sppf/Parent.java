package oogll.sppf;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Parent extends Node {

	private Set<INode> kids;
	
	public Parent(int i, int j) {
		super(i, j);
		//this.kids = new HashSet<INode>();
	}
	
	public boolean add(PackNode pack) {
		if (kids == null) {
			kids = new HashSet<INode>();
		}
		return kids.add(pack);
	}
	
	
	@Override
	public Iterator<INode> iterator() {
		return kids.iterator();
	}
	
	public Set<INode> getKids() {
		return kids;
	}
	
	public PackNode getFirst() {
		for (INode k: getKids()) {
			return (PackNode)k;
		}
		throw new AssertionError("no kids in this node");
	}
	
	public int width() {
		return kids.size();
	}
	
	public boolean isAmbiguous() {
		return width() > 1;
	}

}
