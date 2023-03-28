public class MinPQ {

	private Element[] queue;
	private int size;

	/**
	 * Creates an empty MinPQ with the default capacity of 10 (11).
	 */
	public MinPQ() {
		queue = new Element[11]; // Size 11 since the index 0 is ignored by a binary heap.
		size = 0;
	}


	/**
	 * Returns the current size of the MinPQ.
	 * @return The current size of the MinPQ.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Returns if the MinPQ is empty.
	 * @return True if the MinPQ is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}


	/**
	 * Inserts an element into the MinPQ based on priority.
	 * @param val The value to insert.
	 * @param priority The priority of the value.
	 */
	public void insert(String val, int priority){
		if(size+1 == queue.length){
			resize(queue.length*2);
		}

		size++;

		queue[size] = new Element(val, priority);
		swim(); // Ensure the character is in the correct position.
	}

	/**
	 * Returns the current priority of the minimum element.
	 * @return The priority of the minimum element.
	 */
	public int getMinPriority() {
		return queue[1].priority;
	}


	/**
	 * Returns and removes the current minimum element.
	 * @return The value of the minimum element.
	 */
	public String delMin() {

		String character = queue[1].val;

		sink();

		if(size < queue.length/4 && queue.length/2 >= 11){
			resize(queue.length/2);
		}

		return character;
	}

	/**
	 * Resizes the underlying array to a specified size.
	 * @param newSize The size to resize the array to.
	 */
	private void resize(int newSize){
		Element[] newQueue = new Element[newSize];
		for(int i = 0; i < Math.min(queue.length, newQueue.length); i++){
			newQueue[i] = queue[i];
		}
		queue = newQueue;
	}

	/**
	 * Takes an element from the end of the queue and moves it to the proper position.
	 */
	private void swim(){
		int index = size;
		while(index != 1 && queue[index].priority < queue[index/2].priority) { // Run until in proper position
			swap(index, index/2);										 // or at the front of queue.
			index = index/2;
		}
	}

	/**
	 * Moves the last element to the front of the Queue, then move it backwards into
	 * the proper position in the queue.
	 */
	private void sink(){
		int index = 1;

		queue[1] = queue[size];
		queue[size] = null;
		size--;

		while(index != 0){ // Index = 0 means the element is in the proper location
			int swap = 0;
			int c1 = index*2; // Calculate the index of child 1.
			int c2 = index*2+1; // Calculate the index of child 2.

			boolean c1MoreUrgent = false; // Used to ensure the final check is only happens when both
			boolean c2MoreUrgent = false; // children are more urgent than the parent.

			if(c1 <= size && queue[index].priority > queue[c1].priority){
				swap = c1;
				c1MoreUrgent = true;
			}
			if(c2 <= size && queue[index].priority > queue[c2].priority){
				swap = c2;
				c2MoreUrgent = true;
			}

			if(c1MoreUrgent && c2MoreUrgent && queue[c1].priority < queue[c2].priority){
				swap = c1; // Check which child is more urgent between two more urgent children
			}

			if(swap != 0) { // Swaps only when a candidate is found
				swap(index, swap);
			}
			index = swap;
		}
	}

	/**
	 * Swaps two elements in the queue.
	 * @param index1 The first index to swap.
	 * @param index2 The second index to swap.
	 */
	private void swap(int index1, int index2){
		Element temp = queue[index2];
		queue[index2] = queue[index1];
		queue[index1] = temp;
	}

	/**
	 * A class representing an element in the queue.
	 */
	private static class Element{
		public String val;
		public int priority;

		/**
		 * Creates an element with a given value and priority.
		 * @param val The new value.
		 * @param priority The new priority.
		 */
		public Element(String val, int priority) {
			this.priority = priority;
			this.val = val;
		}
	}
}