
public class Vertex implements Comparable<Vertex> {
	private String symbol;
	private String data;
	
	public Vertex(String symbol, String data) {
		this.symbol = symbol;
		this.data = data;
	}
	
	public Vertex(String line) throws Exception {
		String[] args = line.split("\t");
//		if (args.length != 2) throw new Exception("line contained more than two items: " + line);
		symbol = args[0];
		data = args[1];
	}
	
	public String getSymbol() { return symbol; }
	public String getData() { return data; }
	
	@Override
	public String toString() {
		return symbol + " " + data;
	}
	
	@Override
	public int hashCode() {
	    int hash = 7;
	    hash = 31 * hash + symbol.hashCode();
	    return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o instanceof Vertex)
			return symbol.equals(((Vertex)o).symbol);
		
		return false;
	}

	@Override
	public int compareTo(Vertex o) {
		return symbol.compareTo(o.symbol);
	}
}
