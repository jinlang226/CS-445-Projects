//package BinaryTree;

import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}

///////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// DEQUEUE THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}

////////////////////////////////////////////////////////////////////////////////
class BSTreeL5<T>
{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addAttemptWasDupe=false;
	@SuppressWarnings("unchecked")
	public BSTreeL5( String infileName ) throws Exception
	{
		nodeCount=0;
		root=null;
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );
		while ( infile.ready() )
			add( (T) infile.readLine() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	public int size()
	{
		return nodeCount; // LOCAL VAR KEEPING COUNT
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		if (!addAttemptWasDupe) ++nodeCount;
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
    } // END addHelper


	// ITS A SEARCH - ONE OF FEW OPS YOU CAN DO ITERATIVELY
	public boolean contains( T key )
	{
		return contains( this.root, key  );
	}
	
	@SuppressWarnings("unchecked")
	private boolean contains( BSTNode<T> root, T key  )
	{
		if (root == null) return false;
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 ) return true;
		if (comp < 0) return contains(root.left, key );
		return contains(root.right, key );
	}


	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
	// DO NOT MODIFY ANYTHING ABOVE THIS LINE.  YOU FILL IN ALL THE CODE BELOW
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

	// COUNT NODES REQUIRES RECURSION
	public int countNodes() // DYNAMIC COUNT ON THE FLY TRAVERSES TREE
	{
		return countNodes( this.root );
	}
	
	private int countNodes( BSTNode<T> root )
	{	
		int count = 0;
		if (root == null)
			return count;
		//	num nodes in left child + num nodes in right childs
		return 1 + countNodes(root.left) + countNodes(root.right);
	}

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{	
		printInOrder( this.root );
		System.out.println();
		//BAC
	}
	
	private void printInOrder( BSTNode<T> root )
	{	// IN ORDER = LVR
		// YOUR CODE HERE. MUST USE RECURSION
		if(root == null) 
			return;
		printInOrder(root.left);
		System.out.print(root.key + " ");
		printInOrder(root.right);
	}

	// PRE ORDER TRAVERSAL REQUIRES RECURSION
	public void printPreOrder()
	{	
		printPreOrder( this.root );
		System.out.println();
		//ABC
	}
	
	private void printPreOrder( BSTNode<T> root )
	{	
		// POSTORDER = VLR
		// YOUR CODE HERE. MUST USE RECURSION
		if(root == null) 
			return;
		System.out.print(root.key + " ");
		printPreOrder(root.left);
		printPreOrder(root.right);
	}

	// POST ORDER TRAVERSAL REQUIRES RECURSION
	public void printPostOrder()
	{	
		printPostOrder( this.root );
		System.out.println();
		//BCA
	}
	
	private void printPostOrder( BSTNode<T> root )
	{	
		// POSTORDER = LRV
		// YOUR CODE HERE. MUST USE RECURSION
		if(root == null) 
			return;
		printPostOrder(root.left);
		printPostOrder(root.right);
		
		System.out.print(root.key + " ");
		
	}

	// LEVEL ORDER TRAVERSAL REQUIRES A QUEUE AND ITERATION
	public void printLevelOrder()
	{	
		// YOUR CODE HERE NO REQURSION.
		// USE THE Queue CLASS WE WROTE
		if(root == null)
			return;
		Queue<T> queue = new Queue<T>();
		queue.enqueue(root);
		while(!queue.empty()) {
			BSTNode<T> curr = queue.dequeue();
			System.out.print(curr.key + " ");
			if(curr.left != null) {
				queue.enqueue(curr.left);
			}
			if(curr.right != null) {
				queue.enqueue(curr.right);
			}
		}
	}
}