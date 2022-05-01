import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// TODO: Use your own Generic heap PriorityQueue to be used with your Dijkstra’ s Shortest algorithm

public class Dijkstra {
	static int totalCost; // TODO: find out what I'm supposed to do with this

	public static Path shortestPath(Map<Vertex, Set<Edge>> adjList, String startSymbol, String endSymbol) {
		PriorityQueue<Path> pq = new PriorityQueue<Path>(); // TODO: use my own priority queue
		ArrayList<String> visited = new ArrayList<String>();
		
		pq.add(new Path(startSymbol, 0, Arrays.asList(startSymbol)));
		
		while (!pq.isEmpty()) {
			Path nextEntry = pq.remove(); // removes lowest cost
			
			if (visited.contains(nextEntry.getVertex())) { // skip visited vertices b/c it must be a higher cost
				continue;
			} else { // replace with each unvisited child of nextEntry w/ updated pathStr and cost
				Set<Edge> neighbors = adjList.get(new Vertex(nextEntry.getVertex(), ""));
				
				for (Edge e : neighbors) {
					// TODO: path criteria, don't add if highway
					if (Graph.avoidHighways && e.getIsHighway())
						continue;
					
					List<String> newPath = nextEntry.getPath();
					newPath.add(e.getDestination());
					
					if (!visited.contains(e.getDestination())) {
						pq.add(new Path(
							e.getDestination(),
							nextEntry.getCost() + (Graph.useDistCost ? e.getDistanceCost() : e.getTimeCost()),
							newPath
						));
					}
				}
			}

			visited.add(nextEntry.getVertex());
			
			if (nextEntry.getVertex().equals(endSymbol)) return nextEntry;
		}
		
		return null;
	}

}
