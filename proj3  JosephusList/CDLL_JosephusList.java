//package proj3;

import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method 
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );	
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked") 
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data ); 
		}
		infile.close();
	}
	

	
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################
	
	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node(data,null,null);
		if (head==null)
		{
			newNode.setNext( newNode );
			newNode.setPrev( newNode );
			head = newNode;
			return;
		}


		CDLL_Node<T> oldNode = head.getPrev();
		newNode.setNext(head);
		newNode.setPrev(oldNode);
		oldNode.setNext(newNode);
		head.setPrev(newNode);
	}

	
	public int size()
	{	
		if(head == null) {
			return 0;
		}
		
		int count = 0;
		if(head != null) {
			count++;
		}
		CDLL_Node<T> curr = head;
		while(curr.getNext() != head) {
			count++;
			curr = curr.getNext();
		}
		return count;
	}
	
	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{	
		if (head == null) {
			return null;
		}

		if(head.getData().equals(key)) {
			return head;
		}
		
		CDLL_Node<T> curr = head.getNext();
		
		while(curr != head) {
			if(curr.getData().equals(key)) {
				return curr;
			}
			curr = curr.getNext();
		}

		return null;
	}
	
	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		String result = "";

		result += head.getData();

		if(head.getNext() != head) {
			result += "<=>";
		}
		
		
		for(CDLL_Node curr = head.getNext(); curr!= head; curr = curr.getNext()) {
			result += curr.getData();
			if(curr.getNext() != head) {
				result += "<=>";
			}
		}
		return result;
	}
	
	void removeNode( CDLL_Node<T> deadNode )
	{
		
		CDLL_Node<T> temp = deadNode.getNext();
		deadNode.getPrev().setNext(temp);
		temp.setPrev(deadNode.getPrev());
		//deadNode.getNext().setPrev(deadNode.getPrev());
	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() < 1 ) 
			return;

		CDLL_Node<T> curr = search( first2Bdeleted );

		if ( curr == null ) {
			return;
		} 
		
		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			CDLL_Node<T> deadNode = curr;
			T deadName = deadNode.getData();
			
			// ** println( "stopping on Misurda to delete Misurda");
			System.out.println("stopping on " + deadName + " to delete " + deadName);
			
			// before you delete this node you advance curr to either .pre OR .next
			if(skipCount < 0) {
				curr = deadNode.getPrev();
			}  else {
				curr = deadNode.getNext();
			}
			 
			// now before you remove the node you ask one more thing. Is the head pointing at deadnode?
			// if so the make haed point to same node as curr 
			if(deadNode == head) {
				head = curr;
			}
			
			// remove the deadNode
			removeNode(deadNode);
			

			// ** println("deleted. list now:   HoffmanT<=>Lange<=>Lee<=>Litman<=>Melhem<=>Mosse<=>Novacky<=>Ramirez");
			System.out.println("deleted. list now: " + this.toString());
			
			// if the list size has reached 1 break out of this loop YOU ARE DONE 
			if(this.size() == 1)
				break;
	
			// ** println("resuming at Mosse, skipping Mosse + 4 nodes CLOCKWISE after");
			if(skipCount > 0) {
				System.out.println("resuming at " + curr + ", skipping " + curr + " + " + skipCount + " nodes CLOCKWISE after");
			} else {
				System.out.println("resuming at " + curr + ", skipping " + curr + " + " + (-1 -skipCount) + " nodes COUNTER_CLOCKWISE after");
			}
	
			// write loop that advance curr skipCount times (be sure of CLOCKWISE or COUNTER)
			if(skipCount > 0) {
				for(int i = 0; i < skipCount; i++) {
				curr = curr.getNext();
				}
			} else  {
				for(int i = 0; i < -skipCount; i++) {
					curr = curr.getPrev();
				}
			}	
	
			
			// String junk = kbd.nextLine();  <= MIGHT FIND THis HELPFUL. FOR DEBUGGING. WAITS FOR YOU TO HIT RETUN KEY
			
		} while (size() > 1 );

	}
	
} // END CDLL_LIST CLASS