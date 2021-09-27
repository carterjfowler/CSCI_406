import java.text.DecimalFormat;
import java.util.ArrayList;

public class tspClass {
	Double totalDist;
	ArrayList<point> path;
	
	public tspClass(Double dist, ArrayList<point> _path) {
		super();
		totalDist = dist;
		path = _path;
	}

	@Override
	public String toString() {
		String temp = "";
		for (int i = 0; i < path.size(); ++i) {
			temp += + path.get(i).x + " " + path.get(i).y + " \n";
		}
		DecimalFormat sigFig = new DecimalFormat("#0.000");
		return sigFig.format(totalDist) + " \n" + temp;
	}
	
}
