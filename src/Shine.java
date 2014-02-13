import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Shine {
	
	PApplet p;
	private static int LIGHT_DOT_COUNT=80;
	LinkedList<Nozzle> path = new LinkedList<Nozzle>();
	ArrayList<LightDot> ldList= new ArrayList<LightDot>();
	boolean dead = false;
	int color;
	
	public Shine(PApplet p, LinkedList<Nozzle> path, int color) {
		this.p = p;
		this.path = path;
		this.color = color;
	}

	public void setUpShine() {
		
		//SetUpLightDot
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		//color = p.color((int)p.random(0,360), 100, 100);
		
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			System.out.println(i);			
			ldList.add(new LightDot(-5-2*i, 0, 2, 0, color, 255-5*i, path));
		}
	}
	
	public void updateShine() {
		if(p.frameCount%4==0){
		for(Iterator<LightDot> ldIterator = ldList.iterator(); ldIterator.hasNext();){
		LightDot ld = ldIterator.next();

			if(ld.x>=ld.current.sysA.width-1){
				//System.out.println("Bla"+ld.clone.toString());
				//ld.current = ld.clone.removeLast();
				ld.x = 0;
				ld.next();
			}else{
				ld.x += ld.vx;
				ld.y += ld.vy;	
			}
			
			if(ld.clone.size()==0 && ld.x>=ld.current.sysA.width-1){
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
				pg.fill(ld.col,ld.lifetime);
				pg.rect((int)ld.x,(int)ld.y,2,5);
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