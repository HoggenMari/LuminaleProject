import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class PointParticle {

	private PApplet p;
	private int colorR;
	private int colorG;
	private int colorB;
	private int alpha;
	private PVector pV;
	private PVector vV;
	private boolean dead;
	int ttl = 120;

	public PointParticle(PApplet p, float x, float y, float vx, float vy,
			float alpha) {
		this.p = p;
		// this.x = x;
		// this.y = y;
		this.pV = new PVector(x, y);
		this.vV = new PVector(vx, vy);
		//colorR = 180;
		//colorG = 180;
		//colorB = 10;
		this.alpha = 255;
		dead = false;
	}

	public void update() {
		/*if (colorR > 0)
			colorR += 2;
		if (colorG > 0)
			colorG -= 1;
		if (colorB > 0)
			colorB -= 2;*/
		if (alpha > 0)
			alpha -= 10;

		if (alpha <= 0) {
			dead = true;
		}
		//pV.add(vV);
		// pV.add(wind);

		//if (vV.x > 0) {
		//	vV.x -= 0.07;
		//}

		//if (vV.x < 0) {
		//	vV.x += 0.07;
		//}
	}

	boolean isDead() {
		if (dead == true) {
			return true;
		} else
			return false;
	}

	public void draw(PGraphics pg) {
		if (pV.y > 0) {
			// System.out.println("DRAW PARTICLE");
			// pV.y += p.random(-10, -15);
			// pV.y *= p.random(0,1);
			pV.x += p.random(-3, 3);
			//pg.noStroke();
			// pg.colorMode(PConstants.RGB);
		    //pg.stroke( p.frameCount % 256, 255, 255);

			pg.fill(p.frameCount % 256, 255, 255, alpha);
			pg.pushMatrix();
			pg.translate(pV.x, pV.y);
			//pg.line(x1, y1, x2, y2)
			pg.ellipse(0, 0, p.random(30,40), p.random(30,40));
			pg.popMatrix();
		}
	}

	public void decAlpha(int dec) {
		this.alpha = alpha - dec;
	}

}
