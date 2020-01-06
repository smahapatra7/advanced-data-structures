package shibani.risingCity;

public class MinHeap {
	private Building[] heap;
	private int size;
	private int maxsize;

	public static final int FRONT = 1;

	public int getSize() {
		return size;
	}

	public MinHeap(int maxsize) {
		this.maxsize = maxsize;
		this.size = 0;
		heap = new Building[this.maxsize + 1];
		heap[0] = new Building(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	// Function to return the position of
	// the parent for the node currently
	// at pos
	private int parent(int pos) {
		return pos / 2;
	}

	// Function to return the position of the
	// left child for the node currently at pos
	private int leftChild(int pos) {
		return (2 * pos);
	}

	// Function to return the position of
	// the right child for the node currently
	// at pos
	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	// Function that returns true if the passed
	// node is a leaf node
	private boolean isLeaf(int pos) {
		if (2 * pos > size && 2 * pos + 1 > size && pos <= size) {
			return true;
		}
		return false;
	}

	// Function to swap two nodes of the heap
	private void swap(int fpos, int spos) {
		Building tmp;
		tmp = heap[fpos];
		heap[fpos] = heap[spos];
		heap[spos] = tmp;
	}

	// Function to heapify the node at pos
	public void minHeapify(int pos) {

		// If the node is a non-leaf node and greater
		// than any of its child
		if (!isLeaf(pos)) {

			if (heap[leftChild(pos)]!=null && (heap[pos].getExecutedTime() > heap[leftChild(pos)].getExecutedTime())
					|| (heap[rightChild(pos)]!=null && (heap[pos].getExecutedTime() > heap[rightChild(pos)].getExecutedTime()))) {

				// Swap with the left child and heapify
				// the left child
				if(heap[leftChild(pos)]==null) {
					swap(pos, rightChild(pos));
					minHeapify(rightChild(pos));
				}else if (heap[rightChild(pos)]==null) {
					swap(pos, leftChild(pos));
					minHeapify(leftChild(pos));
				}else {
					if (heap[leftChild(pos)].getExecutedTime() < heap[rightChild(pos)].getExecutedTime()) {
						swap(pos, leftChild(pos));
						minHeapify(leftChild(pos));
					}
					// Swap with the right child and heapify
					// the right child
					else if (heap[leftChild(pos)].getExecutedTime() > heap[rightChild(pos)].getExecutedTime()) {
						swap(pos, rightChild(pos));
						minHeapify(rightChild(pos));
					}else {
						int minPos = Math.min(heap[leftChild(pos)].getBuildingNum(), heap[rightChild(pos)].getBuildingNum());
						if(heap[leftChild(pos)].getBuildingNum() == minPos) {
							swap(pos, leftChild(pos));
							minHeapify(leftChild(pos));
						}else {
							swap(pos, rightChild(pos));
							minHeapify(rightChild(pos));
						}
					}
				}
			} else {
				if (heap[leftChild(pos)]!=null && heap[pos].getExecutedTime() == heap[leftChild(pos)].getExecutedTime()) {
					if (heap[pos].getBuildingNum() > heap[leftChild(pos)].getBuildingNum()) {
						swap(pos, leftChild(pos));
						minHeapify(leftChild(pos));
					}
				}

				if (heap[rightChild(pos)]!=null && heap[pos].getExecutedTime() == heap[rightChild(pos)].getExecutedTime()) {
					if (heap[pos].getBuildingNum() > heap[rightChild(pos)].getBuildingNum()) {
						swap(pos, rightChild(pos));
						minHeapify(rightChild(pos));
					}
				}
			}
		}
	}

	// Function to insert a node into the heap
	public void insert(Building element) {
		if (size >= maxsize) {
			return;
		}
		heap[++size] = element;
		int current = size;

	}

	// Function to print the contents of the heap
	public void print() {
		for (int i = 1; i <= size / 2; i++) {
			System.out.print(
					" PARENT : " + heap[i] + " LEFT CHILD : " + heap[2 * i] + " RIGHT CHILD :" + heap[2 * i + 1]);
			System.out.println();
		}
	}

	// Function to build the min heap using
	// the minHeapify
	public void minHeaps() {
		for (int pos = (size / 2); pos >= 1; pos--) {
			minHeapify(pos);
		}
	}

	// Function to remove and return the minimum
	// element from the heap
	public Building remove() {
		Building popped = heap[FRONT];
		heap[FRONT] = heap[size];
		heap[size--] = null;
		minHeaps();
		return popped;
	}

	public Building getMin() {
		return heap[FRONT];
	}

	// Driver code
	public static void main(String[] arg) {
		System.out.println("The Min Heap is ");
		MinHeap minHeap = new MinHeap(2000);
		minHeap.insert(new Building(5, 0, 17));
		minHeap.insert(new Building(4, 0, 13));
		minHeap.insert(new Building(3, 0, 16));
		minHeap.insert(new Building(2, 0, 10));
		minHeap.insert(new Building(1, 0, 84));
		minHeap.print();

		System.out.println("Removed Building: " + minHeap.remove().getBuildingNum());
		minHeap.print();
		System.out.println("The Min val is " + minHeap.remove());
		minHeap.print();
	}
}
