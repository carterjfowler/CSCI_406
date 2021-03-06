import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class algorithm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		maze _maze = fileParser("/Users/carterfowler/Desktop/Comp_Sci/406/Code/Maze/src/input");
		//make an array list of the graph
		ArrayList<node> graph = makeGraph(_maze);
		
		dijkstra(graph, _maze.cols);
		ArrayList<point> tempOrder = traceback(graph, _maze.cols, _maze.rows, _maze.rows * _maze.cols);
		String order = "";
		//reverse the order to make the string, add one to row and columns to make more readable
		for (int i = tempOrder.size() - 1; i >= 0; --i) {
			int row = tempOrder.get(i).row + 1;
			int col = tempOrder.get(i).col + 1;
			order += "(" + row + ", " + col + ") ";
		}
		System.out.println("Resulting order is: " + order);
	}
	
	public static ArrayList<point> traceback(ArrayList<node> graph, int rowSize, int colSize, int offset) {
		ArrayList<point> result = new ArrayList<point>();
		
		int finalPoint = rowSize * colSize - 1;
		node parent = graph.get(finalPoint);
		while (parent != null) {
			result.add(parent.location);
			node tempParent = parent.parent;
			if (tempParent != null) {
				int index = 0;
				int row = tempParent.location.row;
				int col = tempParent.location.col;
				int dist = tempParent.location.distance;
				color colorType = tempParent.location.colorType;
				
				if (row == rowSize - 1 && col == colSize - 1) {
					//points to position 63, so no offset
					index = (row * rowSize) + col;
				}
				//positive and going to black
				else if(dist > 0 && colorType == color.black) {
					//no offset
					index = (row * rowSize) + col;
				}
				//negative and going to red
				else if(dist < 0 && colorType == color.red) {
					//no offset
					index = (row * rowSize) + col;
				}
				//positive and going to red
				else if(dist > 0 && colorType == color.red) {
					//offset
					index = (row * rowSize) + col + offset;
				}
				//negative and going to black
				else if(dist < 0 && colorType == color.black) {
					//offset
					index = (row * rowSize) + col + offset;
				}
				parent = graph.get(index);
			} else {
				break;
			}
		}
		
		return result;
	}
	
	public static void dijkstra(ArrayList<node> graph, int rowSize) {
		Set<node> s = new LinkedHashSet<node>();
		//unable to use a priority queue due to set up, so using the ArrayList graph as an approximation
		ArrayList<node> q = (ArrayList<node>) graph.clone();
		int minVal = 2000000000;
		node currentNode = q.get(0);
		currentNode.depth = 0;
		int row = 0;
		int col = 0;
		int index = 0;
		int qIndex = 0;

		boolean removeNode = false;
		int infiniteLoopCount = 0;
		while (!q.isEmpty()) {
			//find minimum depth
			minVal = 2000000000;
			removeNode = false;
			for (int i = 0; i < q.size(); ++i) {
				if (q.get(i).depth < minVal) {
					minVal = q.get(i).depth;
					currentNode = q.get(i);
					qIndex = i;
					removeNode = true;
				}
			}
			//prevent infinite loops
			if (minVal == 2000000000) {
				++infiniteLoopCount;
			} else {
				infiniteLoopCount = 0;
			}
			if (infiniteLoopCount > 1000) {
				break;
			}
			
			if (removeNode) q.remove(qIndex);
			//now I can add the node I'm looking at to the set
			s.add(currentNode);
			//go over all the points in the adjacency list associated to this node
			for (node adj: currentNode.edges) {
				if (adj.depth > (currentNode.depth + currentNode.edgeWeight)) {
					adj.depth = (currentNode.depth + currentNode.edgeWeight);
					adj.parent = currentNode;
				}
			}
		}
		for (node tempNode: s) {
			row = tempNode.location.row;
			col = tempNode.location.col;
			int dist = tempNode.location.distance;
			for (node g: graph) {
				if (g.location.row == row && g.location.col == col && g.location.distance == dist) {
					g = tempNode;
				}
			}
		}
	}
	

	public static ArrayList<node> makeGraph(maze input) {
		ArrayList<ArrayList<node>> inputMaze = input.table;
		ArrayList<node> result = new ArrayList<node>();
		int rowLength = input.cols;
		//make the graph
		for (int row = 0; row < inputMaze.size(); ++row) {
			for (int col = 0; col < inputMaze.get(0).size(); ++col) {
				//don't need the duplicate version of end point
				if(!(row == (input.rows * 2 - 1) && col == input.cols - 1)) {
					result.add(inputMaze.get(row).get(col));
				}
			}
		}
		
		//now update the edge lists
		ArrayList<node> newEdges = new ArrayList<node>();
		for (node tempNode: result) {
			int index = 0;
			int offset = input.rows * input.cols;
			newEdges = new ArrayList<node>();
			for (node adj: tempNode.edges) {
				int row = adj.location.row;
				int col = adj.location.col;
				//base case
				if (row == input.rows - 1 && col == input.cols - 1) {
					//points to position 63, so no offset
					index = (row * input.rows) + col;
					newEdges.add(result.get(index));
				}
				//moving diagonally, offset
				else if ((Math.abs(tempNode.location.row - row) == Math.abs(tempNode.location.distance)) && (Math.abs(tempNode.location.col - col) == Math.abs(tempNode.location.distance))) {
					//offset
					index = (row * input.rows) + col + offset;
					newEdges.add(result.get(index));
				}
				//moving horizontally or vertically, no offset
				else {
					//no offset
					index = (row * input.rows) + col;
					newEdges.add(result.get(index));
				}
			}
			tempNode.edges = newEdges;
		}
		
		return result;
	}
	
	public static maze fileParser(String fileName) {
		File file = new File(fileName);
		Scanner input;
		try {
			input = new Scanner(file);
			
			String[] size = input.nextLine().split(" ");
			int rows = Integer.parseInt(size[0]);
			int cols = Integer.parseInt(size[1]);
			ArrayList<ArrayList<node>> graph = new ArrayList<ArrayList<node>>();
			ArrayList<node> tempRow = new ArrayList<node>();
			node tempNode;
			int rowCount = 0;
			int colCount = 0;
			ArrayList<node> edges = new ArrayList<node>();
			//instead of making black and red edges for a single node, make 2 nodes at once, one red and one black
			//be sure to properly mark edge colors
			while(input.hasNext()) {
				String[] line = input.nextLine().split(" ");
				colCount = 0;
				tempRow = new ArrayList<node>();
				for (String i : line) {
					edges = new ArrayList<node>();
					
					int dist = Integer.parseInt(i);
					color pointColor;
					if (dist < 0) pointColor = color.red;
					else pointColor = color.black;
					point currentPoint = new point(rowCount, colCount, pointColor, dist);
					dist = Math.abs(dist);
					
					point tempPoint;
					node edgeNode;
					//don't wan't to make edges for the bottom right point of the maze
					//not necessary but saves memory
					if (!(rowCount == (rows - 1) && colCount == (cols - 1))) {
						//make blackEdges
						if (currentPoint.distance > 0) {
							if (rowCount - dist >= 0) {
								tempPoint = new point(rowCount - dist, colCount, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (rowCount + dist < rows) {
								tempPoint = new point(rowCount + dist, colCount, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (colCount - dist >= 0) {
								tempPoint = new point(rowCount, colCount - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (colCount + dist < cols) {
								tempPoint = new point(rowCount, colCount + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							}
						}
						
						//make redEdges
						else if (currentPoint.distance < 0) {
							if (rowCount - dist >= 0 && colCount - dist >= 0) {
								tempPoint = new point(rowCount - dist, colCount - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (rowCount + dist < rows && colCount - dist >= 0) {
								tempPoint = new point(rowCount + dist, colCount - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (rowCount - dist >= 0 && colCount + dist < cols) {
								tempPoint = new point(rowCount - dist, colCount + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (rowCount + dist < rows && colCount + dist < cols) {
								tempPoint = new point(rowCount + dist, colCount + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							}
						}
					}
					
					tempNode = new node(currentPoint, null, edges);
					tempNode.edgeWeight = Math.abs(dist);

					//add the node we just made to the row
					tempRow.add(tempNode);
					
					++colCount;
				}
				++rowCount;
				graph.add(tempRow);
			}

			input.close();
			
			for (int i = 0; i < rows; ++i) {
				tempRow = new ArrayList<node>();
				for (int j = 0; j < cols; ++j) {
					edges = new ArrayList<node>();
					
					int dist = (-1) * graph.get(i).get(j).location.distance;
					color pointColor;
					if (dist > 0) pointColor = color.red;
					else pointColor = color.black;
					point currentPoint = new point(i, j, pointColor, dist);
					dist = Math.abs(dist);

					point tempPoint;
					node edgeNode;
					//don't wan't to make edges for the bottom right point of the maze
					//not necessary but saves memory
					if (!(i == (rows - 1) && j == (cols - 1))) {
						//make blackEdges
						if (currentPoint.distance > 0) {
							if (i - dist >= 0) {
								tempPoint = new point(i - dist, j, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (i + dist < rows) {
								tempPoint = new point(i + dist, j, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (j - dist >= 0) {
								tempPoint = new point(i, j - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (j + dist < cols) {
								tempPoint = new point(i, j + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							}
						}
						
						//make redEdges
						else if (currentPoint.distance < 0) {
							if (i - dist >= 0 && j - dist >= 0) {
								tempPoint = new point(i - dist, j - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (i + dist < rows && j - dist >= 0) {
								tempPoint = new point(i + dist, j - dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (i - dist >= 0 && j + dist < cols) {
								tempPoint = new point(i - dist, j + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							} if (i + dist < rows && j + dist < cols) {
								tempPoint = new point(i + dist, j + dist, color.none, 0);
								edgeNode = new node(tempPoint, null, null);
								edges.add(edgeNode);
							}
						}
					}
					
					tempNode = new node(currentPoint, null, edges);
					tempNode.edgeWeight = Math.abs(dist);

					//add the node we just made to the row
					tempRow.add(tempNode);
				}
				graph.add(tempRow);
			}
			
			return new maze(rows, cols, graph);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
