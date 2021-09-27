import java.util.ArrayList;

public class node {
	point location;
	node parent;
	ArrayList<node> edges;
	int depth;					//depth variable for djikstra's algorithm
	int edgeWeight;					//storing edge weight for all edges coming off (from) this node
	
	public node(point _location, node _parent, ArrayList<node> _edges) {
		location = _location;
		parent = _parent;
		edges = _edges;
		depth = 2000000000; //initialize to max int it can store, will be reset earlier
		edgeWeight = 0;			//will be reset to the weight stored in location when initialized
	}
}
