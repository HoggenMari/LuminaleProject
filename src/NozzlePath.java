import java.util.*;

//The following site was very helpful for coding this up
//http://www.cs.panam.edu/~meng/Course/CS6345/Notes/chpt-5/node10.html
//Jeremy Carr
//This class contains a function shortest which takes an edge cost grid
//and the total number of nodes and returns a vector containing an
//ordered list of nodes to visit to move from start to target.

public class NozzlePath
{
    static int n = 4;
    static int start = 0;
    static int target = 1;
    //static int curnode = start;
    
    public static void main(String[]args) {
	//Dummy matrix containing the edge cost grid
	double [][] cost = {{0,20,2,15,40},{20,0,-1,3,30},{2,-1,0,7,34},{15,3,7,0,34}};
	Vector solution = shortest (cost, n, start, target);

	//Prints solution, a vector containing an ordered list of 
	//nodes to visit to move from start node to target node
	for (int i = 0; i < solution.size(); i++) {
	    System.out.println (solution.elementAt(i));
	}
					
    }
   
    //Returns a vector containing a list of nodes to move from start to target
    //according to the shortest path
    public static Vector shortest(double cost[][], int n, int start, int target) {
	// shortest takes an nxn matrix cost of edge costs and produces
	// an nxn matrix shortestPath of lengths of shortest paths, 
	//and an nxn matrix P giving the node history to get to that node
	int i,j,k;
	int [][] history = new int [n][n];
	double [][] shortestPath = new double[n][n];

	//init the three matrices
      for (i=0; i<n; i++)
	  for (j=0; j<n; j++) {
		  shortestPath[i][j] = cost[i][j];
		  history[i][j] = -1;
        }
      for (i=0; i<n; i++)
	  shortestPath[i][i] = 0;              // no self cycle
      
      //Grunt work- Is edge ik+kj shorter than ij?  If so, update the solution matrices
      for (k=0; k<n; k++)
	  for (i=0; i<n; i++)
	      for (j=0; j<n; j++) {
		  
		  if (shortestPath[i][k] > 0 &&
		      shortestPath[k][j] > 0 &&
		      shortestPath[i][j] > 0 &&
		      shortestPath[i][k]+shortestPath[k][j] < shortestPath[i][j]) {
		      //two edges are shorter than one, update the shortestPath matrix
		      shortestPath[i][j] = shortestPath[i][k] + shortestPath[k][j];
		      shortestPath[j][i] = shortestPath[i][k] + shortestPath[k][j];
		      history[i][j] = k;        // k is included in the shortest path
		      history[j][i] = k;
		  }
	      }
      /*  Help for debugging:
      //Prints the history matrix
      for (int x=0; x<n; x++) {
	  System.out.println();
	  for (int y=0; y<n; y++)
	      System.out.print(history[x][y] + " ");
      }
      System.out.println();
      
      //Prints the shortestPath matrix
      for (int x=0; x<n; x++) {
	  System.out.println();
	  for (int y=0; y<n; y++)
	      System.out.print(shortestPath[x][y] + " ");
      }
      */
      
      //Traverse history correctly - get from 0 to target
      Vector path = new Vector();
      path.addElement(new Integer(target));
      int curnode = history[start][target];
      while (curnode != -1) {
	  path.insertElementAt(new Integer(curnode),0);
	  curnode = history[start][curnode];
      }

      path.insertElementAt(new Integer(start), 0);
      return path;
	      
    } //shortest
}