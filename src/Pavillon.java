import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;



public class Pavillon {

	private String IP_ADRESS;
	private int PORT;
	private int ID;

	private static byte port_map[] =
		{1, 1, 1, 1, 2, 2, 2, 2,
		 3, 3, 3, 3, 3, 4, 4, 4, 4, 4,
		 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6,
		 7, 7, 7, 7, 8, 8, 8, 8};
	
	ArrayList<Nozzle> nozzleList = new ArrayList<Nozzle>();
	private InetAddress dest;
	private MulticastSocket socket;

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
	
	public Pavillon(String ip, int i, int j) {
		IP_ADRESS = ip;
		PORT = i;
		ID = j;
		
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

	public void send() {
		byte[] data = new byte[1472];
		
		int nozzle_count = 0;

		for(int i=0; i<2; i++){
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
		}while(port_map[nozzle_count]==i+1);
		
		for(int j=dataIndex; j<1458; j++){
			data[dataIndex++]= (byte) 40;
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
        
        for(int k=0; k<data.length; k++){
			//System.out.println("DATA: "+i+" "+k+" "+data[k]);
		}

        try {
          socket.send(dp);
        } catch (IOException e) {
        	
        }
		
		}
		
	}
}
