

/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
	//All the instance variables
	private final int OUT_BOUNDS = -1;
	private int maxRCIndex;
	private int dim;
	private int myOpenCount;
	private boolean[][] myGrid; //default for the elements is false, apparently
	private IUnionFind myFinder;
	private final int VTOP; //the top number that doesn't interfere with the indices
	private final int VBOTTOM; // the bottom number that doesn't interfere with the indices 

	/**
	 * Constructs a Percolation object for a nxn grid that that creates
	 * a IUnionFind object to determine whether cells are full
	 */
	public PercolationUF(int n, IUnionFind finder) {
		if (n<1) {
			throw new IndexOutOfBoundsException("Index + 'n' + out of bounds");
		}
		myGrid = new boolean[n][n]; //initialize the size of myGrid, it's either attached open (
		maxRCIndex= n-1;
		dim= n;
		VTOP= dim*dim;
		VBOTTOM= dim*dim+1;
		
	for (int i=0; i<dim; i++) { //initialized all the elements to false
			for (int j=0; j<dim; j++) {
				myGrid[i][j]= false; 
			}	
		}
					
		
		finder.initialize(dim*dim+2); //initializes the size of finder, and makes all the elements connected to ONLY themselves 
		myFinder= finder; //assign the object finder to the instance variable myFinder 
		myOpenCount=0;	
	}	
	

	/**
	 * Return an index that uniquely identifies (row,col), typically an index
	 * based on row-major ordering of cells in a two-dimensional grid. However,
	 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
	 */
	private int getIndex(int row, int col) {
		if (! inBounds(row,col)) {
			return OUT_BOUNDS;
		}
		int index = (dim*row)+col;
		return index;
		
	}

	@Override
	public void open(int i, int j) {
		if (! inBounds(i,j)) {
			throw new IndexOutOfBoundsException("Out of bounds, mate");
		}
		if (myGrid[i][j]) { //makes sure to not reopen something that's already open
			return;
		}
		//turn the coordinate from false to true
		myGrid[i][j] = true;
		myOpenCount++; //adds one to the count
		updateOnOpen(i,j);
	}

	@Override
	public boolean isOpen(int i, int j) {
		if (! inBounds(i,j)) {
			throw new IndexOutOfBoundsException("Out of bounds, mate");
		}
		
		//if it's open then it's true
		return myGrid[i][j];
	}

	@Override
	public boolean isFull(int i, int j) {
		if (! inBounds(i,j)) {
			throw new IndexOutOfBoundsException("Out of bounds, mate");
		}
		
		if (!isOpen(i,j)) { //if it's not full
			return false;
		}
		//get the index
		int x= getIndex(i,j);
		
		//is the coordinate unioned with the top
		return myFinder.connected(x, VTOP);
	}

	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}

	@Override
	public boolean percolates() {
		//checks to see if VTOP is connected to VBOTTOM
		return myFinder.connected(VTOP,VBOTTOM);
	}
	
	//helper method that is called in open
	private void updateOnOpen(int row, int col) {
		//get the Indices 
		int x = getIndex(row,col);
		
		//check if the point is in the first or last row, and also if the surrounding neighbors are open, and if they are, then the bounds should be 
		if (row == 0) { //on top
			myFinder.union(x,VTOP);
		}
		
		if (row == maxRCIndex) { //on bottom
			myFinder.union(x,VBOTTOM);
		}
		
		if (inBounds(row-1,col) && isOpen(row-1,col)){
			int y=getIndex(row-1,col);
			myFinder.union(x,y);
		}
		if (inBounds(row, col - 1) && isOpen(row, col - 1)){
			int y=getIndex(row,col-1);
			myFinder.union(x,y);	
		}
		
		if (inBounds(row, col + 1) && isOpen(row, col + 1)){
			int y=getIndex(row,col+1);
			myFinder.union(x,y);
		}
		
		if (inBounds(row + 1, col) && isOpen(row + 1, col)){
			int y=getIndex(row+1,col);
			myFinder.union(x,y);	
		}
	}
	
	private boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
}
