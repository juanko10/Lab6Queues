package queue;


public class SLLQueue<E> implements Queue<E> {

	private Node<E> first, last;   // references to first and last node
	private int size; 
	
	private static class Node<E> {   // Inner class for nodes. 
		private E element; 
		private Node<E> next;
		public Node(E e) {
			this.element = e;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E element) {
			this.element = element;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		} 
	}	
	
	public SLLQueue() {           // initializes instance as empty queue
		first = last = null; 
		size = 0; 
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public E first() {
		if (isEmpty()) return null;
		return first.getElement(); 
	}
	public E dequeue() {
		if (isEmpty()) return null;		
		E temp = first.getElement();
		first = first.getNext();
		size--;
		return temp;
	}
	public void enqueue(E e) {
		if (size == 0) 
			first = last = new Node<E>(e); 
		else { 
			Node<E> temp = new Node<E>(e);
			last.setNext(temp);
			last = temp;
		}
		size++; 
	}



	//JUST FOR TESTING
	@Override
	public void showReverse() { 
	    if (size == 0)
		   System.out.println("Queue is empty."); 
		else
		   recSR(first);
    } 
    private void recSR(Node<E> f) { 
		if (f != null) { 
		   recSR(f.getNext()); 
		   System.out.println(f.getElement()); 
	     } 
    } 

}
