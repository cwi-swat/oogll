package oogll.sppf;

import oogll.Alt;
import oogll.Item;

public class ItemNode extends Parent {

	private final Item item;

	public ItemNode(Item item, int i, int j) {
		super(i, j);
		this.item = item;
	}

	@Override
	public String getLabel() {
		return item.toString();
	}

	@Override
	public String getShape() {
		return "ellipse";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ItemNode)) {
			return false;
		}
		ItemNode node = (ItemNode)obj;
		return item.equals(node.item) && equalExtent(node);
	}
	
	@Override
	public int hashCode() {
		return item.hashCode() * 41 + super.hashCode();
	}

	public Alt getAlt() {
		return item.getAlt();
	}
	
}
