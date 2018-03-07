import java.util.*;

/**
 * Compute statistics on Percolation after performing T independent experiments
 * on an N-by-N grid. Compute 95% confidence interval for the percolation
 * threshold, and mean and std. deviation Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 */

public class PercolationStats {
	//Instance variables 
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	public HashMap<Integer, Integer[]> myMap; //helps translate elements to coordinate numbers
	public int[] percThresh; //calculates threshold
	public int myT;

	// Constructor will perform T experiments 
	public PercolationStats(int N, int T) {
		myT=T;
		//Initialize a counter, the percThresh array and the coordinate array
		int i = 0;
		percThresh = new int[T-1];
		Integer[] coordinates = new Integer[2]; //initialize the coordinates
		
		//Performs T experiments 
		while (i<T) {
		//Initialize a IPercolate Object
		IUnionFind finder = new QuickFind();
		IPercolate perc = new PercolationUF(N, finder); //makes sure everything is blocked, there are N*N things
		
		//Call the helper to shift the number to a coordinate system
		indextoCoord(N);
		
		//first create an ArrayList of N^2 size
		ArrayList<Integer> listOfCells= new ArrayList<Integer>();
		//then add N^2 numbers to the ArrayList
		for (int r=0; r< N*N ; r++) {
			listOfCells.add(r);	
		}
		//then randomize the list
		Collections.shuffle(listOfCells, ourRandom);
		System.out.println(listOfCells);
		
		//iterate over this list until it percolates
		while(!perc.percolates()) {
			for (int cells: listOfCells) { //make sure it's going through the randomized list
				coordinates = myMap.get(cells); //then fill coordinates with the value of the map
				//System.out.printf("coordinates are" + Arrays.toString(coordinates));
				perc.open(coordinates[0], coordinates[1]);
				perc.percolates();
			}
		}
		percThresh[i]=perc.numberOfOpenSites()/(N*N);
		i++;
		}
	}
	
	//Helper method
	public void indextoCoord(int N) {
		//Initialize coord and map 
		Integer[] coord = new Integer[2];
		myMap = new HashMap<Integer,Integer[]>();
		myMap.clear(); //makes sure the map is empty
		//Initialize counters
		int k=0;
		while (k<N*N) {
		for (int i=0; i<N-1; i++) {
			for (int j=0; j<N-1; j++) {
			coord[0]=i; coord[1]=j;
			myMap.put(k, coord);
			k++;
			}
		  }
		}
	}

	// Methods
	public double mean() {
		double mean = StdStats.mean(percThresh);
		return mean;
	}

	public double stddev() {
		double stdDev= StdStats.stddev(percThresh);
		return stdDev;

	}

	public double confidenceLow() {
		double confLow = StdStats.mean(percThresh)-(1.96*StdStats.stddev(percThresh)/Math.sqrt(myT));
		return confLow;

	}

	public double confidenceHigh() {
		double confHi = StdStats.mean(percThresh)+(1.96*StdStats.stddev(percThresh)/Math.sqrt(myT));
		return confHi;

	}

	public static void main(String[] args) {
		/*PercolationStats ps = new PercolationStats(50,100); //new class instance 
	      System.out.println(ps.mean()); //get the mean
	      ps = new PercolationStats(200,100); //new class instance
	      System.out.println(ps.mean()); //get the mean
		*/
		
	      //an example of running time mean
	      double start =  System.nanoTime();
	      PercolationStats ps = new PercolationStats(10,2);
	      double end =  System.nanoTime();
	      double time =  (end-start)/1e9;
	      System.out.printf("mean: %1.4f, time: %1.4f\n",ps.mean(),time);

	}	
}
