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
	private double[] percThresh; //calculates threshold
	private int myT;

	// Constructor will perform T experiments 
	public PercolationStats(int N, int T) {
		if (N<1 || T<1) throw new IllegalArgumentException("Can't do that, dude");
		myT=T;
		//Initialize a counter, the percThresh array and the coordinate array
		int i = 0;
		percThresh = new double[T];
		
		//Performs T experiments 
		while (i<T) {
		//Initialize a IPercolate Object
		IUnionFind finder = new QuickFind();
		IPercolate perc = new PercolationUF(N, finder); //makes sure everything is blocked, there are N*N things
		
		//first create an ArrayList of N^2 size
		ArrayList<Integer> listOfCells= new ArrayList<Integer>();
		//then add N^2 numbers to the ArrayList
		for (int r=0; r< N*N ; r++) {
			listOfCells.add(r);
		}
		
		//then create a way to translate the element number to a coordinate (x,y)
		ArrayList<Integer[]> coordArray = new ArrayList<Integer[]>();
		for (int x=0; x<N; x++) {
			for (int y=0; y<N; y++) {
				Integer[] point = new Integer[2];
				point[0]=x; point[1]=y;
				coordArray.add(point);
			}
		}
		//then randomize the list
		Collections.shuffle(listOfCells, ourRandom);
		
		
		//iterate over this list until it percolates
		Integer[] coordinate = new Integer[2];
			for (int cells: listOfCells) { //make sure it's going through the randomized list
				coordinate=coordArray.get(cells);
				perc.open(coordinate[0], coordinate[1]);
				if (perc.percolates()) break;
			}
			double mathN = (double) N; //type cast into a double dude so the division is not dumb and returns an integer
		percThresh[i]=perc.numberOfOpenSites()/(mathN*mathN);
		i++;
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
	      PercolationStats ps = new PercolationStats(10,20);
	      double end =  System.nanoTime();
	      double time =  (end-start)/1e9;
	      System.out.printf("mean: %1.4f, time: %1.4f\n",ps.mean(),time);

	}	
}
