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
	
	public ColorPoint(PApplet p, int color) {
		x = 0;
		y = 0;
		alpha = (int) p.random(255,255);
		this.color = color;
		this.p = p;
	}
	
	public void update(Nozzle nozzle) {
		this.nozzle = nozzle;
		x = 0;
	}
	
	public boolean draw(){
		pg = nozzle.sysA;
		pg.beginDraw();
		pg.colorMode(PConstants.HSB);
		pg.fill(color, 255, 255, alpha);
		pg.noStroke();
		pg.rect(x, y, 1, 5);
		pg.fill(color+5, 255, 255, (int)(0.5*alpha));
		pg.rect(x-1, y, 1, 5);
		pg.fill(color+5, 255, 255, (int)(0.5*alpha));
		pg.rect(x+1, y, 1, 5);
		pg.fill(color-5, 255, 255, (int)(0.25*alpha));
		pg.rect(x-2, y, 1, 5);
		pg.fill(color-5, 255, 255, (int)(0.25*alpha));
		pg.rect(x+2, y, 1, 5);
		pg.endDraw();
		x=x+1;
		/*if(y<2){
			y++;
		}else y--;*/
		if(x>pg.width) {
		return true;
		} else return false;
	}
}
