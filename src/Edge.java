public class Edge implements Comparable<Edge> {

	private String destination;
	private int timeCost;
	private int distanceCost;
	private boolean isHighway;
	
	public Edge(String destination, int timeCost, int distanceCost, boolean isHighway) {
		this.destination = destination;
		this.timeCost = timeCost;
		this.distanceCost = distanceCost;
		this.isHighway = isHighway;
	}

	public Edge(String line) throws Exception {
		String[] args = line.split("\t");

		args = line.substring(line.split("\t")[0].length()).trim().split("\t");

		destination = args[0];
		timeCost = Integer.parseInt(args[1]);
		distanceCost = Integer.parseInt(args[2]);
		if (args.length > 3) isHighway = Boolean.parseBoolean(args[3]) || args[3].equals("1");
	}
	
	public String getDestination() { return destination; }
	public int getTimeCost() { return timeCost; }
	public int getDistanceCost() { return distanceCost; }
	public boolean getIsHighway() { return isHighway; }

	@Override
	public int compareTo(Edge o) {
		return 1;
	}
	
	@Override
	public String toString() {
		return destination + " " + timeCost + " " + distanceCost;
	}
}
