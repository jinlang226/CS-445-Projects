//package proj1;

import java.io.*;

public class LinkedList<T extends Comparable<T>>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	@SuppressWarnings("unchecked")	
	public LinkedList( String fileName, boolean orderedFlag )
	{
		head=null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new element at front of list - pushing old elements back one place
	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print


	public String toString()
	{
		String toString = "";

		for (Node curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	
	public int size() // OF COURSE MORE EFFICIENT TO MAINTAIN COUNTER. BUT YOU WRITE LOOP!
	{
		int length = 0;
		for (Node<T> curr = head; curr != null; curr = curr.getNext()) {
			length ++;
		}
		return length;
	}


	public boolean empty()
	{
		return size() == 0;
		//Must call size(), NOT examine head and be written as 1 line return statement
	}

	
	public boolean contains( T key )
	{
		return search(key) != null;
	}

	
	public Node<T> search( T key )
	{
		if (head == null) {
			return null;
		}

		Node<T> curr = head;
		
		while(curr != null) {
			if(curr.getData().equals(key)) {
				return curr;
			}
			curr = curr.getNext();
		}

		return null;
	}

	
	public void insertAtTail(T data) // TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	{
		if (head == null) {
			insertAtFront(data);
			return;
		}
		Node<T> curr = head;

		while(curr.getNext() != null) {
			curr = curr.getNext();
		}

		curr.setNext(new Node<T>(data, null));
	}

	@SuppressWarnings("unchecked")
	public void insertInOrder(T data)  // PLACE NODE IN LIST AT ITS SORTED ORDER POSTIOPN
	{
		Comparable cmpData = (Comparable) data;
		
		if(head == null || cmpData.compareTo(head.getData()) < 0) {  //order of evaluation
			insertAtFront(data);
			return;
		}
		
		//compare the incomming data to each data value in list starting at head
		Node<T> curr = head;
		while(curr.getNext() != null && cmpData.compareTo(curr.getNext().getData()) > 0) {
			curr = curr.getNext();
		}

		Node<T> temp = new Node<T> (data, null);
		temp.setNext(curr.getNext());
		curr.setNext(temp);
	}
	
	
	public boolean remove(T key) // FIND/REMOVE 1st OCCURANCE OF A NODE CONTAINING KEY
	{
		if(head == null) {
			return false;
		}
		if(head.getData().equals(key)) {
			removeAtFront();
			return true;
		}
		
		for(Node<T> curr = this.head; curr.getNext() != null; curr = curr.getNext()) {
			if(curr.getNext().getData().equals(key)) {
				curr.setNext(curr.getNext().getNext());
				return true;
			}
		}
		return false;
	}

	
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(head.getNext() == null) {
			removeAtFront();
			return false;
		}
		
		Node<T> curr = head;
		while(curr.getNext().getNext() != null) { //cur.next = null
			curr = curr.getNext();
		}
		curr.setNext(null);
		return true;
	}

	
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(head == null)
			return false; // CHANGE TO YOUR CODE
		Node<T> curr = head;
		head = curr.getNext();
		return true;
	}


	public LinkedList<T> union( LinkedList<T> other )
	{
		LinkedList<T> union = new LinkedList<T>();
	
		for(Node<T> curr = this.head; curr != null; curr = curr.getNext()) {
			if(!union.contains(curr.getData())) 
				union.insertInOrder(curr.getData());
		}

		for(Node<T>curr = other.head; curr != null; curr = curr.getNext()) {
			if(!union.contains(curr.getData())) {
				union.insertInOrder(curr.getData());
			}
		}
		return  union; 
	}
	
	
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> inter = new LinkedList<T>();
		
		for(Node<T> curr = this.head; curr != null; curr = curr.getNext()) {
			if(other.contains(this.get(i)) && !inter.contains(this.get(i))) {
				inter.insertInOrder(curr.getData());
			}
		}
		
		return inter;
	}


	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> diff = new LinkedList<T>();
		for(Node<T> curr = this.head; curr != null; curr = curr.getNext()) {
			if(!other.contains(this.get(i)) && !diff.contains(this.get(i))) {
				diff.insertInOrder(curr.getData());
			}
		}
		return  diff; 
	}

	
	public LinkedList<T> xor( LinkedList<T> other )
	{
		return this.diff(other).union(other.diff(this));
	}

} //END LINKEDLIST CLASS
