
import java.io.IOException;

import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.*;

public class ProcessingMain extends PApplet {
	
	/*Luminale
	 * 
	 */
	
	ControlP5 cp5;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "ProcessingMain" });
	  }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Node node1 = new Node(this, new int[]{0,1});
	Node node2 = new Node(this, new int[]{0,1});
	Node node3 = new Node(this, new int[]{0,1});
	Node node4 = new Node(this, new int[]{0,1});
	Node node5 = new Node(this, new int[]{0,1});
	Node node6 = new Node(this, new int[]{0,1});
	Node node7 = new Node(this, new int[]{0,1});

	private PGraphics pg, pg2;


	
	int hue1=255;
	int hue2=255;
	int hue3=255;

	private PImage img;

	//Animation Stuff
	private int xpos=0;
	private int n=0;
	private int num=8;
	private int pixel_pos = -20;
	private SpreadC[] sc=new SpreadC[num];
	private int pixel_posy = -12;


	private float pixel_x=0;

	private final String IP = "224.1.1.1";
	private final byte CONTROLLER_ID_1 = 4;
	private final byte CONTROLLER_ID_2 = 4;
	private Pavillon scp;






	
	public void setup() {
		
		size(1200,800);
		
		
		//ControlP5
		cp5 = new ControlP5(this);

		cp5.addTextfield("IP")
		   .setPosition(10,10)
		   .setSize(50,15)
		   .setFocus(true)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(IP);
		;
		
		cp5.addTextfield("ID-1")
		   .setPosition(70,10)
		   .setSize(20,15)
		   .setFocus(true)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID_1));
		;
		
		cp5.addTextfield("ID-2")
		   .setPosition(100,10)
		   .setSize(20,15)
		   .setFocus(true)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID_2));
		;
		
		cp5.addButton("OK")
		   .setValue(0)
		   .setPosition(130,10)
		   .setSize(20,15)
		;
		
		
		for (int i=0; i<num; i++) {
			    sc[i]=new SpreadC(this,3, 3, random(-1, 1), random(-1, 1), random(1, 1), 
			    random(255), random(255), random(255));
		}
		
		frameRate(20);
		

		pg = createGraphics(12, 5);
		pg2 = createGraphics(12, 5);

		
		int node1_leds[] = {90,60,75,60,60,75,60,75};
		int node2_leds[] = {75,75,75,75,60,60,75,75,60,90};
		int node3_leds[] = {75,75,75,60,90,75,75,60,75,75,75};
		int node4_leds[] = {60,75,60,75,60,75,75,60};
		int node5_leds[] = {60,90,75,60,75,75,75,90,75,75,60};
		int node6_leds[] = {75,75,45,90,60,60,90,75,60};
		int node7_leds[] = {75,75,75,75,60,60,60,60,90};


		node1.add(node1_leds);
		node2.add(node2_leds);
		node3.add(node3_leds);
		node4.add(node4_leds);
		node5.add(node5_leds);
		node6.add(node6_leds);
		node7.add(node7_leds);
		
		String ip = "224.1.1.1";
		scp = new Pavillon(ip, 5026, 4);
		scp.add(node1);


		/*m = new Movie(this,"/Users/mariushoggenmuller/Documents/BachelorArbeit/FinalLighterSketch/Videos/Neon Jogginghose.avi");
		m.loop();*/
				
		/*img = loadImage("../Images/bg_small.png");
		img.resize(12, 5);*/
		

	}

	public void draw() {
		  background(255);

		  sc[0].update();
		  sc[0].display(pg2);
		   
		  int durchmesser=5;

		  if (xpos > pg.width + durchmesser) {
		    xpos = -durchmesser;
		  }

 
		  for(int i=0; i<1; i++){  
			
			System.out.println(i);
			pg = node1.nozzleList.get(i).sysA;
		    System.out.println(i);
		   // PImage img1 = pg.get(0, 0, pg.width, pg.height);
		    //image(img1, 0, 0);
		    
			pg.beginDraw();
			pg.fill(0,0,0,40);
			pg.rect(0,0,pg.width,pg.height);
			
			//Background-Gradient
			for(int ix=0; ix<14; ix++){
			pg.colorMode(HSB);
			pg.fill(60+50+2*ix+10*i,200,150,frameCount%255-120);
			pg.noStroke();
			pg.rect(ix, 0, 1, 5);
			}
			pg.colorMode(RGB);
			
			/*pg.fill(frameCount,255,255,0);
			pg.noStroke();
			pg.rect(0, 0, 2, 5);	
			
			pg.fill(0,0,255,250-(frameCount%50)*5);
			pg.noStroke();
			pg.rect(2, 0, 2, 5);
			
			pg.fill(frameCount,255,255,0);
			pg.noStroke();
			pg.rect(4, 0, 2, 5);
			
			pg.fill(0,0,255,250-(frameCount%50)*5);
			pg.noStroke();
			pg.rect(6, 0, 2, 5);
		    
			pg.fill(frameCount,255,255,0);
			pg.noStroke();
			pg.rect(8, 0, 2, 5);
			
			pg.fill(0,0,255,250-(frameCount%50)*5);
			pg.noStroke();
			pg.rect(10, 0, 2, 5);*/
			
		  //New
			for(int ii=0; ii<6; ii++){
				pg.fill(120,220,80,20*ii);
				pg.noStroke();
				pg.rect(pixel_posy+ii, 0, 1, 5);
			}
			for(int ii=0; ii<6; ii++){
				pg.fill(100,220,80,100-20*ii);
				pg.noStroke();
				pg.rect(pixel_posy+ii+6, 0, 1, 5);
			}
			
		    
		    if(frameCount%30==0){
		    pixel_x++;
		    }
		    
		    if(pixel_x>5){
		    	pixel_x=0;
		    }
			
		        
		    pg.endDraw();
		    	    
			pg2 = node1.nozzleList.get(0).sysB;
		    pg2.beginDraw();
			pg2.colorMode(RGB);


		    pg2.fill(255, 255, 255, frameCount&255);
		    pg2.rect(0, 0, pg2.width, pg2.height);
		    
		    pg2.endDraw();
		    
		    
		    
		   }
		  
		  
		xpos = xpos+1; 
		pixel_pos = pixel_pos + 1;
		if(frameCount%2==0){
		pixel_posy = pixel_posy + 1;
		}
			 
		  if(frameCount%30==0){
		  if(n>8){
			  n=0;
		  }else n++;
		  }
		  if (xpos > 20) {
			    xpos = 0;
			  }
		  
		//if(frameCount%40==0){
		  if (pixel_posy > 6) {
			    pixel_posy = -12;
			    //pixel_posy  += 1;
			  }
		//}


			node1.drawOnGui(10, 50);
			node2.drawOnGui(150, 50);
			node3.drawOnGui(300, 50);
			node4.drawOnGui(450, 50);
			node5.drawOnGui(600, 50);
			node6.drawOnGui(750, 50);
			node7.drawOnGui(900, 50);

			scp.send();
			/*try {
				node1.sendNode(CONTROLLER_ID_1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/		

	}

	//Called every time a new frame is available to read (Video-Background)
	/*public void movieEvent(Movie m) {
		m.read();
	}*/



}