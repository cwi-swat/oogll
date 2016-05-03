package oogll.sppf;

import java.util.Arrays;
import java.util.Iterator;

import oogll.Alt;
import oogll.Item;


public class PackNode implements INode {

	private final int pivot;
	private final Node left;
	private final Node right;
	private final Item item;

	public PackNode(Item item, int pivot, Node right) {
		this(item, pivot, null, right);
	}

	public PackNode(Item item, int pivot, Node left, Node right) {
		this.item = item;
		this.pivot = pivot;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String getLabel() {
		return pivot + "";
	}

	@Override
	public String getShape() {
		return "point";
	}

	@Override
	public Iterator<INode> iterator() {
		if (left == null) {
			return Arrays.<INode>asList(right).iterator();
		}
		return Arrays.<INode>asList(left, right).iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PackNode)) {
			return false;
		}
		PackNode packNode = (PackNode)obj;
		return item.equals(packNode.item) && pivot == packNode.pivot; 
	}
	
	@Override
	public int hashCode() {
		return item.hashCode() * 7 + pivot * 19; 
	}

	public INode getLeft() {
		return left;
	}
	
	public INode getRight() {
		return right;
	}

	public Alt getAlt() {
		return item.getAlt();
	}
	
	
}
