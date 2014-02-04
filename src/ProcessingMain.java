
import java.util.List;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.*;

public class ProcessingMain extends PApplet {
	
	/*Luminale
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Network settings
	private final String IP = "224.1.1.1";
	private final int PORT = 5026;
	private final byte CONTROLLER_ID_1 = 4;
	private final byte CONTROLLER_ID_2 = 4;
	
	//Init Nozzles, Node, Sculpture Objects
	private final int NODE1_LEDS[] = {90,60,75,60,60,75,60,75};
	private final int NODE2_LEDS[] = {75,75,75,75,60,60,75,75,60,90};
	private final int NODE3_LEDS[] = {75,75,75,60,90,75,75,60,75,75,75};
	private final int NODE4_LEDS[] = {60,75,60,75,60,75,75,60};
	private final int NODE5_LEDS[] = {60,90,75,60,75,75,75,90,75,75,60};
	private final int NODE6_LEDS[] = {75,75,45,90,60,60,90,75,60};
	private final int NODE7_LEDS[] = {75,75,75,75,60,60,60,60,90};
		
	Node node1, node2, node3, node4, node5, node6, node7;

	
	Pavillon scp;

	//Variables for GUI
	ControlP5 cp5;

	//Animation Stuff
	private int xpos=0;
	private int n=0;
	private int num=8;
	private int pixel_pos = -20;
	private SpreadC[] sc=new SpreadC[num];
	private int pixel_posy = -12;
	private PGraphics pg, pg2;
	private float pixel_x=0;
	int hue1=255;
	int hue2=255;
	int hue3=255;

	private int zähler;

	private boolean next;

	private int fade;
		
	List<Nozzle> path;

	private int color;
	
	//DijkstraPaint dp;

	//Initiate as Application
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "ProcessingMain" });
	  }

	public void setup() {
		
		
		size(1200,800);
		
		//frameRate(10);
		
		//Init GUI with Textfields, Buttons
		cp5 = new ControlP5(this);

		cp5.addTextfield("IP")
		   .setPosition(10,10)
		   .setSize(50,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(IP);
		;
		
		cp5.addTextfield("PORT")
		   .setPosition(70,10)
		   .setSize(30,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(PORT));
		;
		
		cp5.addTextfield("ID-1")
		   .setPosition(110,10)
		   .setSize(20,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID_1));
		;
		
		cp5.addTextfield("ID-2")
		   .setPosition(140,10)
		   .setSize(20,15)
		   .setColor(color(255,255,255))
		   .setColorCaptionLabel(color(0,0,0))
		   .setText(Integer.toString(CONTROLLER_ID_2));
		;
		
		cp5.addButton("OK")
		   .setPosition(170,10)
		   .setSize(20,15)
		;
		
		
		for (int i=0; i<num; i++) {
			    sc[i]=new SpreadC(this,3, 3, random(-1, 1), random(-1, 1), random(1, 1), 
			    random(255), random(255), random(255));
		}
		
		

		pg = createGraphics(12, 5);
		pg2 = createGraphics(12, 5);

		
		node1 = new Node(this);
		node2 = new Node(this);
		node3 = new Node(this);
		node4 = new Node(this);
		node5 = new Node(this);
		node6 = new Node(this);
		node7 = new Node(this);

		node1.add(NODE1_LEDS);
		node2.add(NODE2_LEDS);
		node3.add(NODE3_LEDS);
		node4.add(NODE4_LEDS);
		node5.add(NODE5_LEDS);
		node6.add(NODE6_LEDS);
		node7.add(NODE7_LEDS);
		
		scp = new Pavillon(IP, PORT, CONTROLLER_ID_1);
		scp.add(node1, node2, node3, node4, node5, node6, node7);
		scp.setAdj();
		
		zähler=0;
		next=false;
		fade=1;
		path = scp.computePaths(scp.nozzleList.get(0), scp.nozzleList.get(6));
		
		//dp = new DijkstraPaint();
		//dp.set();
		color = 255;
		
	}

	public void draw() {
		  background(255);

			System.out.println(frameRate);

		  for(Nozzle nozzle : scp.nozzleList) {
			  pg = nozzle.sysA;
			  pg.beginDraw();
			  pg.background(0);
			  pg.endDraw();
		  }
		  //sc[0].update();
		  //sc[0].display(pg2);
		   
		  int durchmesser=5;

		  if (xpos > pg.width + durchmesser) {
		    xpos = -durchmesser;
		  }
		  
		  
		  
		  
		  //List<Nozzle> path = scp.computePaths(scp.nozzleList.get((int)random(0,7)), scp.nozzleList.get((int)random(0,7)));
		  
		  System.out.println(path);
		  
		  PGraphics pg5 = createGraphics(260,50,JAVA2D);
		  
		  
		  pg5 = path.get(zähler).sysA;
		  pg5.beginDraw();
		  //pg5.background(100);
		  pg5.noStroke();
		  pg5.colorMode(HSB);
		  pg5.fill(color,100,100,fade);
		  pg5.rect(0, 0, pg5.width, pg5.height);
		  pg5.endDraw();
		  image(pg5, 9, 30); 
		  
		  if(frameCount%4==0){
		  if(fade<255){
			  //next = true;
			  fade = (int) (fade + fade*1.04);
		  } else next = true;
		  }
		  
		  if(next){
			  zähler++;
			  fade = 1;
			  next = !next;
		  }
		  if(zähler>=path.size()){
			  System.out.println("GO HERE 1");
			  zähler=0;
			  path.clear();
			  System.out.println(path.size());
			  path = scp.computePaths(scp.nozzleList.get((int)random(0,17)), scp.nozzleList.get((int)random(0,17)));
			  while(path.size()<2){
				System.out.println("GO HERE 1");
				path.clear();
				path = scp.computePaths(scp.nozzleList.get((int)random(0,17)), scp.nozzleList.get((int)random(0,17)));
			  };
			  color=(int)random(0,255);
		  }
		  System.out.println(zähler);
		  System.out.println(path.size());
			  /*pg5.beginDraw();
			  pg5.background(100);
			  pg5.fill(255,0,255);
			  pg5.rect(0, 0, pg5.width, pg5.height);
			  pg5.endDraw();
			  image(pg5, 9, 30); 
			  image(pg5, 51, 30);*/
			  
			  //PImage img1 = pg5.get(0, 0, pg5.width, pg5.height);
			  //image(img1,650,650);
			  
		  node1.drawOnGui(10, 50);
		  node2.drawOnGui(150, 50);
		  node3.drawOnGui(300, 50);
		  node4.drawOnGui(450, 50);
		  node5.drawOnGui(600, 50);
		  node6.drawOnGui(750, 50);
		  node7.drawOnGui(900, 50);
		  
		  scp.send();
		  
		  
		  /*int i=0;
		  for(Nozzle nozzle : scp.nozzleList){  
			
			//System.out.println(i);
			pg = nozzle.sysA;
		    //System.out.println(i);
		   // PImage img1 = pg.get(0, 0, pg.width, pg.height);
		    //image(img1, 0, 0);
		    
			pg.beginDraw();
			pg.fill(0,0,0,40);
			pg.rect(0,0,pg.width,pg.height);
			
			//Background-Gradient
			for(int ix=0; ix<14; ix++){
			pg.colorMode(HSB);
			pg.fill(60+50+2*ix+i*+frameCount%56,200,150,frameCount%255-0);
			pg.noStroke();
			pg.rect(ix, 0, 1, 5);
			}
			pg.colorMode(RGB);*/
			
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
			/*for(int ii=0; ii<6; ii++){
				pg.fill(120,220,80,20*ii);
				pg.noStroke();
				pg.rect(pixel_posy+ii, 0, 1, 5);
			}
			for(int ii=0; ii<6; ii++){
				pg.fill(100,220,80,100-20*ii);
				pg.noStroke();
				pg.rect(pixel_posy+ii+6, 0, 1, 5);
			}*/
			
		    
		    /*if(frameCount%30==0){
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
		    
			//dp.paint();

		    i++;
		    
		    
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
			scp.setAdj();*/
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
	
	@SuppressWarnings("deprecation")
	public void controlEvent(ControlEvent theEvent) {
		  println(theEvent.controller().name());
		  
		}

		// function buttonA will receive changes from 
		// controller with name buttonA
		public void OK(int theValue) {
		  println("a button event from buttonA: "+theValue);
		  scp.setIP_ADRESS(cp5.get(Textfield.class,"IP").getText());
		  scp.setPORT(Integer.parseInt(cp5.get(Textfield.class,"PORT").getText()));
		  scp.setID(Integer.parseInt(cp5.get(Textfield.class,"ID-1").getText()));
		}


}