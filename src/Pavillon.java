import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import processing.core.PApplet;



public class Pavillon {

	private String IP_ADRESS;
	private int PORT;
	private int ID;
	
	PriorityQueue<Nozzle> vertexQueue = new PriorityQueue<Nozzle>();
	List<Nozzle> path = new ArrayList<Nozzle>();
	
	private static byte port_map[] =
		{1, 1, 1, 1, 2, 2, 2, 2,
		 3, 3, 3, 3, 3, 4, 4, 4, 4, 4,
		 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6,
		 7, 7, 7, 7, 8, 8, 8, 8, -1};
	
	private static byte adj_matrix1[][] =
		{ { 0, 1, 1, 1, 1, 1, 0, 0 }, 
		  { 1, 0, 1, 0, 0, 1, 1, 0 }, 
		  { 1, 1, 0, 1, 0, 0, 1, 1 },
		  { 1, 0, 1, 0, 1, 0, 0, 1 },
		  { 1, 0, 0, 1, 0, 1, 0, 0 },
		  { 1, 1, 0, 0, 1, 0, 0, 1 },
		  { 0, 1, 1, 0, 0, 0, 0, 1 },
		  { 0, 0, 1, 1, 1, 1, 1, 0 }};
	
	private static byte adj_matrix2[][] =
		{ { 0, 1, 1, 1, 1, 0, 1, 0, 0, 0 }, 
		  { 1, 0, 1, 1, 0, 1, 0, 1, 0, 0 }, 
		  { 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 },
		  { 1, 1, 0, 0, 1, 1, 0, 0, 0, 1 },
		  { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 },
		  { 0, 1, 0, 1, 0, 0, 0, 1, 0, 1 },
		  { 1, 0, 1, 0, 1, 0, 0, 0, 1, 1 },
		  { 0, 1, 1, 0, 0, 1, 0, 0, 1, 1 },
		  { 0, 0, 1, 0, 0, 0, 1, 1, 0, 1 },
		  { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 }};
	
	ArrayList<Nozzle> nozzleList = new ArrayList<Nozzle>();
	private InetAddress dest;
	private MulticastSocket socket;
	private PApplet p;

	public String getIP_ADRESS() {
		return IP_ADRESS;
	}
	
	public void setIP_ADRESS(String ip) {
		IP_ADRESS = ip;
	}
	
	public int getPORT() {
		return PORT;
	}
	
	public void setPORT(int port) {
		PORT = port;
	}

	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		ID = id;
	}
	
	public Pavillon(PApplet parent, String ip, int i, int j) {
		IP_ADRESS = ip;
		PORT = i;
		ID = j;
		p = parent;
		
		try {
			socket = new MulticastSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void add(Node... nodes){
		for(Node node : nodes){
		  for(int i=0; i<node.nozzleList.size(); i++){
		    nozzleList.add(node.nozzleList.get(i));
		  }
		}
	}
	
	
    public List<Nozzle> computePaths(Nozzle source, Nozzle target)
    {
        source.minDistance = 0.;
        //PriorityQueue<Nozzle> vertexQueue = new PriorityQueue<Nozzle>();
        vertexQueue.clear();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Nozzle u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Nozzle v = e.target;
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
	//List<Nozzle> path = new ArrayList<Nozzle>();
	path.clear();
    for (Nozzle vertex = target; vertex != null; vertex = vertex.previous)
        path.add(vertex);
    Collections.reverse(path);
    return path;
	
    }
    
    public List<Nozzle> getShortestPathTo(Nozzle target)
    {
        List<Nozzle> path = new ArrayList<Nozzle>();
        for (Nozzle vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    
	
	public void setAdj(){
		for (int i = 0; i < adj_matrix1.length; i++){
		  for (int j = 0; j < adj_matrix1[i].length; j++){
			//nozzleList.get(i).adjacencies.add(new Edge());
			if(adj_matrix1[i][j]>0){
			  nozzleList.get(i).adjacencies.add(new Edge(nozzleList.get(j),1));
		      System.out.println(adj_matrix1[i][j]);
			}
		  }
		}
		
		
		/*for(Nozzle nz : nozzleList){
			nz.compareTo(nozzleList.get(0));
		}*/
		
		//Nozzle[] nozzles = { nozzleList.get(0) };
		//List<Nozzle> path = computePaths(nozzleList.get(0), nozzleList.get(7));
        //for (Nozzle v : nozzleList)
        //for(int i=0; i<8; i++)
	//{
	    //System.out.println("Distance to " + nozzleList.get(0) + ": " + nozzleList.get(7).minDistance);
	    //List<Nozzle> path = getShortestPathTo(nozzleList.get(i));
	    //System.out.println("Path: " + path);
	//}
	}

	public void send() {
		byte[] data = new byte[1472];
		
		int nozzle_count = 0;

		for(int i=0; i<8; i++){
		int dataIndex = 0;

		data[dataIndex++] = 'Y';
		data[dataIndex++] = 'T';
		data[dataIndex++] = 'K';
		data[dataIndex++] = 'J';
		
		data[dataIndex++] = (byte) ID;
		data[dataIndex++] = 0;
		data[dataIndex++] = 0x57;
		data[dataIndex++] = 0x05;
		
		data[dataIndex++] = (byte) 1;
		data[dataIndex++] = 0;
		
		int channel = i*2048;
		
		data[dataIndex++] = (byte) (channel & 0xff);
		data[dataIndex++] = (byte) ((channel >> 8) & 0xff);

		data[dataIndex++] = (byte) ((1458) & 0xff);
		data[dataIndex++] = (byte) (((1458) >> 8) & 0xff);
		
		
		do{
			byte[] buffer = nozzleList.get(nozzle_count).data;
			
			//Now write the RGB-values
			for(int j=0; j<buffer.length; j++) {
				data[dataIndex++] = buffer[j];
			}
			
			nozzle_count++;
			System.out.println(nozzle_count);
		}while(port_map[nozzle_count]==i+1);
		
		for(int j=dataIndex; j<1458; j++){
			data[dataIndex++]= (byte) 0;
		}
				
		DatagramPacket dp = new DatagramPacket(data, data.length);
        dp.setPort(PORT);
        try {
			dest = InetAddress.getByName(IP_ADRESS);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        dp.setAddress(dest);
        
        System.out.println("SEND-TO-ADRESS: "+dp.getAddress());
        System.out.println("SEND-TO-PORT: "+dp.getPort());
        
        //p.delay(50);
        
        //for(int k=0; k<data.length; k++){
			p.delay(1);
        	//System.out.println("DATA: "+i+" "+k+" "+data[k]);
		//}

        try {
          socket.send(dp);
        } catch (IOException e) {
        	
        }
		
		}
		
	}
}
