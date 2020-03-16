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

///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}

////////////////////////////////////////////////////////////////////////////////
class BSTreeP6<T>
{
	private BSTNode<T> root;
	private boolean addAttemptWasDupe=false;
	@SuppressWarnings("unchecked")
	public BSTreeP6( String infileName ) throws Exception
	{
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	public int size()
	{
		return countNodes(); 
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
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

	public int countNodes()
	{
		return countNodes( root );
	}
	private int countNodes( BSTNode root)
	{
		if (root==null) return 0;
		return 1 + countNodes(root.left) + countNodes(root.right);
	}
		
	public int countLevels()
	  {
		return countLevels( root ); 
	  }
	  private int countLevels( BSTNode root)
	  {
		if (root==null) return 0;
		return 1 + Math.max( countLevels(root.left), countLevels(root.right) );
	  }
	  
	  public int[] calcLevelCounts()
	  {
		int levelCounts[] = new int[countLevels()];
		calcLevelCounts( root, levelCounts, 0 );
		return levelCounts;
	  }
	  private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
	  {
		if (root==null)return;
		++levelCounts[level];
		calcLevelCounts( root.left, levelCounts, level+1 );
		calcLevelCounts( root.right, levelCounts, level+1 );
	  }

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{
		printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{
		if (root == null) return;
		printInOrder( root.left );
		System.out.print( root.key + " " );
		printInOrder( root.right );
	}

	public void printLevelOrder()
	{	if (this.root == null) return;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarity
		while ( !q.empty() )
		{	BSTNode<T> n = q.dequeue();
			System.out.print( n.key + " " );
			if ( n.left  != null ) q.enqueue( n.left );
			if ( n.right != null ) q.enqueue( n.right );
		}
	}


		//////////////////////////////////////////////////////////////////////////////////////
	// # # # #   WRITE THE REMOVE METHOD AND ALL HELPERS / SUPPORTING METHODS   # # # # # 

	
	// return true only if it finds/removes the node
	public boolean remove(T key2remove ) {
		
		return remove(key2remove, root, null, false);
	}

	@SuppressWarnings("unchecked")
	public boolean remove( T key2remove, BSTNode<T> root, BSTNode<T> parent, boolean left)
	{
		if(key2remove == null) 
			return true;
		
		if(root == null) {
			return false;
		}

		int comp = ((Comparable)key2remove).compareTo(root.key);
		
		if(comp < 0) {
			if(root.left != null) {
				return remove(key2remove, root.left, root, true);
			} else {
				return false;
			}
		} else if(comp > 0) {
			if(root.right != null) {
				return remove(key2remove, root.right, root, false);
			} else {
				return false;
			}
		} else {
			//remove the leaf node
			if(root.left == null && root.right == null) {
				if(parent == null) {
					root = null;
				} else if(left == true) {
					parent.left = null;
				} else {
					parent.right = null;
				}
				return true;
			} 

			//remove node only have one child
			//root has a child to the right
			else if(root.left == null) {
				if(parent == null) {
					root = root.right;
				} else if(left == true) {
					parent.left = root.right;
				} else {
					parent.right = root.right;
				}
				return true;
			} 
			//root has a child to the left
			else if(root.right == null) {
				if(parent == null) {
					root = root.left;
				} else if(left == true) {
					parent.left = root.left;
				} else {
					parent.right = root.left;
				}
				return true;
			}
			

			//remove the node have two  children
			//if(root.left != null && root.right != null) 
			else {

				BSTNode<T> max = maxValue(root.left);

				root.key = max.key;
				max.key = key2remove;

				return remove(key2remove, root.left, root, false);
			} 
		}
		
	}
	
	public BSTNode<T> maxValue(BSTNode<T> node) {
		BSTNode<T> max = node;
		while(max.right != null) {
			max = max.right;
		}
		return max;
		System.out.println(max);
	}
}

//log
		//find the dead node log2(n)
		//find longest path of leaf log(n) to replace it
		
		/*
		 * if it is a leaf
		 * get a pointer to its parent
		 * set parent L or R to null
		 * 
		 * 
		 * it has one child
		 * get a pointer to its parent
		 * make parent pointer to that's leaf
		 * like linked list
		 * 
		 * 
		 * it has two child
		 * 
		 */

	