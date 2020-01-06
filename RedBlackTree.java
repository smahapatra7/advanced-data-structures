package shibani.risingCity;

import java.util.ArrayList;

public class RedBlackTree {
	// Program to implement Red-Black Tree.
	static class Node {
		Building data; // data value.
		Node left; // points to left child
		Node right; // points to right child.
		Node parent; // points to parent node.
		char color; // color of the node.

		public Node(Building data, char color) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.color = color;
		}
	}
	
	public RedBlackTree() {
		// TODO Auto-generated constructor stub
	}

	static Node root; // root of the tree.

	// function to create a Node.
	public Node makeNode(Building data) {
		Node node = new Node(data, 'R');
		node.left = new Node(null, 'B');
		node.right = new Node(null, 'B');
		return node;
	}

	// function to add elements in the tree.
	public boolean add(Building data) {
//		System.out.println("Inserting data : " + data);
		Node node = makeNode(data);
		if (root == null) {
			root = node;
			root.color = 'B';
			return true;
		}

		Node temp = root;
		while (temp != null) {
			if (temp.data.getBuildingNum() == data.getBuildingNum()) {
				return false;
			}
			if (temp.data.getBuildingNum() > data.getBuildingNum()) {
				if (temp.left.data == null) {
					temp.left = node;
					node.parent = temp;
					balance(node); // balance the tree.
					return true;
				}
				temp = temp.left;
				continue;
			}
			if (temp.data.getBuildingNum() < data.getBuildingNum()) {
				if (temp.right.data == null) {
					temp.right = node;
					node.parent = temp;
					balance(node); // balance the tree.
					return true;
				}
				temp = temp.right;
			}
		}
		return true;
	}

	// function to remove elements from the tree.
	public void remove(Building data) {
//		System.out.println("Remove data : " + data);
		if (root == null) {
			return;
		}

		// search for the given element in the tree.
		Node temp = root;
		while (temp.data != null) {
			if (temp.data == data) {
				break;
			}
			if (temp.data.getBuildingNum() >= data.getBuildingNum()) {
				temp = temp.left;
				continue;
			}
			if (temp.data.getBuildingNum() < data.getBuildingNum()) {
				temp = temp.right;
				continue;
			}
		}

		// if not found. then return.
		if (temp.data == null) {
			return;
		}

		// find the next greater number than the given data.
		Node next = findNext(temp);

		// swap the data values of given node and next greater number.
		Building t = temp.data;
		temp.data = next.data;
		next.data = t;

		// remove the next node.
		Node parent = next.parent;
		if (parent == null) {
			if (next.left.data == null) {
				root = null;
			} else {
				root = next.left;
				next.parent = null;
				root.color = 'B';
			}
			return;
		}

		if (parent.right == next) {
			parent.right = next.left;
		} else {
			parent.left = next.left;
		}
		next.left.parent = parent;
		String color = Character.toString(next.left.color) + Character.toString(next.color);
		balance(next.left, color); // balance the tree.
	}

	// function to balance the tree IN CASE OF DELETION.
	private static void balance(Node node, String color) {
//		System.out.println("Balancing Node : " + node.data + " Color : " + color);

		// if node is Red-Black.
		if (node.parent == null || color.equals("BR") || color.equals("RB")) {
			node.color = 'B';
			return;
		}

		Node parent = node.parent;

		// get sibling of the given node.
		Node sibling = null;
		if (parent.left == node) {
			sibling = parent.right;
		} else {
			sibling = parent.left;
		}

		Node sibleft = sibling.left; // sibling's left node.
		Node sibright = sibling.right; // siblings right node.

		if(sibleft==null && sibright==null) {
			return;
		}
		
		// sibling sibleft and sibright all are balck.
		if (sibling.color == 'B' && sibleft.color == 'B' && sibright.color == 'B') {
			sibling.color = 'R';
			node.color = 'B';
			String c = Character.toString(parent.color) + Character.toString('B');
			balance(parent, c);
			return;
		}

		// sibling is red.
		if (sibling.color == 'R') {
			if (parent.right == sibling) {
				leftRotate(sibling);
			} else {
				rightRotate(sibling);
			}
			balance(node, color);
			return;
		}

		// sibling is red but one of its children are red.
		if(sibleft==null) {
			return;
		}
		if (parent.left == sibling) {
			if (sibleft.color == 'R') {
				rightRotate(sibling);
				sibleft.color = 'B';
			} else {
				leftRotate(sibright);
				rightRotate(sibright);
				sibright.left.color = 'B';
			}
			return;
		} else {
			if (sibright.color == 'R') {
				leftRotate(sibling);
				sibright.color = 'B';
			} else {
				rightRotate(sibleft);
				leftRotate(sibleft);
				sibleft.right.color = 'B';
			}
			return;
		}
	}

	// function to find the next greater element than the given node.
	private static Node findNext(Node node) {
		Node next = node.right;
		if (next.data == null) {
			return node;
		}
		while (next.left.data != null) {
			next = next.left;
		}
		return next;
	}

	// function to balance the tree IN CASE OF INSERTION>
	public static void balance(Node node) {
//		System.out.println("Balancing Node : " + node.data);

		// if given node is root node.
		if (node.parent == null) {
			root = node;
			root.color = 'B';
			return;
		}

		// if node's parent color is black.
		if (node.parent.color == 'B') {
			return;
		}

		// get the node's parent's sibling node.
		Node sibling = null;
		if (node.parent.parent.left == node.parent) {
			sibling = node.parent.parent.right;
		} else {
			sibling = node.parent.parent.left;
		}

		// if sibling color is red.
		if (sibling.color == 'R') {
			node.parent.color = 'B';
			sibling.color = 'B';
			node.parent.parent.color = 'R';
			balance(node.parent.parent);
			return;
		}

		// if sibling color is black.
		else {
			if (node.parent.left == node && node.parent.parent.left == node.parent) {
				rightRotate(node.parent);
				balance(node.parent);
				return;
			}
			if (node.parent.right == node && node.parent.parent.right == node.parent) {
				leftRotate(node.parent);
				balance(node.parent);
				return;
			}
			if (node.parent.right == node && node.parent.parent.left == node.parent) {
				leftRotate(node);
				rightRotate(node);
				balance(node);
				return;
			}
			if (node.parent.left == node && node.parent.parent.right == node.parent) {
				rightRotate(node);
				leftRotate(node);
				balance(node);
				return;
			}
		}
	}

	// function to perform Left Rotation.
	private static void leftRotate(Node node) {
//		System.out.println("Rotating left  : " + node.data);
		Node parent = node.parent;
		Node left = node.left;
		node.left = parent;
		parent.right = left;
		if (left != null) {
			left.parent = parent;
		}
		char c = parent.color;
		parent.color = node.color;
		node.color = c;
		Node gp = parent.parent;
		parent.parent = node;
		node.parent = gp;

		if (gp == null) {
			root = node;
			return;
		} else {
			if (gp.left == parent) {
				gp.left = node;
			} else {
				gp.right = node;
			}
		}
	}

	// function to perform Right Rotation.
	private static void rightRotate(Node node) {
//		System.out.println("Rotating right : " + node.data);
		Node parent = node.parent;
		Node right = node.right;
		node.right = parent;
		parent.left = right;
		if (right != null) {
			right.parent = parent;
		}
		char c = parent.color;
		parent.color = node.color;
		node.color = c;
		Node gp = parent.parent;
		parent.parent = node;
		node.parent = gp;

		if (gp == null) {
			root = node;
			return;
		} else {
			if (gp.left == parent) {
				gp.left = node;
			} else {
				gp.right = node;
			}
		}
	}

	// function for PreOrder Traversal of the tree.
	private static void preOrder(Node node) {
		if (node.data == null) {
			return;
		}
		System.out.print(node.data + "-" + node.color + " ");
		preOrder(node.left);
		preOrder(node.right);
	}

	// function to display the tree.
	public static void display() {
		if (root == null) {
			System.out.println("Empty Tree");
			return;
		}

		System.out.print("Tree's PreOrder Traversal : ");
		preOrder(root);
		System.out.println();
	}

	public static Building search(Node node, int buildingNumber) {
		if(node == null || node.data == null) {
			return null;
		}
		if(node.data.getBuildingNum()==buildingNumber) {
			return node.data;
		}else if(node.data.getBuildingNum()<buildingNumber) {
			return search(node.right, buildingNumber);
		}else if(node.data.getBuildingNum()>buildingNumber) {
			return search(node.left, buildingNumber);
		}
		return null;
	}
	
	public static ArrayList<Building> searchRange(ArrayList<Building> list, Node node, int startBuilding, int endBuilding){
		if(node==null || node.data == null) {
			return list;
		}
		if(isBetween(node.data.getBuildingNum(), startBuilding, endBuilding)) {
			searchRange(list, node.left, startBuilding, endBuilding);
			list.add(node.data);
			searchRange(list, node.right, startBuilding, endBuilding);
		}else if(node.data.getBuildingNum() >= startBuilding) {
			searchRange(list, node.left, startBuilding, endBuilding);
		}else if(node.data.getBuildingNum() <= startBuilding) {
			searchRange(list, node.right, startBuilding, endBuilding);
		}
		return list;
	}
	
	private static boolean isBetween(int buildingNum, int startBuilding, int endBuilding) {
		if(buildingNum >= startBuilding && buildingNum<=endBuilding)
			return true;
		return false;
	}

	// main function to run the program.
	public static void main(String[] args) {
	}
}
