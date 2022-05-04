import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {
	public static boolean useDistCost = true;
	public static boolean avoidHighways = false;
	public static boolean returnAddress = true;
	public static boolean avoidSpeedTraps = false;
	
	public Map<Vertex, List<Edge>> adjList = new HashMap<Vertex, List<Edge>>();
	public Map<String, String> addresses = new HashMap<String, String>();
	
	public Graph(String filename) throws Exception {
		enum ReadModes { NONE, NODE, EDGE };
		ReadModes mode = ReadModes.NONE;
		
		File myObj = new File(filename);

		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			
			if (line.contains("<Nodes>")) {
				myReader.nextLine(); // read over column names
				line = myReader.nextLine();
				mode = ReadModes.NODE;
			}
			else if (line.contains("<Edges>")) {
				myReader.nextLine(); // read over column names
				line = myReader.nextLine();
				mode = ReadModes.EDGE;
			}
			else if (line.contains("</")) mode = ReadModes.NONE;
			
			if (mode == ReadModes.NODE) {
				Vertex v = new Vertex(line);
				adjList.put(v, new ArrayList<Edge>());
				addresses.put(v.getSymbol(), v.getData());
			} else if (mode == ReadModes.EDGE) {
				String key = line.split("\t")[0];
				adjList.get(new Vertex(key, "")).add(
					new Edge(line)
				);
			}
			
		}
		myReader.close();
	}
	
	public String ToString() {
		// returns all vertices and costs (both dependent on the two static Booleans above 
		String ret = "";
		Set<Vertex> keys = adjList.keySet();
		for (Vertex key : keys) {
			ret += key.getSymbol() + (returnAddress ? " Address: " + addresses.get(key.getSymbol()) : "") + "\n";
			for (Edge edge : adjList.get(key)) {
				ret += "  " + edge.getDestination() + " Cost: " +
						(useDistCost ? edge.getDistanceCost() : edge.getTimeCost()) + 
						(returnAddress ? " Address: " + addresses.get(key.getSymbol()) : "") + "\n";
			}
		}
		return ret;
	}
}
