import java.text.DecimalFormat;

public class point {
	int row;
	int col;
	color colorType;
	int distance;
	
	public point(int _row, int _col, color _colorType, int _distance) {
		row = _row;
		col = _col;
		colorType = _colorType;
		distance = _distance;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}
