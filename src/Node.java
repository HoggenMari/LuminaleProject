
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Node {
	private int MAX_DATA = 1500;

	private PApplet p;
	ArrayList<Nozzle> nozzleList = new ArrayList<Nozzle>();
	private PImage image;


	private final String ip = "224.1.1.1";
	private InetAddress dest;
	private MulticastSocket socket;
	private final int port = 5026;

	private int ledsOnPort;

	private int[] ports;


	public Node(PApplet p, int[] ports) {
		this.p = p;
		this.ports = ports;

	}
	
	public void add(int[] leds_of_nozzle) {
		for(int i=0; i<leds_of_nozzle.length; i++){
			nozzleList.add(new Nozzle(p,leds_of_nozzle[i]));
		}
		
		int sum = 0;
		for (int i : leds_of_nozzle) {
		sum += i;
		}
		
		ledsOnPort = sum; 
		
		System.out.println(sum);
		
        try {
			socket = new MulticastSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void update(PImage image) {
		this.image = image;
	}
	
	public void drawOnGui(int x, int y) {
		for (int i=0; i<nozzleList.size(); i++) {
			nozzleList.get(i).update();
			nozzleList.get(i).drawOnGui(x, y+i*60);
			
		}
	}
	
	public void sendNode(int controller) throws IOException {
		int ports_in_use = ports.length;
		int count=0;
		int send;
		if(nozzleList.size()%ports_in_use==0){
			send = nozzleList.size()/ports_in_use;
		} else {
			send = nozzleList.size()/ports_in_use+1;
		}
		
		
		
		for(int i=0; i<2; i++){
		byte[] data = new byte[914];
		int rgb_on_port=0;


		System.out.println("SEND: "+send);

		data[0] = 'Y';
		data[1] = 'T';
		data[2] = 'K';
		data[3] = 'J';
		
		data[4] = (byte) controller;
		data[5] = 0;
		data[6] = 0x57;
		data[7] = 0x05;
		
		data[8] = (byte) 2;
		data[9] = 0;

		int dataIndex = 10;

		int channel = i*2048;
		// Now map the pixels
		
			for(int ctr=count; ctr<send; ctr++){
				rgb_on_port += nozzleList.get(count).data.length;
				System.out.println("rgb_on_port"+rgb_on_port);
			}
			
			System.out.println("rgb_on_port: "+rgb_on_port);
			
			data[dataIndex++] = (byte) (channel & 0xff);
			data[dataIndex++] = (byte) ((channel >> 8) & 0xff);

			data[dataIndex++] = (byte) ((900) & 0xff);
			data[dataIndex++] = (byte) (((900) >> 8) & 0xff);
	
			
				
			while(count<send){
			
			//System.out.println(nozzleList.get(count).data.length);
			
			for(int j=0; j<nozzleList.get(count).data.length; j++) {
				//System.out.println("i: "+j+" "+nozzleList.get(0).data[j]);
				data[dataIndex++] = nozzleList.get(count).data[j];
			}
			
			
			//System.out.println(dataIndex+" "+nozzleList.get(count).data.length);
			
			count++;
			
			System.out.println("COUNT: "+count);
			
			}	
			
			//count=0;
			send=nozzleList.size();
			//channel += (96-nozzleList.get(h).ledsTotal)*3;
			//channel +=2048;
			
			//channel += 2048;

			//udp.send(data, ip, port);
			
			DatagramPacket dp = new DatagramPacket(data, data.length);
            dp.setPort(port);
            dest = InetAddress.getByName("224.1.1.1");
            dp.setAddress(dest);

            try {
                    socket.send(dp);
            } catch (IOException e) {
            }
			
			for(int k=0; k<data.length; k++){
				System.out.println("DATA: "+i+" "+k+" "+data[k]);
			}

		}
		
		//}
		
		/*for(int i=1482-(270*2); i>0; i--) {
			payload[payloadIndex++] = 0;
		}*/
		
		
		

		
		}
}
