import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class ColorPoint {

	private Nozzle nozzle;
	private PGraphics pg;
	private int x;
	private int y;
	private double vx = 2;
	private double vy = 1.05;
	private int color;
	private PApplet p;
	private int alpha;
	private double velocity_x;
	private double acc_x;
	private double velocity_y;
	private double acc_y;
	private int saturation;
	
	int savedTime;
	int totalTime = 200;
	
	public ColorPoint(PApplet p, int color) {
		x = 0;
		y = 0;
		alpha = (int) p.random(255,255);
		this.color = color;
		this.p = p;
		velocity_x = 1.0;    
		velocity_y = p.random(-1,1);
		saturation=0;
		acc_y = Math.random();
		
		savedTime = p.millis();
	}
	
	public void update(Nozzle nozzle) {
		this.nozzle = nozzle;
		int passedTime = p.millis() - savedTime;
		if (passedTime > totalTime) {
		acc_x = 0.1;
		velocity_x += acc_x;  
		x += velocity_x;
		savedTime = p.millis();
		System.out.println(x+" "+velocity_x+" "+acc_x);
		}
		//x = 0;
	}
	
	public boolean draw(){
		
		
		pg = nozzle.sysA;
		
		//if(velocity_x>1){
			
		//y += velocity_y;
		  
		pg.beginDraw();
		pg.colorMode(PConstants.HSB);
		pg.fill(color, saturation, 255, alpha);
		pg.noStroke();
		pg.rect(x, y, 1, 5);
		pg.fill(color+5, saturation, 255, (int)(0.75*alpha));
		pg.rect(x-1, y, 1, 5);
		pg.fill(color+5, saturation, 255, (int)(0.75*alpha));
		pg.rect(x+1, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.5*alpha));
		pg.rect(x-2, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.5*alpha));
		pg.rect(x+2, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.25*alpha));
		pg.rect(x-3, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.25*alpha));
		pg.rect(x+3, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.125*alpha));
		pg.rect(x-4, y, 1, 5);
		pg.fill(color-5, saturation, 255, (int)(0.125*alpha));
		pg.rect(x+4, y, 1, 5);
		
		pg.endDraw();
		
		//System.out.println(x+" "+velocity_x+" "+acc_x);
		//x=x+1;
		/*if(y<2){
			y++;
		}else y--;*/
		
		if(x>pg.width) {
		System.out.println(x+" "+pg.width);
		return true;
		} else return false;
	  }
	
}
