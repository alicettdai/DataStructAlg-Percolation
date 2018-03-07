import java.util.NoSuchElementException;

public class PercolationDFSFast extends PercolationDFS {
	
	//A constructor that makes a call to the superclass constructor
	public PercolationDFSFast(int n) {
	super(n);
	}

	//Update on open should just check if the cells around the newly opened cell are open 
	@Override
	public void updateOnOpen(int row, int col) {
	//now that a cell is open, to check if it's full, one of its neighbors needs to be full
		//if it's out of bounds
		if (! inBounds(row,col)) {
	        throw new NoSuchElementException("Out of bounds, mate");
		}
		
		//if it's on the top then it definitely is full
		if (row==0) {
			myGrid[row][col]	 = FULL;
			//with a newly full point, we call DFS to check how this affects the other surrounding grids
			dfs(row - 1, col);
			dfs(row, col - 1);
			dfs(row, col + 1);
			dfs(row + 1, col);
		}
		
		
		//then see if one of the surroundings is full, if it is then the row and column is full, and then if it itself becomes full, then its surroundings are all full
		else {
			if (inBounds(row-1,col) && isFull(row-1,col) ){
			myGrid[row][col]= FULL;
			//with a newly full point, we call dfs to check how this affects the other surrounding grids
			dfs(row - 1, col);
			dfs(row, col - 1);
			dfs(row, col + 1);
			dfs(row + 1, col);
			}
			if ( inBounds(row, col - 1) && isFull(row, col - 1) ){
				myGrid[row][col]= FULL;
				dfs(row - 1, col);
				dfs(row, col - 1);
				dfs(row, col + 1);
				dfs(row + 1, col);
			}
			if (inBounds(row, col + 1) && isFull(row, col + 1)){
				myGrid[row][col]= FULL;
				dfs(row - 1, col);
				dfs(row, col - 1);
				dfs(row, col + 1);
				dfs(row + 1, col);
			}
			if (inBounds(row + 1, col) && isFull(row + 1, col)){
				myGrid[row][col]= FULL;
				dfs(row - 1, col);
				dfs(row, col - 1);
				dfs(row, col + 1);
				dfs(row + 1, col);
			}
		}	
	}
	
	@Override
	public void open(int row, int col) {
		//if not in bounds, throw an exception
		if (! inBounds(row,col)) {
	        throw new NoSuchElementException("Out of bounds, mate");
	    }
	    super.open(row, col); 
		
	}

	@Override
	public boolean isOpen(int row, int col)  {
		//if not in bounds, throw an exception
		if (!inBounds(row,col)) {
	        throw new NoSuchElementException("Out of bounds, mate");
	    }
	    return super.isOpen(row, col);
	}

	@Override
	public boolean isFull(int row, int col) {
		//if not in bounds, throw an exception
				if (! inBounds(row,col)) {
			        throw new NoSuchElementException("Out of bounds, mate");
			    }
			    return super.isFull(row, col);
	}

	@Override
	public boolean percolates() {
		return super.percolates();
	}

	@Override
	public int numberOfOpenSites() {
		return super.numberOfOpenSites();
	}

}
