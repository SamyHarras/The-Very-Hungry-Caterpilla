package assignment2;

import java.util.NoSuchElementException;

public class MyStack<E> {

	private MySinglyLinkedList<E> list ;

	/* ADD YOUR CODE HERE */

	public MyStack(){
		list = new MySinglyLinkedList<>();
	}

	public boolean push(E element){
		if(element == null){
			return false;
		}
		list.addFirst(element);
		return true;
	}

	public E pop(){
		if (list.isEmpty()){
			throw new NoSuchElementException();
		}
		return list.removeFirst();
	}

	public E peek(){
		if (list.isEmpty()){
			throw new NoSuchElementException();
		}
		return list.peekFirst();
	}

	public boolean isEmpty(){
		return list.getSize()==0;
	}

	public void clear(){
		list.clear();
	}

	public int getSize() {
		return list.getSize();
	}



	/* ADD YOUR CODE HERE */

	public String toString() {
		String msg = "" ;
		for ( E e : list) {
			msg = e.toString() + ","  + msg ;
		}
		return msg ;
	}
}
