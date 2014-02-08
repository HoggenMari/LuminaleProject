import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class hsvGradient {

	private Nozzle nozzle;
	private PApplet p;
	private int hue;

	public hsvGradient(PApplet p, Nozzle nozzle, int hue) {
		this.p = p;
		this.nozzle = nozzle;
		this.hue = hue;
	}
	
	public void draw() {
		PGraphics pg = nozzle.sysA;
		pg.beginDraw();
		pg.colorMode(PConstants.HSB, 360, 100, 200);
		for(int iy=0; iy<pg.height; iy++){
			pg.fill(0, 80-iy*20, hue);
			pg.noStroke();
			pg.rect(0, iy, pg.width, 1);
		}
		pg.endDraw();
	}
}
