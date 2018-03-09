//@author Alice Dai
//notice how we don't have coordinate points now, the elements are already indexed 

public class QuickUWPC implements IUnionFind {
	//instance variables 
	private int[] parent;
	private int[] size;
	private int myComponents;
	
	//constructors 
	public QuickUWPC() {
		this(10);
	}
	
	public QuickUWPC(int numSets) {
		initialize(numSets);
	}
	
	//methods
	
	//initializes variables, and makes sure the components are only connected to themselves 
	@Override
	public void initialize(int n) {
        myComponents = n;
        parent = new int[n]; //an integer node called parent that 
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
		}	
	}

	//return number of components
	@Override
	public int components() {
		return myComponents;
	}

	//finds the ROOT of the element, make sure that's what it does
	@Override
	public int find(int x) {
		//first make sure it's valid
		validate(x);
		
		//then make sure you find the ROOT of x
		while (x != parent[x])  //while we haven't reached the root, and the root always points to itself
			x=parent[x];
			return x; //but you ultimately want the root, and the while loop ensures that
	}

	//asks if the two elements have the same component
	@Override
	public boolean connected(int p, int q) {
		return find(p)==find(q);
	}

	//always want to merge the smaller set to the larger set
	@Override
	public void union(int p, int q) {
		int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return; // they already have the same root

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) { 
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else { //if sizes are the same or Q is greater than P
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
		
		//every time there's a union there's one less component
		myComponents--;	
	}
	
	//helper methods
	private void validate(int p) {
		int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1)); 
	}
  }
}
