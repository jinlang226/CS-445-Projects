//package proj7;

//java GraphTester graphdata.txt

/* This class was borrowed and modified as needed with permission from it's original author
Mark Stelhik ( http:///www.cs.cmu.edu/~mjs ).  You can find Mark's original presentation of
this material in the links to his S-01 15111,  and F-01 15113 courses on his home page.
*/

import java.io.*;
import java.util.*;

public class Graph 
{
	private final int NO_EDGE = -1; // all real edges are positive
	private Edge G[];              // will point to a 2D array to hold our graph data

	private int numEdges;

	public Graph( String graphFileName ) throws Exception  // since readFild doesn't handle them either
	{
		loadGraphFile( graphFileName );
		
//		T E M P O R A R Y    C O D E    T O    V E R I F Y    P R I V A T E 
//		M E T H O D S    W E    C A N T    C A L L    F R O M   T E S T E R 
//		      ........R E M O V E   A F T E R   T E S T I N G .......
/*
		for (int node = 0 ; node < G.length ; ++node )
		{
			System.out.format( "DEBUG:: in (%d)=%d  ",node,inDegree(node) );
			System.out.format( "out(%d)=%d  ",node,outDegree(node) );
			System.out.format( "deg(%d)=%d\n",node,degree(node) );
		}
*/
	}

	///////////////////////////////////// LOAD GRAPH FILE //////////////////////////////////////////
	//
	// FIRST NUMBER IN GRAPH FILE IS THE SQUARE DIMENSION OF OUR 2D ARRAY
	// THE REST OF THE LINES EACH CONTAIN A TRIPLET <ROW,COL,WEIGHT> REPRESENTING AN EDGE IN THE GRAPH

	private <T> void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );

		int dimension = graphFile.nextInt();   	// THE OF OUR N x N GRAPH
		 
		//LinkedList<Edge> G[] = new LinkedList[dimension];
		//arrayRefVar = new dataType[arraySize];
		G = new Edge[dimension];
//		
		for(int i = 0; i < dimension ; i++){ 
            G[i] = null; 
        } 
		
		// N x N ARRAY OF ZEROS
		numEdges=0;

		while (graphFile.hasNextInt())
		{
			// read in the row,col,weight // that eat us this line
			// call add edge
			int r = graphFile.nextInt();
			int c = graphFile.nextInt();
			int w = graphFile.nextInt();
			addEdge(r, c, w);
			
		}

		//System.out.println(toString());

	} // END readGraphFile
	
	// ADD THIS CODE INTO YOUR Graph.java FILE JUST LIKE YOU DID IN THE BSTs

	public class Edge
	{
		int dest, weight;
		Edge next;
		
		//a Constructor that takes dest,weight, next
		Edge(int dest, int weight, Edge next) {
			this.dest = dest;
			this.weight = weight;
			this.next = next;
		}
		
	}
	
	
	
	private void addEdge( int r, int c, int w )
	{
		//System.out.println("adding edge: ["+r+"->"+c+"]");
		if(G[r] == null) {
			//System.out.println("G[r] is null");
			G[r] = new Edge(c, w, null);

		}
		else {
			//System.out.println("G[r] is not null");
			Edge before = G[r];
			G[r] = new Edge(c, w, before);
		}
		++numEdges; // only this method adds edges so we do increment counter here only
	}
	
	
	private boolean hasEdge(int fromNode, int toNode)
	{
		Edge curr = G[fromNode];
		while (curr != null) {
			if(curr.dest == toNode)
				return true;
			curr = curr.next;
		}
		return false; 
	}
	
	
	// IN DEGREE IS NUMBER OF ROADS INTO THIS CITY
	//all cities, to that destination
	private int inDegree(int node)
	{
		int inDegree = 0;
		for(int i = 0; i < G.length; i++) {
			Edge curr = G[i];
			while(curr != null) {
				if(curr.dest == node) {
					inDegree ++;
				}
				curr = curr.next;
			}
		}
		
		return inDegree;
	}

	
	
	///modify
	// OUT DEGREE IS NUMBER OF ROADS OUT OF THIS CITY
	// NODE IS THE ROW #. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT ROW
	
	//how many city under LL
	private int outDegree(int node)
	{
		int outDegree = 0;
		Edge curr = G[node];
		while(curr != null) {
			outDegree ++;
			curr = curr.next;
		}
		return outDegree;	
	}

	
	//modify
	// DEGREE IS TOTAL NUMBER OF ROAD BOTH IN AND OUT OFR THE CITY 
	private int degree(int node)
	{
		return outDegree(node)+inDegree(node);
	}

	
	
	//modify
	public void removeEdge(int fromNode, int toNode)
	{
		/* if caller passes in a row col pair that 
		   out of bound or has no edge there, you must 
		   throw and catch an exception. See my output.
		
		   if the edge is there then remove it by writing 
		   in a NO_EDGE value at that coordinate in the grid	
		*/
		try {

			if(fromNode<0 || fromNode>=G.length || toNode<0||toNode>=G.length 
				 || !hasEdge(fromNode, toNode)) {
				throw new Exception("Non Existent Edge Exception: removeEdge("+fromNode+","+toNode+")" );
			}
			
			//delete the first edge 
			if( G[fromNode].dest == toNode) {
				 G[fromNode] =  G[fromNode].next;
			} 
			else { //delete second and later
				Edge curr = G[fromNode];
				while(curr.next.dest != toNode) {
					curr = curr.next;
				}
				curr.next = curr.next.next;
			}
		}

		catch(Exception e)
		{
			System.out.println(e);
			System.exit(0);
		}
	}
	
	
	//modify
	// TOSTRING
	public String toString()
	{	
		String the2String = "";
		for (int r=0 ; r < G.length ;++r )
		{
			Edge curr = G[r];
			int from = r;
			the2String += r + ": ";
			if(G[r]!= null)
				the2String += "-> ";
			while(curr != null) {
				the2String += ("[" + curr.dest +"," + curr.weight + "]");
				curr = curr.next;
				if(curr != null)
					the2String += " -> ";
			}
			the2String += "\n";
		}
		return the2String;
	} // END TOSTRING
	
	

	// PUBLIC METHODS 
	
	public int maxOutDegree()
	{
		int maxOut = 0;
		for(int i = 0; i < G.length; i++) {
			if(outDegree(i) > maxOut) {
				maxOut = outDegree(i);
			}
		}
		return maxOut;
	}

	public int maxInDegree()
	{
		int maxInDegree=0;
		for (int i = 0; i < G.length; i++)
		{
			if (inDegree(i) > maxInDegree) 
				maxInDegree=inDegree(i);
		}
		return maxInDegree;
	}

	public int minOutDegree()
	{
		int minOutDegree = outDegree(0);
		for (int i = 0; i < G.length; i++)
		{
			if (outDegree(i) < minOutDegree) 
				minOutDegree = outDegree(i);
		}
		return minOutDegree;
	}

	public int minInDegree()
	{
		int minInDegree = inDegree(0);
		for (int i = 0; i < G.length; i++)
		{
			if (inDegree(i) < minInDegree) 
				minInDegree=inDegree(i);
		}
		return minInDegree;
	}	
	
	public int maxDegree()
	{
		int maxInDegree=0;
		for (int i = 0; i < G.length; i++)
		{
			if (degree(i) > maxInDegree) 
				maxInDegree = degree(i);
		}
		return maxInDegree;
	}

	public int minDegree()
	{
		int minInDegree = G.length;
		for (int i = 0; i< G.length; i++)
		{
			if (degree(i) < minInDegree) 
				minInDegree=degree(i);
		}
		return minInDegree;
	}
	
} //EOF


