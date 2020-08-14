package textgen;

import java.util.AbstractList;
import java.util.Iterator;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		LLNode<E> n = new LLNode<E>(element);
		
		// TODO: Implement this method
		try {
			n.next = tail.prev.next;
			n.prev = tail.prev;
			tail.prev.next = n;
			tail.prev = n;
			size++;
			return true;
		} catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		LLNode<E> fn = head;
		
		// TODO: Implement this method.
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
			
		if (index <= size && index >= 0) {			
	        for (int i = 0; i <= index; i++) {
	        	fn = fn.next;
	        }
		}
		
		return fn.data;
		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		try {
			LLNode<E> n = new LLNode<E>(element);

			LLNode<E> fn = head;

			
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}

			if (index <= size && index >= 0) {			
		        for (int i = 0; i <= index; i++) {
	        		fn = fn.next;

    				if (i == index) {
    					n.next = fn;
    					n.prev = fn.prev;
    					fn.prev.next = n;
    					fn.prev = n;
    					size++;
		        	}
		        }
			}
		} catch(Exception e) {
			throw new IndexOutOfBoundsException();
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode<E> fn = head;

		try {

			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}

			if (index <= size && index >= 0) {			
		        for (int i = 0; i <= index; i++) {
	        		fn = fn.next;
	        		
    				if (i == index) {
    					fn.next.prev = fn.prev;
    					fn.prev.next = fn.next;
    					fn.next = null;
    					fn.prev = null;
    					
    					size--;
    					return fn.data;
		        	}
		        }
			}

		} catch(Exception e) {
			throw new IndexOutOfBoundsException();
		}

		E element = fn.data;

		return element;

	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		try {
			LLNode<E> n = new LLNode<E>(element);

			LLNode<E> fn = head;
			
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}

			if (index <= size && index >= 0) {			
		        for (int i = 0; i <= index; i++) {
	        		fn = fn.next;

    				if (i == index) {
    					fn.data = element;
		        	}
		        }
			}
		} catch(Exception e) {
			throw new IndexOutOfBoundsException();
		}

		return element;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
