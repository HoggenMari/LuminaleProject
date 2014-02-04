/*
 * Created by Marius Hoggenmüller on 04.02.14
 * Copyright (c) 2014 Marius Hoggenmüller. All rights reserved.
 * 
 */


import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
import processing.core.*;

public class ProcessingMain extends PApplet {
	
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
	private PGraphics pg, pg2;

	private int counter1;
	private int counter2;
	
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
		
		scp = new Pavillon(this, IP, PORT, CONTROLLER_ID_1);
		scp.add(node1, node2, node3, node4, node5, node6, node7);
		scp.setAdj();
		
		counter1=0;
		counter2=0;

	}

	public void draw() {
		  background(255);

		  scp.breadthFirstSearch(scp.nozzleList.get(0), scp.nozzleList.get(10));
		  //System.out.println(frameRate);
		  
		  //Animate SystemA
		  pg = scp.nozzleList.get(counter1).sysA;
		  pg.beginDraw();
		  pg.background(0);
		  for(int ix=0; ix<pg.width; ix++){
			pg.colorMode(HSB);	
			pg.fill(60+50+3*ix+counter1,255,255,(frameCount%25)*10);
			pg.noStroke();
			pg.rect(ix, 0, 1, 5);
		  }
		  pg.endDraw();
		  
		  if(frameCount%25==0){
			  counter1++;
		  }
		  if(counter1>=scp.nozzleList.size()){
			  counter1=0;
		  }
		  
		  //Animate SystemB
		  counter2 = (frameCount%80)*5;
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysB;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  if(counter2<200){
			  pg2.background(counter2,0,0);
			  }else pg2.background(200+(200-counter2),0,0);
			  pg2.endDraw();
		  }
		  		  
		  //Draw on GUI  
		  node1.drawOnGui(10, 50);
		  node2.drawOnGui(150, 50);
		  node3.drawOnGui(300, 50);
		  node4.drawOnGui(450, 50);
		  node5.drawOnGui(600, 50);
		  node6.drawOnGui(750, 50);
		  node7.drawOnGui(900, 50);
		  
		  //Send DMX-Data
		  scp.send();

	}

	
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