import java.util.ArrayList;

public class dynamic {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//set up the calculations table
		int k = 4;
		int n = 20;
		ArrayList<ArrayList<Integer>> calculations = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		for (int j = 0; j < k; ++j) {
			tempArray.add(-1);
		}
		//populate the table with the row template
		for (int i = 0; i < n; ++i) {
			calculations.add(tempArray);
		}
		
		//traceback table
		ArrayList<ArrayList<point>> traceback = new ArrayList<ArrayList<point>>();
		ArrayList<point> tempPointArray = new ArrayList<point>();
		for (int j = 0; j < k; ++j) {
			point tempPoint = new point(-1,-1);
			tempPointArray.add(tempPoint);
		}
		//populate the table with the row template
		for (int i = 0; i < n; ++i) {
			traceback.add(tempPointArray);
		}
		
//		S = (10 6 7 3 8 5 7 9 11 7 15 10 12 6 19 7 3 12 14 6); k = 4.
		//make test data
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(10);
		array.add(6);
		array.add(7);
		array.add(3);
		array.add(8);
		array.add(5);
		array.add(7);
		array.add(9);
		array.add(11);
		array.add(7);
		array.add(15);
		array.add(10);
		array.add(12);
		array.add(6);
		array.add(19);
		array.add(7);
		array.add(3);
		array.add(12);
		array.add(14);
		array.add(6);
		
		//algorithm call
		int result = dynamicAlg(array, n, k, calculations, traceback);
		//string is wrong, fix at lunch!!
		String partitioned = tracebackAlg(traceback, array);
		System.out.println(partitioned + "\n" + result);
	}
	
	public static int dynamicAlg(ArrayList<Integer> array, int n, int k, ArrayList<ArrayList<Integer>> calculations, ArrayList<ArrayList<point>> traceback) {
		//insert recursive algorithm
		int result = 0;
		int min = 0;
		int tracebackRow = 0;
		point tempPoint = new point(-1, -1);
		ArrayList<point> tempPointRow = new ArrayList<point>();
		ArrayList<Integer> tempRow = new ArrayList<Integer>();
		for (int col = 0; col < k; ++col) {
			for (int row = 0; row < n; ++row) {
				result = 0;
				if (row == 0 && col == 0) {
					result = array.get(0);
				} else if (col > row) {
					result = 0;
				} else if (col == 0) {
					result = total(array, 0, row + 1);
				} else {
					tracebackRow = 0;
					for (int i = 0; i < row; ++i) {
						min = minimum(calculations.get(i).get(col - 1), total(array, i + 1, row + 1));
						if (min > result) {
							result = min;
							tracebackRow = i;
						}
					}
				}
				tempRow = new ArrayList<Integer>();
				tempRow = (ArrayList<Integer>) calculations.get(row).clone();
				tempRow.set(col, result);
				calculations.set(row, tempRow);
				
				tempPoint = new point(tracebackRow, col - 1);
				tempPointRow = new ArrayList<point>();
				tempPointRow = (ArrayList<point>) traceback.get(row).clone();
				tempPointRow.set(col, tempPoint);
				traceback.set(row, tempPointRow);
			}
		}
		
		return calculations.get(n - 1).get(k - 1);
	}
	
	//write traceback algorithm!!
	private static String tracebackAlg(ArrayList<ArrayList<point>> traceback, ArrayList<Integer> input) {
		String result = "";
		ArrayList<Integer> breakpoints = new ArrayList<Integer>();
		
		int currentCol = traceback.get(0).size() - 1;
		int currentRow = traceback.size() - 1;
		while (currentCol != -1) {
			currentRow = traceback.get(currentRow).get(currentCol).row;
			currentCol = traceback.get(currentRow).get(currentCol).col;
			if (currentCol != -1) breakpoints.add(currentRow);
		}
		
		//Make a double for loop to go over the breakpoints then over the input array
		int currentEnd = input.size();
		int currentStart = 0;
		String temp = "";
		for (int i = 0; i <= breakpoints.size(); ++i) {
			if (i == breakpoints.size()) currentStart = 0;
			else currentStart = breakpoints.get(i) + 1;
			temp = "";
			for (int index = currentStart; index < currentEnd; ++index) {
				//get substring of length at breakpoints[i] + 1 from the end of the string
				//use 'D' to denote breakpoints
				temp += input.get(index).toString() + " ";
			}
			if (currentStart != 0) temp = "D " + temp;
			result = temp + result;
			currentEnd = currentStart;
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
		if (j < n) {
			for (int i = j; i < n; ++i) {
				result += array.get(i);
			}
		}
		
		return result;
	}

}
