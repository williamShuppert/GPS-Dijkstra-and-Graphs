import java.util.NoSuchElementException;

public class MinHeapPriorityQueue<T extends Comparable<? super T>>  {
	private T[] heap;
	private int size;
	private static final int DEFAULT_SIZE = 10;
	
	public MinHeapPriorityQueue() {
		this(DEFAULT_SIZE);
	}
	
	public MinHeapPriorityQueue(int size) {
		heap = (T[]) new Comparable[++size];
		clear();
	}
	
	public MinHeapPriorityQueue(T[] entries) {
		this(entries.length);
		size = entries.length;
		for (int i = 0; i < entries.length; i++)
			heap[i + 1] = entries[i];
		for (int i = size / 2; i > 0; i--)
			reheapDown(i);
	}
	
	public boolean isEmpty() {
		return heap[1] == null;
	}

	public boolean isFull() {
		return size == heap.length - 1;
	}

	public void clear() {
		while (size > 0) {
			heap[size] = null;
			size--;
		}
	}

	public int size() {
		return size;
	}

	public void add(T newEntry) {
		verifyCapacity();
		heap[++size] = newEntry;
		reheapUp(size);
	}

	public T peek() {
		return heap[1];
	}

	public T remove() {
		if (isEmpty()) throw new NoSuchElementException();
		
		T ret = heap[1];
		heap[1] = heap[size];
		heap[size--] = null;
		
		reheapDown(1);
		return ret;
	}
	
	public String toString() {
		String str = "";
		for (int i = 1; i < heap.length; i++)
			str += ", " + (heap[i] != null ? heap[i].toString() : "null");
		return "[null" + str + "]";
	}
	
//	Helper Methods
	
	private void reheapUp(int index) {
		int parentIndex = index / 2;
	
		while (parentIndex > 0 && heap[index].compareTo(heap[parentIndex]) < 0) {
			swap(index, parentIndex);
			index = parentIndex;
			parentIndex = index / 2;
		}
	}
	
	private void reheapDown(int index) {		
		int childIndex = getMinChildIndex(index);
		if (childIndex == -1) return;
		if (heap[childIndex].compareTo(heap[index]) >= 0) return;

		swap(index, childIndex);
		reheapDown(childIndex);
	}
	
	private int getMinChildIndex(int index) {
		int left = index * 2;
		int right = index * 2 + 1;
		
		if (left >= heap.length || heap[left] == null)
			return -1;
		
		if (right >= heap.length || heap[right] == null)
			return left;
		
		if (heap[left].compareTo(heap[right]) < 0)
			return left;
		
		return right;
	}
	
	private void swap(int i1, int i2) {
		T tmp = heap[i1];
		heap[i1] = heap[i2];
		heap[i2] = tmp;
	}
	
	private void verifyCapacity() {
		// double length if not enough space
		if (!isFull()) return;
		
		T[] old = heap;
		heap = (T[]) new Comparable[heap.length * 2];
		
		for (int i = 0; i < old.length; i++)
			heap[i] = old[i];
	}

}
