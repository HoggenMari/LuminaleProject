/*
 * Created by Marius Hoggenmüller on 04.02.14
 * Copyright (c) 2014 Marius Hoggenmüller. All rights reserved.
 * 
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Controller;
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

	private int counter3;

	private float y=0;

	private boolean next;

	private LinkedList<Nozzle> path;

	private PGraphics pg_last;

	private int color;

	private ColorPoint cp;

	private DrawPath dp;

	private int color1;

	private int color2;

	private ArrayList<hsvGradient> hsv1 = new ArrayList<hsvGradient>();

	private int startHue;
	
	//DrawPathApplication
	private ArrayList<DrawPath> dpList= new ArrayList<DrawPath>();
	private ArrayList<ColorPoint> cpList= new ArrayList<ColorPoint>();
	int max_path = 1;
	
	//Shine
	private static int SHINE_MAX = 1;
	private static int SHINE_VERT_MAX = 8;
	private ArrayList<HorizontalShine> horizontalShineList = new ArrayList<HorizontalShine>();
	private ArrayList<VerticalShine> verticalShineList = new ArrayList<VerticalShine>();

	private LinkedList<Nozzle> nozzlePath;

	private ColorFade colorFade1, colorFade2, colorFade3, colorFade4, colorFade5;

	int h=0;
	
	//Initiate as Application
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "ProcessingMain" });
	  }

	public void setup() {
		
		
		size(1200,800);
		
		//frameRate(20);
		
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
		
		next=true;
		path = scp.breadthFirstSearch(scp.nozzleList.get(0), scp.nozzleList.get(19));
		
		pg_last = createGraphics(12,5);
		
		cp = new ColorPoint(this, color);
		dp = new DrawPath(path, cp);
		
		
		startHue = 120;

		for(Nozzle n : scp.nozzleList){
			  hsv1.add(new hsvGradient(this, n, startHue-2*n.id, 120, 120));

		}

		//color1 = (int) random(0,360);
		  //color2 = (int) random(0,360);
		  		
		scp.start();
		
		//Horizontal Shine
		/*for(int i=0; i<SHINE_MAX; i++) {
		colorMode(HSB, 360, 100, 100);
		color = color(50, 100, 100);
		nozzlePath = createRandomPath();
		horizontalShineList.add(new HorizontalShine(this, nozzlePath, color,(int)random(1,4)));
		//horizontalShineList.get(i).setUpShine();;
		}*/
		
		//Vertical Shine
		for(int i=0; i<SHINE_MAX; i++) {
		colorMode(HSB, 360, 100, 100);
		color = color(50, 0, 100);
		nozzlePath = createPath(i);
		verticalShineList.add(new VerticalShine(this, nozzlePath, color,1));
		//verticalShineList.get(i).setUpShine();;
		}
		

		colorFade1 = new ColorFade(this, 199, 89, 50);
		colorFade1.hueFade(189, 10000);
		colorFade1.brightnessFade(0, 10000);
		colorFade1.start();
		
		/*colorFade2 = new ColorFade(this, 302, 100, 60);
		colorFade2.brightnessFade(20, 3000);
		colorFade2.start();
		
		colorFade3 = new ColorFade(this, 302, 100, 40);
		colorFade3.saturationFade(60, 4000);
		colorFade3.start();
		
		colorFade4 = new ColorFade(this, 302, 100, 20);
		colorFade4.brightnessFade(60, 3000);
		colorFade4.start();
		
		colorFade5 = new ColorFade(this, 302, 100, 0);
		colorFade5.brightnessFade(70, 3000);
		colorFade5.start();*/
	}

	public void draw() {
		
		  //colorFade.draw();
		  System.out.println(frameRate);
		  //background(colorFade.hue, colorFade.saturation, colorFade.brightness);
		  
		  /*for(Nozzle n : scp.nozzleList) {
			  PGraphics pg = n.sysA;
			  pg.beginDraw();
			  pg.colorMode(HSB, 360, 100, 100);
			  pg.noStroke();
			  pg.fill(colorFade1.hue, colorFade1.saturation, colorFade1.brightness);
			  pg.rect(0, 0, pg.width, 1);
			  pg.fill(colorFade2.hue, colorFade2.saturation, colorFade2.brightness);
			  pg.rect(0, 1, pg.width, 1);
			  pg.fill(colorFade3.hue, colorFade3.saturation, colorFade3.brightness);
			  pg.rect(0, 2, pg.width, 1);
			  pg.fill(colorFade4.hue, colorFade4.saturation, colorFade4.brightness);
			  pg.rect(0, 3, pg.width, 1);
			  pg.fill(colorFade5.hue, colorFade5.saturation, colorFade5.brightness);
			  pg.rect(0, 4, pg.width, 1);
			  pg.endDraw();
		  }*/
		  
		  for(Nozzle n : scp.nozzleList) {
			  PGraphics pg = n.sysA;
			  pg.beginDraw();
			  pg.colorMode(HSB, 360, 100, 100);
			  pg.noStroke();
			  pg.fill(colorFade1.hue, colorFade1.saturation, colorFade1.brightness+n.id);
			  pg.rect(0, 0, pg.width, pg.height);
			  pg.endDraw();
		  }
		  //scp.clearSysA();
		  
		  
		  //Glitzer
		  //if(frameCount%10==0) {
		  /*for(Nozzle n : nozzlePath) {
			  PGraphics pg = n.sysA;
			  pg.beginDraw();
			  pg.colorMode(HSB, 360, 100, 100);
			  pg.stroke(0, 0, 50);
			  pg.strokeWeight(1);
			  pg.point(random(0+h, 1+h), random(0, pg.height));
			  pg.endDraw();
		  }
		  if(h<12) {
		  h=h+1;
		  } else {
		  h = 0;	  
		  }*/
		  //}
		  //scp.setColor(305, 55, 26);
		  
		  /*color1 = 260;
		  color2 = 302;
		  //int startHue = 42;
		  		  
		  for(Nozzle n : scp.nozzleList) {
			  

			  int fr = frameCount%480;
			  if(fr==0){
			  hsv1.get(n.id).setHue(color1-1*n.id);	  
			  }else if(fr<120){
			  hsv1.get(n.id).updateHue(1);
			  //hsv1.get(n.id).updateSaturation(1);
			  //hsv1.get(n.id).updateBrightness(1+0.01*n.id);
			  }else if(fr==120){
			  hsv1.get(n.id).setHue(color2-1*n.id);
		      }else if(fr<240){
			  hsv1.get(n.id).updateHue(1);
			  //hsv1.get(n.id).updateSaturation(-0.2);
			  //hsv1.get(n.id).updateBrightness(-(1+0.1*n.id));
		  	  }else if(fr==240){
		  	  hsv1.get(n.id).setHue(color2-1*n.id);
		  	  }else if(fr<360){
		  	  hsv1.get(n.id).updateHue(-1);
		  	  //hsv1.get(n.id).updateSaturation(0.2);
			  //hsv1.get(n.id).updateBrightness(1+0.1*n.id);	  
		  	  }else if(fr==360){
			  hsv1.get(n.id).setHue(color1-1*n.id);
		  	  }else{
			  hsv1.get(n.id).updateHue(-1);
		  	  //hsv1.get(n.id).updateSaturation(-1);
			  //hsv1.get(n.id).updateBrightness(-(1+0.01*n.id));		  		  
		  	  }
			  
		  //hsvGradient hsv1 = new hsvGradient(this, n, startHue-2*n.id);
		  //hsv1.get(n.id).drawHueGradient();
		  hsv1.get(n.id).drawSaturationGradient();
		  }*/

		  //scp.setColor(302, 75, 50);
		  //scp.clearSysA();
		  
		  //scp.dimm(120);
		  
		  //yellowCold();

		  //updateLightDot();
		  //drawLightDot();
		  
		  /*for(int i=0; i<horizontalShineList.size(); i++) {
			  System.out.println("COUNTER: "+i);
		  
		  horizontalShineList.get(i).updateShine();
		  horizontalShineList.get(i).drawShine();
		  }*/
		  
		  for(Iterator<HorizontalShine> shIterator = horizontalShineList.iterator(); shIterator.hasNext();){
			  HorizontalShine sh = shIterator.next();
			  
			  sh.updateShine();
			  sh.drawShine();
			  
			  if(sh.isDead()){
				  shIterator.remove();
			  }
		  }
		  
		  while(horizontalShineList.size()<SHINE_MAX){
			  nozzlePath = createRandomPath();
			  colorMode(HSB, 360, 100, 100);
			  color = color((int)random(0,20), 100, 100);
			  horizontalShineList.add(new HorizontalShine(this, nozzlePath, color, (int) random(1,1)));  
		  }
		  
		  
		  
		  /*for(Iterator<VerticalShine> shIterator = verticalShineList.iterator(); shIterator.hasNext();){
			  VerticalShine sh = shIterator.next();
			  
			  sh.updateShine();
			  sh.drawShine();
			  
			  if(sh.isDead()){
				  shIterator.remove();
			  }
		  }
		  
		  colorMode(HSB, 360, 100, 100);
		  color = color((int)random(0,360), 100, 100);
			
		  if(verticalShineList.size()==0){
			  for(int i=0; i<SHINE_MAX; i++) {
					//colorMode(HSB, 360, 100, 100);
					//color = color((int)random(0,360), 100, 100);
					nozzlePath = createPath(i);
					verticalShineList.add(new VerticalShine(this, nozzlePath, color,2));
					//verticalShineList.get(i).setUpShine();;
					}
		  }*/
		  /*while(verticalShineList.size()<SHINE_VERT_MAX){
			  nozzlePath = createRandomPath();
			  //nozzlePath = createPath(0);
			  colorMode(HSB, 360, 100, 100);
			  color = color((int)random(280,320), 0, 100);
			  verticalShineList.add(new VerticalShine(this, nozzlePath, color));  
		  }*/
		  
		  
		  
		  
		  /*if(horizontalShineList.size()<SHINE_MAX){
			    
			  for(int i=0; i<SHINE_MAX; i++){
			  	nozzlePath = createPath((int)random(0,5));
			  	horizontalShineList.add(new HorizontalShine(this, nozzlePath, color));
			  	//horizontalShineList.get(i).setUpShine(); 
			  }
		  }*/
		  
		  
		  /*for(int i=0; i<SHINE_MAX; i++) {
			  verticalShineList.get(i).updateShine();
			  verticalShineList.get(i).drawShine();
			  if(verticalShineList.get(i).isDead()){
				    System.out.println("COUNTER: "+i);
				    verticalShineList.remove(i);
				  	nozzlePath = createPath(0,1,2,3,4,5,6,7);
				  	verticalShineList.add(new VerticalShine(this, nozzlePath, color));
				  	verticalShineList.get(i).setUpShine();  
			  }
		  }*/

		  //yellowCold();

		  //color = 0;
		  
		  //scp.clearSysA();
		  
		  //yellowCold();
		  
		  /*dp.update();
		  dp.draw();
		  
		  if(dp.isDead()){
			  System.out.println("GO HERE");
			  path = scp.breadthFirstSearch(scp.nozzleList.get(0), scp.nozzleList.get(19));
			  cp = new ColorPoint(this, color);
			  dp = new DrawPath(path, cp);  
		  }*/
		  
		  //drawPathApplication();
		  
			  /*if(next){
			  //System.out.println(path.size());
			  if(path.size()==0){
				  int r1=0;
				  int r2=0;
				  do{
				  r1 = (int)random(0,7);
				  r2 = (int)random(48,65);
				  }while(r1==r2);
				  System.out.println(r1);
				  System.out.println(r2);
				  path = scp.breadthFirstSearch(scp.nozzleList.get(r1), scp.nozzleList.get(r2));
				  color = 0;
			  }
			  pg = path.removeLast().sysA;
			  next = !next;
		  }
		  
		  pg.beginDraw();
		  pg.colorMode(HSB);
		  pg.fill(color, 255, 255, (frameCount%5)*50);
		  pg.noStroke();
		  pg.rect(0, 0, pg.width, pg.height);
		  pg.endDraw();
		  
		  pg_last.beginDraw();
		  pg_last.colorMode(HSB);
		  pg_last.background(0);
		  pg_last.fill(color, 255, 255, 250-(frameCount%5)*50);
		  //System.out.println(249-frameCount%25*10);
		  pg_last.noStroke();
		  pg_last.rect(0, 0, pg_last.width, pg_last.height);
		  
		  if(frameCount%5==0){
			  next = true;
			  pg_last.background(0);
			  pg_last = pg;
		  }
		  
		  pg_last.endDraw();*/

		  //ArrayList<Nozzle> ng_list = scp.nozzleList.get((int)random(0,65)).getNeighbour();
		  
		  //scp.clearSysA();
		  /*for(Nozzle n : scp.nozzleList){
			  pg = n.sysA;
			  pg.beginDraw();
			  pg.background(0);
			  pg.endDraw();
		  }*/
		  
		  /*for(Nozzle n : ng_list) {
			  pg = n.sysA;
			  pg.beginDraw();
			  pg.colorMode(HSB);
			  pg.fill(color, 255, 255, 255);
			  pg.noStroke();
			  pg.rect(0, 0, pg.width, pg.height);
			  pg.endDraw();
		  }*/
		  //System.out.println(frameRate);
		  
		  /*//for(Nozzle n : scp.nozzleList) {
			  pg = scp.nozzleList.get(counter1).sysA;
			  pg.beginDraw();
			  pg.background(0);
			  pg.colorMode(HSB);
			  for(int i=0; i<pg.height; i++) {
			  pg.fill(42-5*i, 255-(frameCount%25)*10, 255);
			  pg.rect(0, i, pg.width, 1);
			  }
			  pg.endDraw();
		  //}*/
		  
		  //Animate SystemA
		  //pg = scp.nozzleList.get(counter1).sysA;
		  /*pg.beginDraw();
		  //pg.background(0);
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
		  }*/
			  
			//Animate SystemB
		  	  /*counter2 = -400+((frameCount%100)*8);
			  if(counter2<=0){
			  for(Nozzle nozzle : scp.nozzleList){
				  pg2 = nozzle.sysA;
				  pg2.beginDraw();
				  pg2.colorMode(RGB);
				  pg2.background(255);
				  if(counter2<=-50){
					  System.out.println("T1: "+(350-(counter2+400)));
					  for(int ix=0; ix<pg2.height; ix++){
							pg2.colorMode(HSB);	
							pg2.fill(42-5*ix,350-(counter2+400),255,255);
							pg2.noStroke();
							pg2.rect(0, ix, pg.width, 1);
					   }
				  //pg2.background(counter2,0,0);
				  }else {
					  System.out.println("T2: "+(50+counter2));
					  for(int ix=0; ix<pg2.height; ix++){
							pg2.colorMode(HSB);	
							pg2.fill(127-5*ix,50+counter2,255,100);
							pg2.noStroke();
							pg2.rect(0, ix, pg.width, 1);
					   }
				  }
				  pg2.endDraw();
			  }
			  }else{
			  //counter2 = (frameCount%100)*4;
			  for(Nozzle nozzle : scp.nozzleList){
				  pg2 = nozzle.sysA;
				  pg2.beginDraw();
				  pg2.colorMode(RGB);
				  pg2.background(255);
				  if(counter2<50){
					  System.out.println("T3: "+(50-counter2));
					  for(int ix=0; ix<pg2.height; ix++){
							pg2.colorMode(HSB);	
							pg2.fill(127-5*ix,50-counter2,255,100);
							pg2.noStroke();
							pg2.rect(0, ix, pg.width, 1);
					   }
				  //pg2.background(counter2,0,0);
				  }else {
					  System.out.println("T4: "+(-50+counter2));
					  for(int ix=0; ix<pg2.height; ix++){
							pg2.colorMode(HSB);	
							pg2.fill(42-5*ix,-50+counter2,255,255);
							pg2.noStroke();
							pg2.rect(0, ix, pg.width, 1);
					   }
				  }
				  pg2.endDraw();
			  }
			 }*/
			  
				//Animate SystemB
		  	  /*counter2 = -400+((frameCount%100)*8);
		  	  int color1=42;
		  	  int color2=127;
		  	  int dimm=0; //(frameCount%300)/2;
			  if(counter2<=0){
			  for(Nozzle nozzle : scp.nozzleList){
				  pg2 = nozzle.sysA;
				  pg2.beginDraw();
				  pg2.colorMode(RGB);
				  pg2.background(255);
				  if(counter2<=-200){
					  System.out.println("T1: "+(350-(counter2+400)));
					  for(int iy=0; iy<pg2.height; iy++){
							pg2.colorMode(HSB);	
							pg2.fill(color1-5*iy,200-(counter2+400),255,255);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
					   }
				  //pg2.background(counter2,0,0);
				  }else {
					  System.out.println("T2: "+(50+counter2));
					  for(int iy=0; iy<pg2.height; iy++){
							pg2.colorMode(HSB);	
							pg2.fill(color2-5*iy,200+counter2,255,255);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
							pg2.colorMode(RGB);	
							pg2.fill(255,255,255,200-counter2);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
					   }
				  }
				  pg2.colorMode(RGB);
				  pg2.fill(0,0,0,dimm);
				  pg2.noStroke();
				  pg2.rect(0, 0, pg.width, pg.height);
				  pg2.endDraw();
			  }
			  }else{
			  //counter2 = (frameCount%100)*4;
			  for(Nozzle nozzle : scp.nozzleList){
				  pg2 = nozzle.sysA;
				  pg2.beginDraw();
				  pg2.colorMode(RGB);
				  pg2.background(0);
				  if(counter2<200){
					  System.out.println("T3: "+(50-counter2));
					  for(int iy=0; iy<pg2.height; iy++){
							pg2.colorMode(HSB);	
							pg2.fill(color2-5*iy,200-counter2,255,255);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
							pg2.colorMode(RGB);	
							pg2.fill(255,255,255,200-counter2);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
					   }
				  //pg2.background(counter2,0,0);
				  }else {
					  System.out.println("T4: "+(-50+counter2));
					  for(int iy=0; iy<pg2.height; iy++){
							pg2.colorMode(HSB);	
							pg2.fill(color1-5*iy,-200+counter2,255,255);
							pg2.noStroke();
							pg2.rect(0, iy, 2, 1);
							pg2.rect(4, iy, 2, 1);
							pg2.rect(8, iy, 2, 1);
							pg2.rect(12, iy, 2, 1);
					   }
				  }
				  pg2.colorMode(RGB);
				  pg2.fill(0,0,0,dimm);
				  pg2.noStroke();
				  pg2.rect(0, 0, pg.width, pg.height);
				  pg2.endDraw();
			  }
			 }
		     
			  if(counter2<=0){
				  for(Nozzle nozzle : scp.nozzleList){
					  pg2 = nozzle.sysA;
					  pg2.beginDraw();
					  pg2.colorMode(RGB);
					  //pg2.background(0);
					  if(counter2<=-200){
						  System.out.println("T1: "+(350-(counter2+400)));
						  for(int iy=0; iy<pg2.height; iy++){
								pg2.colorMode(HSB);	
								pg2.fill(color2-5*iy,200-(counter2+400),255,255);
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
								pg2.colorMode(RGB);	
								pg2.fill(255,255,255,200-(counter2+400));
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
						   }
					  //pg2.background(counter2,0,0);
					  }else {
						  System.out.println("T2: "+(50+counter2));
						  for(int iy=0; iy<pg2.height; iy++){
								pg2.colorMode(HSB);	
								pg2.fill(color1-5*iy,200+counter2,255,255);
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
						   }
					  }
					  pg2.colorMode(RGB);
					  pg2.fill(0,0,0,dimm);
					  pg2.noStroke();
					  pg2.rect(0, 0, pg.width, pg.height);
					  pg2.endDraw();
				  }
				  }else{
				  //counter2 = (frameCount%100)*4;
				  for(Nozzle nozzle : scp.nozzleList){
					  pg2 = nozzle.sysA;
					  pg2.beginDraw();
					  pg2.colorMode(RGB);
					  //pg2.background(0);
					  if(counter2<200){
						  System.out.println("T3: "+(50-counter2));
						  for(int iy=0; iy<pg2.height; iy++){
								pg2.colorMode(HSB);	
								pg2.fill(color1-5*iy,200-counter2,255,255);
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
						   }
					  //pg2.background(counter2,0,0);
					  }else {
						  System.out.println("T4: "+(-50+counter2));
						  for(int iy=0; iy<pg2.height; iy++){
								pg2.colorMode(HSB);	
								pg2.fill(color2-5*iy,-200+counter2,255,255);
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
								pg2.colorMode(RGB);	
								pg2.fill(255,255,255,counter2-200);
								pg2.noStroke();
								pg2.rect(2, iy, 2, 1);
								pg2.rect(6, iy, 2, 1);
								pg2.rect(10, iy, 2, 1);
								pg2.rect(14, iy, 2, 1);
						   }
					  }
					  pg2.colorMode(RGB);
					  pg2.fill(0,0,0,dimm);
					  pg2.noStroke();
					  pg2.rect(0, 0, pg.width, pg.height);
					  pg2.endDraw();
				  }
				 }*/
			  
		  //Animate SystemB
		  /*counter2 = (frameCount%400);
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysB;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  if(counter2<200){
			  pg2.background(counter2,counter2,counter2);
			  }else pg2.background(200+(200-counter2),200+(200-counter2),200+(200-counter2));
			  pg2.endDraw();
		  }*/
		  		  
		  //Draw on GUI  
		  node1.drawOnGui(10, 50);
		  node2.drawOnGui(150, 50);
		  node3.drawOnGui(300, 50);
		  node4.drawOnGui(450, 50);
		  node5.drawOnGui(600, 50);
		  node6.drawOnGui(750, 50);
		  node7.drawOnGui(900, 50);
		  
		  //Send DMX-Data
		  //scp.send();
		  

	}
	
	public LinkedList<Nozzle> createRandomPath() {
	LinkedList<Nozzle> randomPath = new LinkedList<Nozzle>();
	do{
      int r1=0;
	  int r2=0;
	  do{
	    r1 = (int)random(0,17);
	    r2 = (int)random(57,65);
	    //System.out.println("randomPath.size: "+randomPath.size());
	  }while(r1==r2 | r1>r2);
	  randomPath = scp.breadthFirstSearch(scp.nozzleList.get(r1), scp.nozzleList.get(r2));
	}while(randomPath.size()<5);
	return randomPath;
	}
	
	public LinkedList<Nozzle> createClosedPath() {
		LinkedList<Nozzle> randomPath = new LinkedList<Nozzle>();
		do{
	      int r1=0;
		  int r2=0;
		  do{
		    r1 = (int)random(0,16);
		    r2 = (int)random(0,65);
		    //System.out.println("randomPath.size: "+randomPath.size());
		  }while(r1==r2 | r1>r2);
		  randomPath = scp.breadthFirstSearch(scp.nozzleList.get(r1), scp.nozzleList.get(r2));
		}while(randomPath.size()<5);
		return randomPath;
		}
	
	public LinkedList<Nozzle> createPath(int...i) {
		LinkedList<Nozzle> path = new LinkedList<Nozzle>();
		for(int f : i){
		path.add(scp.nozzleList.get(f));
		}
		return path;
	}

	public void drawPathApplication() {
		if(dpList.size()<max_path){
			int r1=0;
			int r2=0;
			do{
			r1 = (int)random(0,0);
			r2 = (int)random(20,20);
			}while(r1==r2);
			LinkedList<Nozzle> randomPath = scp.breadthFirstSearch(scp.nozzleList.get(r1), scp.nozzleList.get(r2));
			ColorPoint cp = new ColorPoint(this, (int) random(180,240));
			dpList.add(new DrawPath(randomPath, cp));
		}
		
		for(Iterator<DrawPath> dpIterator = dpList.iterator(); dpIterator.hasNext();){
		//for(DrawPath dp : dpList){
			DrawPath dp = dpIterator.next();
			dp.update();
			dp.draw();
			
			if(dp.isDead()){
				  System.out.println("GO HERE");
				  dpIterator.remove();
				  //path = scp.breadthFirstSearch(scp.nozzleList.get(0), scp.nozzleList.get(19));
				  //cp = new ColorPoint(this, color);
				  //dp = new DrawPath(path, cp);  
			}
		}
	}
	
	public void yellowCold(){
		//Animate SystemB
	  	  counter2 = -400+((frameCount%800)*1);
	  	  int color1=120;
	  	  int color2=240;
	  	  int dimm=0; //(frameCount%300)/2;
		  if(counter2<=0){
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysA;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.background(255);
			  if(counter2<=-200){
				  //System.out.println("T1: "+(350-(counter2+400)));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color1-5*iy,200-(counter2+400),255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  //pg2.background(counter2,0,0);
			  }else {
				  //System.out.println("T2: "+(50+counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color2+5*iy,200+counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
						pg2.colorMode(RGB);	
						pg2.fill(255,255,255,200-counter2);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  }
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,dimm);
			  pg2.noStroke();
			  pg2.rect(0, 0, pg.width, pg.height);
			  pg2.endDraw();
			  
			  /*for(int ix=0; ix<pg2.width; ix++){
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,255);
			  pg2.rect(ix,y,1,1);
			  pg2.endDraw();
			  }*/
			  
		  }
		  }else{
		  //counter2 = (frameCount%100)*4;
		  for(Nozzle nozzle : scp.nozzleList){
			  pg2 = nozzle.sysA;
			  pg2.beginDraw();
			  pg2.colorMode(RGB);
			  pg2.background(0);
			  if(counter2<200){
				  //System.out.println("T3: "+(50-counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color2+5*iy,200-counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
						pg2.colorMode(RGB);	
						pg2.fill(255,255,255,200-counter2);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  //pg2.background(counter2,0,0);
			  }else {
				  //System.out.println("T4: "+(-50+counter2));
				  for(int iy=0; iy<pg2.height; iy++){
						pg2.colorMode(HSB);	
						pg2.fill(color1-5*iy,-200+counter2,255,255);
						pg2.noStroke();
						pg2.rect(0, iy, pg.width, 1);
				   }
			  }
			  pg2.colorMode(RGB);
			  pg2.fill(0,0,0,0);
			  pg2.noStroke();
			  pg2.rect(0, 0, pg.width, pg.height);
			  pg2.endDraw();
			  
		  }
		  
		 }
	  scp.dimm(100);
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