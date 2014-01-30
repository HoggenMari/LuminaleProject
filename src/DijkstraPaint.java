import java.util.List;

public class DijkstraPaint {
	
	Vertex a1, b1, c1, d1, e1, f1, g1, h1;

	public DijkstraPaint() {
		
	}
	
	public void set(){
	a1 = new Vertex("Node1NozzleA");
	b1 = new Vertex("Node1NozzleB");
	c1 = new Vertex("Node1NozzleC");
	d1 = new Vertex("Node1NozzleD");
	e1 = new Vertex("Node1NozzleE");
	f1 = new Vertex("Node1NozzleF");
	g1 = new Vertex("Node1NozzleG");
	h1 = new Vertex("Node1NozzleH");
		
	a1.adjacencies = new Edge[]{ new Edge(b1, 1),
                new Edge(c1, 1),
                new Edge(d1, 1),
                new Edge(e1, 1),
                new Edge(f1, 1)};
	b1.adjacencies = new Edge[]{ new Edge(a1, 1),
                new Edge(c1, 1),
                new Edge(f1, 1),
                new Edge(g1, 1),};
	c1.adjacencies = new Edge[]{ new Edge(a1, 1),
                new Edge(b1, 1),
                new Edge(d1, 1),
                new Edge(g1, 1),
                new Edge(h1, 1)};
	d1.adjacencies = new Edge[]{ new Edge(a1, 1),
                new Edge(c1, 1),
                new Edge(e1, 1),
                new Edge(h1, 1)};
	e1.adjacencies = new Edge[]{ new Edge(a1, 1),
				 new Edge(d1, 1),
				 new Edge(f1, 1),
				 new Edge(f1, 1)};
	f1.adjacencies = new Edge[]{ new Edge(a1, 1),
				 new Edge(b1, 1),
				 new Edge(e1, 1),
				 new Edge(h1, 1)};
	g1.adjacencies = new Edge[]{ new Edge(b1, 1),
				 new Edge(c1, 1),
				 new Edge(h1, 1)};
	h1.adjacencies = new Edge[]{ new Edge(c1, 1),
				 new Edge(d1, 1),
				 new Edge(e1, 1),
				 new Edge(f1, 1),
				 new Edge(g1, 1)};
	}
	
	public void paint(){
	  Vertex[] vertices = { a1, b1, c1, d1, e1, f1, g1, h1 };
      Dijkstra.computePaths(a1);
      for (Vertex v : vertices)
      {
    	System.out.println("Distance to " + v + ": " + v.minDistance);
    	List<Vertex> path = Dijkstra.getShortestPathTo(v);
    	System.out.println("Path: " + path);
	  }
	}
}
