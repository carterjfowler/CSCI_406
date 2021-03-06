import java.util.ArrayList;
import java.util.Random;

public class recursion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(1);
		array.add(2);
		array.add(4);
		array.add(5);
		
		int result = recursionAlg(array, 4, 3);
		
		System.out.println(result);
//		ArrayList<Integer> array5 = generateList(5);
//		ArrayList<Integer> array10 = generateList(10);
//		ArrayList<Integer> array15 = generateList(15);
//		ArrayList<Integer> array20 = generateList(20);
//		
//		long startTime = 0;
//		long endTime = 0;
//		int numIterations = 5;
//		
//		long time_5_5 = 0;
//		long time_5_10 = 0;
//		long time_5_15 = 0;
//		long time_10_5 = 0;
//		long time_10_10 = 0;
//		long time_10_15 = 0;
//		long time_15_5 = 0;
//		long time_15_10 = 0;
//		long time_15_15 = 0;
//		long time_20_5 = 0;
//		long time_20_10 = 0;
//		long time_20_15 = 0;
//		
//		//CHANGE THE N's
//		
//		for (int i = 0; i < numIterations; ++i) {
//			//n = 5
//			startTime = System.nanoTime();
//			recursionAlg(array5, 5, 5);
//			endTime = System.nanoTime();
//			time_5_5 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array5, 5, 10);
//			endTime = System.nanoTime();
//			time_5_10 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array5, 5, 15);
//			endTime = System.nanoTime();
//			time_5_15 += (endTime - startTime);
//			
//			//n = 10
//			startTime = System.nanoTime();
//			recursionAlg(array10, 10, 5);
//			endTime = System.nanoTime();
//			time_10_5 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array10, 10, 10);
//			endTime = System.nanoTime();
//			time_10_10 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array10, 10, 15);
//			endTime = System.nanoTime();
//			time_10_15 += (endTime - startTime);
//
//			//n = 15
//			startTime = System.nanoTime();
//			recursionAlg(array15, 15, 5);
//			endTime = System.nanoTime();
//			time_15_5 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array15, 15, 10);
//			endTime = System.nanoTime();
//			time_15_10 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array15, 15, 15);
//			endTime = System.nanoTime();
//			time_15_15 += (endTime - startTime);
//			
//			//n = 20
//			startTime = System.nanoTime();
//			recursionAlg(array20, 20, 5);
//			endTime = System.nanoTime();
//			time_20_5 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array20, 20, 10);
//			endTime = System.nanoTime();
//			time_20_10 += (endTime - startTime);
//			
//			startTime = System.nanoTime();
//			recursionAlg(array20, 20, 15);
//			endTime = System.nanoTime();
//			time_20_15 += (endTime - startTime);
//			
//			System.out.println("Iteration #" + i + " is done.");
//		}
//		
//		System.out.println("Runtime for n = 5, k = 5: " + time_5_5/numIterations);
//		System.out.println("Runtime for n = 5, k = 10: " + time_5_10/numIterations);
//		System.out.println("Runtime for n = 5, k = 15: " + time_5_15/numIterations);
//		System.out.println("Runtime for n = 10, k = 5: " + time_10_5/numIterations);
//		System.out.println("Runtime for n = 10, k = 10: " + time_10_10/numIterations);
//		System.out.println("Runtime for n = 10, k = 15: " + time_10_15/numIterations);
//		System.out.println("Runtime for n = 15, k = 5: " + time_15_5/numIterations);
//		System.out.println("Runtime for n = 15, k = 10: " + time_15_10/numIterations);
//		System.out.println("Runtime for n = 15, k = 15: " + time_15_15/numIterations);
//		System.out.println("Runtime for n = 20, k = 5: " + time_20_5/numIterations);
//		System.out.println("Runtime for n = 20, k = 10: " + time_20_10/numIterations);
//		System.out.println("Runtime for n = 20, k = 15: " + time_20_15/numIterations);
	}

	
	public static int recursionAlg(ArrayList<Integer> array, int n, int k) {  
		
		int result = 0;

		if (n == 1 && k == 1) {
			return array.get(0);
		}
		else if (k > n) {
			return 0;
		}
		else if (k == 1) {
			for (int i = 0; i < n; ++i) {
				result += array.get(i);
			}
			return result;
		}
		
		result = 0;
		int temp = 0;
		
		for (int i = 1; i <= n; ++i) {
			temp = minimum(recursionAlg(array, i, k - 1), total(array, i, n));
			if (temp > result) result = temp;
		}
		
		return result;
	}	
	
	private static int minimum(int a, int b) {
		if (a < b) {
			return a;
		} else {
			return b;
		}
	}
	
	private static int total(ArrayList<Integer> array, int j, int n) {
		int result = 0;
		
		for (int i = j; i < n; ++i) {
			result += array.get(i);
		}
		
		return result;
	}
	
	private static ArrayList<Integer> generateList(int n) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		Random rand = new Random();
		
		int x = 0;
		
		for (int i = 0; i < n; ++i) {
			x = rand.nextInt(100);
			result.add(x);
		}
		
		return result;
	}
}
