package textgen;

import java.util.AbstractList;


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
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
        if (element == null) {
            throw new NullPointerException();
        }

        LLNode<E> n = new LLNode<E>(element);
		
		// TODO: Implement this method
        if (size != 0) {
            tail.next = n;
            n.prev = tail;
            tail = n;
        	
        } else {
        	head = n;
        	tail = n;
        }

        size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// Encapsulation of method. Not only does this help keep code coherency, but it avoids the bad practice of only getting the data attribute when getting a single node
		return getAt(index).data;		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (element == null) {
            throw new NullPointerException();
        }
		
		// Two behaviors. If the list is not empty, run first condition. If it  is, run second.
		if (size > 0 && index < size) {
			
            LLNode<E> thisNode = getAt(index);
            
            // Intercalate new node just before old thisNode and after thisNode.prev
            LLNode<E> newNode = new LLNode<>(thisNode.prev, thisNode, element);
            
            if (thisNode.prev != null) {
            	thisNode.prev.next = newNode;
            } else {
                head = thisNode;
            }
            thisNode.prev = newNode;
            size++;
			
		} else {
            add(element);
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
		LLNode<E> n = getAt(index);

        if (n.prev != null) {
            n.prev.next = n.next;
        } else {
            head = n.next;
        }
        
        if (n.next != null) {
            n.next.prev = n.prev;
        } else {
            tail = n.prev;
        }
        
        size--;
        return n.data;

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
        if (element == null) {
            throw new NullPointerException();
        }
			
        LLNode<E> thisNode = getAt(index);

        LLNode<E> altNode = new LLNode(thisNode.prev, thisNode.next, element);

        if (thisNode.prev != null) {
        	thisNode.prev.next = altNode;
        } else {
            head = altNode;
        }
        if (thisNode.next != null) {
        	thisNode.next.prev = altNode;
        } else {
            tail = altNode;
        }
        return thisNode.data;
	}

    private LLNode<E> getAt(int index) {
    	
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        
        LLNode<E> n = head;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        
        return n;
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

    public LLNode(LLNode<E> prev, LLNode<E> next, E data) {
        this.prev = prev;
        this.next = next;
        this.data = data;
    }

}
