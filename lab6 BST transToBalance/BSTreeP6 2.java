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
public int countLeaves()
	{	
		return countLeaves( this. root );
	}
	private int countLeaves( BSTNode root) //help
	{	
		if(root == null)
			return 0;
		 //change return 1

		if(root.left == null && root.right == null)
		{
			return 1 + countLeaves(root.left) + countLeaves(root.right);
		}
		else if(root.left != null || root.right != null)
		{
			return countLeaves(root.left) + countLeaves(root.right);
		}

		else
			return 0;
	}
	public int countInteriorNodes()
	{	
		return countInteriorNodes( this.root );
	}
	private int countInteriorNodes( BSTNode root) //help
	{	
		if(root == null)
			return 0;
		 //change return 1
		if(root.equals(this.root)) //so it excludes the actual root
		{
			return countInteriorNodes(root.left) + countInteriorNodes(root.right);
		}
		
		if(root.left != null || root.right != null)
		{
			return 1 + countInteriorNodes(root.left) + countInteriorNodes(root.right);
		}

		else //if both cildren are null its a leaf
			return 0; 
	}
	public int countLevels()
	{	
		return countLevels( root );
	}
	private int countLevels( BSTNode root)
	{	
		if(root == null)
			return 0;
		return 1 + Math.max(countLevels(root.left), countLevels(root.right));
	}
	public int[] calcLevelCounts()
	{	
		int levelCounts[] = new int[countLevels()];
		calcLevelCounts( root, levelCounts, 0 );
		return levelCounts;
	}
	private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
	{
		if(root == null)
			return;
   		++levelCounts[level];
    	calcLevelCounts(root.left,levelCounts,level+1);
    	calcLevelCounts(root.right,levelCounts,level+1);
	} 

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

	// PRE ORDER TRAVERSAL REQUIRES RECURSION
	public void printPreOrder()
	{	printPreOrder( this.root );
		System.out.println();
	}
	private void printPreOrder( BSTNode<T> root )
	{	if (root == null) return;
		System.out.print( root.key + " " );
		printPreOrder( root.left );
		printPreOrder( root.right );
	}

	// POST ORDER TRAVERSAL REQUIRES RECURSION
	public void printPostOrder()
	{	printPostOrder( this.root );
		System.out.println();
	}
	private void printPostOrder( BSTNode<T> root )
	{	if (root == null) return;
		printPostOrder( root.left );
		printPostOrder( root.right );
		System.out.print( root.key + " " );
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
		System.out.println();
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// # # # #   WRITE THE REMOVE METHOD AND ALL HELPERS / SUPPORTING METHODS   # # # # # 

	// return true only if it finds/removes the node
	public boolean remove( T key2remove )
	{
		if(this.root == null) return false; 
		if(this.root.key.equals(key2remove))
		{
			if(this.root.left == null && this.root.right == null) // leaf
			{
				this.root = null;
				return true;
			}
			else if(this.root.left == null && this.root.right !=null) //parent w 1 child 
			{
				this.root = this.root.right;
				return true;
			}
			else if(this.root.right == null && this.root.left !=null) //other 1 child
			{
				this.root = this.root.left;
				return true;
			}
			else if(this.root.left != null && this.root.right != null) 
			{
				T key = getPredsKey(this.root);
				T predsKey = key;
				removeHelper(this.root, predsKey);
				this.root.key = predsKey;
				return true;
			}
		}
		return removeHelper(this.root, key2remove);
	}

	private boolean removeHelper(BSTNode<T> root, T deadKey)
	{
		boolean isChildLeft = false; 
		BSTNode<T> deadsParent = findDeadsParent(root, deadKey);
		BSTNode<T> deadNode = null;
		if(deadsParent == null) return false;  

		if(deadsParent.left != null && deadsParent.left.key.equals(deadKey))
		{	
			isChildLeft = true;
			deadNode = deadsParent.left;
		}
		else
		{
			deadNode = deadsParent.right;
		}

		if(deadNode.left == null && deadNode.right == null)
		{
			if(isChildLeft)
			{
				deadsParent.left = null;
				return true;
			}
			else
			{
				deadsParent.right = null;
				return true;
			}
		}

	//remove leaf

		if(deadNode.left != null && deadNode.right == null)
		{
			if(isChildLeft)
			{
				deadsParent.left = deadNode.left; return true;
			}
			else
			{
				deadsParent.right = deadNode.left; return true;
			}
		}
		else if(deadNode.left == null && deadNode.right != null) 
		{
			if(isChildLeft)
			{
				deadsParent.left = deadNode.right; return true;
			}
			else
			{
				deadsParent.right = deadNode.right; return true;
			}
		}

		if(deadNode.left != null && deadNode.right != null) 
		{
			T key = getPredsKey(deadNode);
			T predsKey = key; 
			removeHelper(this.root, predsKey);
			deadNode.key = predsKey;
		}

		return false;
	}

	private BSTNode<T> findDeadsParent(BSTNode<T> root, T deadKey)
	{
		if(root==null) return null;
		if(root.left == null && root.right == null) return null;
		if(root.left != null && root.left.key.equals(deadKey)) return root; 
		if(root.right != null && root.right.key.equals(deadKey)) return root;

		if(findDeadsParent(root.left, deadKey) != null)
		{
			return findDeadsParent(root.left, deadKey);
		}

		if(findDeadsParent(root.right, deadKey) != null)
		{
			return findDeadsParent(root.right, deadKey);
		}

		return null; 
	}

	private T getPredsKey(BSTNode<T> deadNode)
	{
		BSTNode<T> currNode = deadNode.left; 
		while(currNode.right != null)
		{
			currNode = currNode.right;
		}
		return currNode.key;
	}
  
} // END BSTREEP6 CLASS
