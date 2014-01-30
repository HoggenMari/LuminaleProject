import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

public class Dijkstra
{
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
    Vertex a1 = new Vertex("Node1NozzleA");
	Vertex b1 = new Vertex("Node1NozzleB");
	Vertex c1 = new Vertex("Node1NozzleC");
	Vertex d1 = new Vertex("Node1NozzleD");
	Vertex e1 = new Vertex("Node1NozzleE");
	Vertex f1 = new Vertex("Node1NozzleF");
	Vertex g1 = new Vertex("Node1NozzleG");
	Vertex h1 = new Vertex("Node1NozzleH");

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
	Vertex[] vertices = { a1, b1, c1, d1, e1, f1, g1, h1 };
        computePaths(c1);
        for (Vertex v : vertices)
	{
	    System.out.println("Distance to " + v + ": " + v.minDistance);
	    List<Vertex> path = getShortestPathTo(v);
	    System.out.println("Path: " + path);
	}
    }
}

