import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
	public static boolean useDistCost = true;
	public static boolean avoidHighways = false;
	public static boolean returnAddress;
	
	
	public Map<Vertex, Set<Edge>> adjList = new HashMap<Vertex, Set<Edge>>();
	
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
				adjList.put(new Vertex(line), new TreeSet<Edge>());
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
		return "";
	}
}
