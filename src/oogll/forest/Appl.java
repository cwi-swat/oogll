package oogll.forest;

import java.util.Iterator;
import java.util.List;

import oogll.Alt;

public class Appl extends Tree {

	private Alt alt;
	private List<Tree> kids;

	public Appl(Alt alt, List<Tree> kids) {
		this.alt = alt;
		this.kids = kids;
	}

	@Override
	public String getLabel() {
		return alt.toString();
	}

	@Override
	public String getShape() {
		return "ellipse";
	}
	
	@Override
	public Iterator<Tree> iterator() {
		return kids.iterator();
	}

	public Alt getAlt() {
		return alt;
	}

	public List<Tree> getKids() {
		return kids;
	}
	
}
