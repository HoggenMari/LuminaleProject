import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class HorizontalShine {
	
	PApplet p;
	private static int LIGHT_DOT_COUNT=80;
	LinkedList<Nozzle> path = new LinkedList<Nozzle>();
	ArrayList<LightDot> ldList= new ArrayList<LightDot>();
	boolean dead = false;
	int color;
	int speed;
	ColorFade colorFade1;
	
	public HorizontalShine(PApplet p, LinkedList<Nozzle> path, int color, int speed) {
		this.p = p;
		this.path = path;
		this.color = color;
		this.speed = speed;
		
		int rhue = (int) p.random(0,360);
		colorFade1 = new ColorFade(p, rhue, 100, 100);
		colorFade1.hueFade(rhue - 100, 10000);
		colorFade1.start();
		
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			//System.out.println(i);			
			ldList.add(new LightDot(-5-speed*i, 0, speed, 0, color, 255-5*i, path));
		}
	}

	public void setUpShine() {
		
		//SetUpLightDot
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		//color = p.color((int)p.random(0,360), 100, 100);
		
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			//System.out.println(i);			
			ldList.add(new LightDot(-5-speed*i, 0, speed, 0, color, 255-5*i, path));
		}
	}
	
	public void updateShine() {
		if(p.frameCount%1==0){
		for(Iterator<LightDot> ldIterator = ldList.iterator(); ldIterator.hasNext();){
		LightDot ld = ldIterator.next();

			if(ld.x>=ld.current.sysA.width){
				//System.out.println("Bla"+ld.clone.toString());
				//ld.current = ld.clone.removeLast();
				ld.x = 0;
				ld.next();
			}else{
				ld.x += ld.vx;
				ld.y += ld.vy;	
			}
			
			if(ld.clone.size()==0 && ld.x>=ld.current.sysA.width){
				ldIterator.remove();
			}
			
			//System.out.println("Position X:"+ld.x);	
		}
		
		if(ldList.size()==0){
			dead = true;
		}
	  }
	}
	
	public void drawShine() {
		for(LightDot ld : ldList){		

			//System.out.println("DRAW: "+ld.x);
			PGraphics pg = ld.current.sysA;

			if(ld.lifetime-50>0){
				pg.beginDraw();
				pg.noStroke();
				pg.colorMode(PConstants.HSB, 360, 100, 100);
				System.out.println("ColorFade: "+colorFade1.hue);
				pg.fill(colorFade1.hue, colorFade1.saturation, colorFade1.brightness,ld.lifetime);
				System.out.println(ld.lifetime);
				pg.rect((int)ld.x,(int)ld.y,speed,5);
				pg.stroke(colorFade1.hue, 0, 100);
				pg.strokeWeight(1);
				pg.point(p.random(ld.x-20, ld.x), p.random(0, pg.height));
				pg.endDraw();
				//ld.lifetime -= 1;
			}else{
			}
				
		}
	}
	
	public boolean isDead() {
		if(dead) {
			colorFade1.stop();
		}
		return dead;
	}
}
