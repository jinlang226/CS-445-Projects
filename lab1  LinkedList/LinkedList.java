import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// COPY ALL NODES FROM OTHER LIST INTO THIS LIST. WHEN COMPLETED THIS LIST IDENTICAL TO OTHER
	public LinkedList( LinkedList<T> other )
    {
        Node<T> curr = other.head; //
        while (curr != null )  // i.we. WHILE CURR POINTING AT SOME NODE FROM OTHER LIST
        {
            insertAtTail(curr.getData());
            curr = curr.getNext();    
        }
    }

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList( String fileName ) 
	{
		try 
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{  
				insertAtTail((T)infile.readLine());  
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
	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print

	public String toString()
	{
		String toString = "";

		for (Node<T> curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " -> ";
		}

		return toString + "\n";
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	// TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	public void insertAtTail(T data)
	{
		//IF THERE ARE NO NODES IN LIST TACK THIS ONE RIGHT ONTO THE HEAD
		//ELSE GET A REF TO THE VERY LAST NODE AND HANG IT OFF THE LAST NODE'S NEXT REF
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

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT DYNAMICALLY WITH A TRAVERSAL LOOP
	public int size()
	{	
		int length = 0;
		Node<T> curr = head;
		while(curr != null) {
			length++;
			curr = curr.getNext(); 
		}
		return length;
		// WALK THE LIST AND COUNT THE NODES
	}
	
	// MUST CALL SEARCH AND IF SEARCH RETURNS NULL, THIS METHOD RETURNS FALSE, OTHERWIASE RETURN TRUE
	public boolean contains( T key )
	{
		if(search(key) == null) 
		{
			return false;
		}
		else 
		{
			return true;
		} // REPLACE WITH CALL TO SEARCH. IF NULL RETURN FALSE ELSE RETURN TRUE
	}

	// TRAVERSE LIST FRONT TO BACK LOOKING FOR THIS DATA VALUE.
	// RETURN REF TO THE FIRST NODE THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
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
		//WALK THE LIST LOOKING FOR 1ST OCCURANCE OF NODE WITH DATA VALUE EQUAL TO KEY
	}

} //EOF