import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path> { // put into priority queue
	private String vertex;
	private int cost;
	private List<String> path;
	
	public Path(String vertex, int cost, List<String> list) {
		this.vertex = vertex;
		this.cost = cost;
		this.path = new ArrayList<String>(list);
	}
	
	public String getVertex() { return vertex; }
	public int getCost() { return cost; }
	public List<String> getPath() { return new ArrayList<String>(path); }
	public String getPathString() {
		String ret = "";
		for (String v : path)
			ret += v + "-";
		return ret.substring(0, ret.length() - 1);
	}
	
	@Override
	public int compareTo(Path other) {
		return cost - other.cost;
	}

	@Override
	public String toString() {
		return vertex + " " + cost + " " + getPathString();
	}
}
