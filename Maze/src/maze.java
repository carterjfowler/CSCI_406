import java.util.ArrayList;

public class maze {
	int rows;
	int cols;
	ArrayList<ArrayList<node>> table;
	
	public maze (int _rows, int _cols, ArrayList<ArrayList<node>> _table) {
		rows = _rows;
		cols = _cols;
		table = _table;
	}
}
