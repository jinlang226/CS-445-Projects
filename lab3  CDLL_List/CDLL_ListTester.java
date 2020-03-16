import java.io.*;
import java.util.*;

public class CDLL_ListTester
{
	@SuppressWarnings("unchecked")
	public static void main( String args[] ) throws Exception
	{
		String[] keys = { "charlie", "golf", "bravo", "dragonfly" }; // WE WILL SEARCH THE LISTS FOR THESE
		
		CDLL_List<String> cdll_1 = new CDLL_List<String>( args[0], "atFront" );
		System.out.format( "cdll_1 loaded from %s (insertAtFront) size=%d %s\n",args[0],cdll_1.size(),cdll_1 );
		for ( String key : keys )
			System.out.format( "cdll_1.contains(%s) returns %b\n", key,cdll_1.contains(key) );
		
		CDLL_List<String> cdll_2 = new CDLL_List<String>( args[0], "atTail" );
		System.out.format( "cdll_2 loaded from %s (insertAtTail)  size=%d %s\n",args[0],cdll_2.size(),cdll_2 );
		for ( String key : keys )
			System.out.format( "cdll_2.contains(%s) returns %b\n", key,cdll_2.contains(key) );
		
	} // END MAIN
} // END CDLL_ListTester CLASS


/*
insertAtFront(): as you write it print the data of each node as you insert it. This will verify you are not hung in an infinite loop.
size(): once you are sure your insertAtFront() is at least creating new nodes and not infinite looping, traverse the list CLOCKWISE (following the next pointer ) and count the nodes.
toString(): once your size works, your InsertAtFront() is probably correct so now you should write your toString using the size() code as a template. Instead of incrementing a counter as the visitation operation, you tack that Node's data onto your string. (SEE OUTPUT SCREENSHOT).
search(): it's just the Same traversal as size() but a different visitation operation ( .equals() ).
contains(): just return the success/failure of search()
insertAtTail(): use your insertAtFront as a starting point.
*/