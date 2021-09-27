
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.*;
import java.text.DecimalFormat;
import java.util.Random;


public class tsp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//points for exhaustive search
//		ArrayList<point> points_4 = generateList(4);
//		ArrayList<point> points_6 = generateList(6);
//		ArrayList<point> points_8 = generateList(8);
//		ArrayList<point> points_10 = generateList(10);
//		//points for nearest neighbor
//		ArrayList<point> points_20 = generateList(20);
//		ArrayList<point> points_30 = generateList(30);
//		ArrayList<point> points_40 = generateList(40);
//		ArrayList<point> points_50 = generateList(50);
//		
//		int numIterations = 25;
		
		fileData exhaustive_data = fileParser("/Users/carterfowler/Desktop/Comp_Sci/406/Code/TSP/src/exhaustive_test");
		fileData neighbor_data = fileParser("/Users/carterfowler/Desktop/Comp_Sci/406/Code/TSP/src/neighbor_test");
		ArrayList<point> points = neighbor_data.points;
		int n = neighbor_data.n;
		tspClass fileNeighbor = nearest_neighbor(n, points);
//		tspClass fileExhaustive = exhaustive_search(n, points);
		System.out.println("Nearest Neighbor\n" + fileNeighbor.toString());
//		System.out.println("Exhaustive Search\n" + fileExhaustive.toString());
		//replace the file path string with the necessary file path to get the paths and distance
		//for both nearest neighbor and exhaustive search

		//Test code
//		ArrayList<point> points = new ArrayList<point>();
//		points.add(new point(0,0));
//		points.add(new point(10,0));
//		points.add(new point(0,5));
//		points.add(new point(10,5));
//		tspClass neighbor = nearest_neighbor(4, points);
//		System.out.println(neighbor.toString());
		
//		long time_neighbor_20 = 0;
//		long time_exhaustive_4 = 0;
//		long time_neighbor_30 = 0;
//		long time_exhaustive_6 = 0;
//		long time_neighbor_40 = 0;
//		long time_exhaustive_8 = 0;
//		long time_neighbor_50 = 0;
//		long time_exhaustive_10 = 0;
//		long startTime = 0;
//		long endTime = 0;
//		for (int i = 0; i < numIterations; ++i) {
//			//4 inputs
//			startTime = System.nanoTime();
//			nearest_neighbor(20, points_20);
//			endTime = System.nanoTime();
//
//			time_neighbor_20 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			exhaustive_search(4, points_4);
//			endTime = System.nanoTime();
//			
//			time_exhaustive_4 += (endTime - startTime);
//			
//			//6 inputs
//			startTime = System.nanoTime();
//			nearest_neighbor(30, points_30);
//			endTime = System.nanoTime();
//
//			time_neighbor_30 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			exhaustive_search(6, points_6);
//			endTime = System.nanoTime();
//			
//			time_exhaustive_6 += (endTime - startTime); 
//			
//			//8 inputs
//			startTime = System.nanoTime();
//			nearest_neighbor(40, points_40);
//			endTime = System.nanoTime();
//
//			time_neighbor_40 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			exhaustive_search(8, points_8);
//			endTime = System.nanoTime();
//			
//			time_exhaustive_8 += (endTime - startTime); 
//			
//			//10 inputs
//			startTime = System.nanoTime();
//			nearest_neighbor(50, points_50);
//			endTime = System.nanoTime();
//
//			time_neighbor_50 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			exhaustive_search(10, points_10);
//			endTime = System.nanoTime();
//			
//			time_exhaustive_10 += (endTime - startTime); 
//		}
//		
//		time_neighbor_20 = time_neighbor_20 / numIterations;
//		time_exhaustive_4 = time_exhaustive_4 / numIterations;
//		System.out.println("20 and 4 inputs. Average neighbor: " + time_neighbor_20 + ", Average exhaustive: " + time_exhaustive_4);
//		
//		time_neighbor_30 = time_neighbor_30 / numIterations;
//		time_exhaustive_6 = time_exhaustive_6 / numIterations;
//		System.out.println("30 and 6 inputs. Average neighbor: " + time_neighbor_30 + ", Average exhaustive: " + time_exhaustive_6);
//		
//		time_neighbor_40 = time_neighbor_40 / numIterations;
//		time_exhaustive_8 = time_exhaustive_8 / numIterations;
//		System.out.println("40 and 8 inputs. Average neighbor: " + time_neighbor_40 + ", Average exhaustive: " + time_exhaustive_8);
//		
//		time_neighbor_50 = time_neighbor_50 / numIterations;
//		time_exhaustive_10 = time_exhaustive_10 / numIterations;
//		System.out.println("50 and 10 inputs. Average neighbor: " + time_neighbor_50 + ", Average exhaustive: " + time_exhaustive_10);
	}
	
	public static fileData fileParser(String fileName) {
		File file = new File(fileName);
		Scanner input;
		try {
			input = new Scanner(file);
			
			int n = Integer.parseInt(input.nextLine());
			ArrayList<point> points = new ArrayList<point>();
			point temp;
			while(input.hasNext()) {
				String[] line = input.nextLine().split(" ");
				temp = new point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
				points.add(temp);
			}

			input.close();
			
			return new fileData(n, points);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public static tspClass nearest_neighbor(int n, ArrayList<point> points) {
		/** Making an array to keep track of the points visited (in the order they are),
		 *	and the points that have yet to be visited. They are initialized with the 
		 *	first point in visited and all other points in neighbors
		 *	
		 *	Then as we go through, finding the nearest neighbor, that point will be
		 *	removed from neighbors and put into visited 
		 */
		double totalDist = 0;
		point currentPosition = points.get(0);
		ArrayList<point> neighbors = new ArrayList<point>();
		ArrayList<point> visited = new ArrayList<point>();
		visited.add(points.get(0));
		for (int i = 1; i < points.size(); ++i) {
			neighbors.add(points.get(i));
		}
		
		while(neighbors.size() != 0) {
			double shortestDist = distCalc(currentPosition, neighbors.get(0));
			int closestPoint = 0;
			for(int j = 1; j < neighbors.size(); ++j) {
				double temp = distCalc(currentPosition, neighbors.get(j)); 
				if (temp < shortestDist) {
					shortestDist = temp;
					closestPoint = j;
				}
			}
			totalDist += shortestDist;
			currentPosition = neighbors.get(closestPoint);
			visited.add(currentPosition);
			neighbors.remove(closestPoint);
		}
		visited.add(points.get(0));
		totalDist += distCalc(points.get(0), currentPosition);
		
		return new tspClass(totalDist, visited);
	}
	
	private static double distCalc (point a, point b) {
		double x = b.x - a.x;
		double y = b.y - a.y;
		return Math.hypot(x, y);
	}
	
	private static int factorialCalc (int n) {
		int result = 1;
		for (int i = 2; i <= n; ++i) {
			result = result * i;
		}
		
		return result;
	}
	
	private static double totalDist (ArrayList<point> input) {
		int n = input.size();
		double result = 0;
		
		for (int i = 1; i < n; ++i) {
			result += distCalc(input.get(i), input.get(i - 1));
		}
		
		//need to add the distance from the last point back to the first
		result += distCalc(input.get(n - 1), input.get(0));
		
		return result;
	}
	
	public static tspClass exhaustive_search(int n, ArrayList<point> points) {
		double shortestDist = 0;
		boolean shortestFound = false;
		ArrayList<point> optPath = new ArrayList<point>();
		
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
		    indexes[i] = 0;
		} 
		
		ArrayList<point> temp = points;
		
		int i = 0;
		double dist = 0;
		while (i < n) {
		    if (indexes[i] < i) {
		        swap(temp, i % 2 == 0 ?  0: indexes[i], i);
		        dist = totalDist(temp);

		        if (!shortestFound) {
		        	shortestDist = dist;
		        	optPath = temp;
		        	shortestFound = true;
		        } else if (dist < shortestDist) {
		        	shortestDist = dist;
		        	optPath = temp;
		        }
		        indexes[i]++;
		        i = 0;
		    }
		    else {
		        indexes[i] = 0;
		        i++;
		    }
		}
		
		optPath.add(optPath.get(0));
		return new tspClass(shortestDist, optPath);
	}
	
	private static void swap(ArrayList<point> input, int a, int b) {
	    point tmp = input.get(a);
	    input.set(a, input.get(b));
	    input.set(b, tmp);
	}
	
	private static ArrayList<point> generateList(int n) {
		ArrayList<point> result = new ArrayList<point>();
		
		Random rand = new Random();
		
		int x = 0;
		int y = 0;
		point temp;
		
		for (int i = 0; i < n; ++i) {
			x = rand.nextInt(100);
			y = rand.nextInt(100);
			temp = new point(x, y);
			result.add(temp);
		}
		
		return result;
	}
}
