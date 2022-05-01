import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Tester {
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Graph g = new Graph("MapInformation.txt");
		
		System.out.println(Dijkstra.shortestPath(g.adjList, "A", "K"));
		
		System.exit(0);
		
		Map<Vertex, Set<Edge>> graph = new HashMap<Vertex, Set<Edge>>();
		graph.put(new Vertex("A",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("B", 2, 2, false),
				new Edge("D", 5, 5, false),
				new Edge("E", 4, 4, false)
		)));
		graph.put(new Vertex("B",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("E", 1, 1, false)
		)));
		graph.put(new Vertex("C",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("B", 3, 3, false)
		)));
		graph.put(new Vertex("D",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("G", 2, 2, false)
		)));
		graph.put(new Vertex("E",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("H", 6, 6, false),
				new Edge("F", 3, 3, false)
		)));
		graph.put(new Vertex("F",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("C", 4, 4, false),
				new Edge("H", 3, 3, false)
		)));
		graph.put(new Vertex("G",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("H", 1, 1, false)
		)));
		graph.put(new Vertex("H",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("I", 1, 1, false)
		)));
		graph.put(new Vertex("I",""), new TreeSet<Edge>(Arrays.asList(
				new Edge("F", 1, 1, false)
		)));
		
		System.out.println(Dijkstra.shortestPath(graph, "A", "H").toString()); // path should be ADGH unless avoiding edges
	}
	

}
