import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Dijkstra {
	public static Path shortestPath(Map<Vertex, List<Edge>> adjList, String startSymbol, String endSymbol) {
		MinHeapPriorityQueue<Path> pq = new MinHeapPriorityQueue<Path>();
		ArrayList<String> visited = new ArrayList<String>();
		
		pq.add(new Path(startSymbol, 0, Arrays.asList(startSymbol)));
		
		while (!pq.isEmpty()) {
			Path nextEntry = pq.remove(); // removes lowest cost
			
			if (visited.contains(nextEntry.getVertex())) { // skip visited vertices b/c it must be a higher cost
				continue;
			} else { // replace with each unvisited child of nextEntry w/ updated pathStr and cost
				List<Edge> neighbors = adjList.get(new Vertex(nextEntry.getVertex(), ""));
				
				for (Edge e : neighbors) {
					if ((Graph.avoidHighways && e.getIsHighway()) ||
						(Graph.avoidSpeedTraps && e.getIsSpeedTrap()))
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
