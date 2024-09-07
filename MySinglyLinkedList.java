package assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException ;

public class MySinglyLinkedList<E> implements Iterable<E> {

	public int length ;
	public SNode head ;
	public SNode tail ;

	public class SNode {
		protected E element ;
		protected SNode next ;
	}

	/* ADD YOUR CODE HERE */
	public MySinglyLinkedList() {
		length = 0;
		head = null;
		tail = null;
	}

	public boolean addFirst(E element) {

		if (element == null) {
			return false;
		}

		SNode newNode = new SNode();
		newNode.element = element;
		newNode.next = head;

		if (head == null) {
			tail = newNode;
		}
		head = newNode;
		length++;
		return true;
	}

	public boolean addLast(E element) {

		if (element == null) {
			return false;
		}

		SNode newNode = new SNode();
		newNode.element = element;
		newNode.next = null;

		if (head == null) {
			head = newNode;
		}
		else {
			tail.next = newNode;
		}
		tail = newNode;
		length++;
		return true;
	}

	public E removeFirst(){
		if (isEmpty()){
			throw new NoSuchElementException("List is empty");
		}
		E removedElement = head.element;
		head = head.next;
		length--;

		if (length == 0) {
			tail = null;
		}
		return removedElement;
	}

	public E removeLast(){

		if (isEmpty()){
			throw new NoSuchElementException("List is empty");
		}

		if (length == 1){
			return removeFirst();
		}
		SNode current = head;
		while (current.next != tail){
			current = current.next;
		}
		E removedElement = tail.element;
		tail = current;
		tail.next = null ;
		length--;
		return removedElement;
	}

	public E peekFirst(){
		if(isEmpty()){
			throw new NoSuchElementException("The list is empty");
		}
		return head.element;
	}
	public E peekLast(){
		if(isEmpty()){
			throw new NoSuchElementException("The list is empty");
		}
		return tail.element;
	}
	public void clear(){
		head = null;
		tail = null;
		length = 0;
	}
	public boolean isEmpty(){
		return length ==0;
	}

	public int getSize(){
		return length;
	}

    /* ADD YOUR CODE HERE */

	public SLLIterator iterator() {
		return new SLLIterator(this);
	}

	private class SLLIterator implements Iterator<E> {

		SNode cur ;

		SLLIterator(MySinglyLinkedList<E> list) {
			cur = list.head ;
		}

		public boolean hasNext() {
			return (cur != null) ;
		}

		public E next() {
			SNode tmp = cur ;
			cur = cur.next ;
			return tmp.element ;
		}
	}
}
