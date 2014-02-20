import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class VerticalShine {
	
	PApplet p;
	private static int LIGHT_DOT_COUNT=30;
	LinkedList<Nozzle> path = new LinkedList<Nozzle>();
	ArrayList<LightDot> ldList= new ArrayList<LightDot>();
	boolean dead = false;
	int color;
	int speed;
	
	public VerticalShine(PApplet p, LinkedList<Nozzle> path, int color, int speed) {
		this.p = p;
		this.path = path;
		this.color = color;
		this.speed = speed;
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			System.out.println(i);			
			ldList.add(new LightDot((int) p.random(0,0), -5-speed*i, 0, speed, color, 255-8*i, path));
		}
	}

	/*public void setUpShine() {
		
		//SetUpLightDot
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		//color = p.color((int)p.random(0,360), 100, 100);
		
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			System.out.println(i);			
			ldList.add(new LightDot(0, -5-1*i, 0, 1, color, 255-20*i, path));
		}
		
		System.out.println("SETUP SETUP SETUP SETUP");

	}*/
	
	public void updateShine() {
		System.out.println("UPDATE UPDATE UPDATE UPDATE");
		if(p.frameCount%1==0){
		for(Iterator<LightDot> ldIterator = ldList.iterator(); ldIterator.hasNext();){
		LightDot ld = ldIterator.next();

			if(ld.y>=ld.current.sysA.height){
				//System.out.println("Bla"+ld.clone.toString());
				//ld.current = ld.clone.removeLast();
				ld.y = 0;
				ld.next();
			}else{
				ld.x += ld.vx;
				ld.y += ld.vy;	
			}
			
			if(ld.clone.size()==0 && ld.y>=ld.current.sysA.height){
				ldIterator.remove();
			}
			
			//System.out.println("Position X:"+ld.x);	
		}
		
		if(ldList.size()==0){
			dead = true;
			System.out.println("DEAD DEAD DEAD DEAD");
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
				pg.fill(ld.col,ld.lifetime);
				pg.rect((int)ld.x,(int)ld.y,12,speed);
				pg.stroke(0, 0, 100);
				pg.strokeWeight(1);
				if(ld.hashCode()%10==0) {
				pg.point(p.random(0, pg.width), p.random(0, pg.height));
				}
				pg.endDraw();
				//ld.lifetime -= 0.5;
			}else{
			}
				
		}
	}
	
	public boolean isDead() {
		return dead;
	}
}
