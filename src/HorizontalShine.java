import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class HorizontalShine {
	
	PApplet p;
	private static int LIGHT_DOT_COUNT=40;
	LinkedList<Nozzle> clone = new LinkedList<Nozzle>();
	ArrayList<LightDot> ldList= new ArrayList<LightDot>();
	boolean dead = false;
	int color;
	
	public HorizontalShine(PApplet p, LinkedList<Nozzle> path, int color) {
		this.p = p;
		for(int i=0; i<path.size(); i++){
			clone.add(path.get(i));
		}
		//LinkedList<Nozzle> clone = (LinkedList<Nozzle>) randomPath.clone();
		this.color = color;
		System.out.println("START START START START");
	}

	public void setUpShine() {
		
		//SetUpLightDot
		p.colorMode(PConstants.HSB, 360, 100, 100, 100);
		//color = p.color((int)p.random(0,360), 100, 100);
		
		for(int i=0; i<LIGHT_DOT_COUNT; i++){
			System.out.println(i);			
			ldList.add(new LightDot(0, -5-1*i, 0, 1, color, 255-20*i, clone));
		}
		
		System.out.println("SETUP SETUP SETUP SETUP");

	}
	
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

			System.out.println("DRAW: "+ld.x);
			PGraphics pg = ld.current.sysA;

			if(ld.lifetime-50>0){
				pg.beginDraw();
				pg.noStroke();
				pg.colorMode(PConstants.HSB, 360, 100, 100);
				pg.fill(ld.col,ld.lifetime);
				pg.rect((int)ld.x,(int)ld.y,12,1);
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
