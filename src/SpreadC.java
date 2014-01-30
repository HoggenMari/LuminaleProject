import processing.core.PApplet;
import processing.core.PGraphics;

public class SpreadC {
  float X, Y, amX, amY, es, R, G, B;
private PApplet p;
  
  SpreadC(PApplet parent, float nX, float nY, float nAmX, float nAmY, float nEs, 
  float nR, float nG, float nB) {
    X=nX;
    Y=nY;
    amX=nAmX;
    amY=nAmY;
    es=nEs;
    R=nR;
    G=nG;
    B=nB;
    p = parent;
  }

  void update() {
    X=X+amX;
    Y=Y+amY;
    es-=0.1;
    if (es<0) {
      X=p.mouseX;
      Y=p.mouseY;
      amX=p.random(-1, 1);
      amY=p.random(-1, 1);
      es=p.random(1, 50);
      R=p.random(255);
      G=p.random(255);
      B=p.random(255);
    }
  }

  void display(PGraphics pg) {
    pg.beginDraw();
    pg.background(0);
	pg.stroke(255);
    pg.point(X, Y);
    pg.noStroke();
    pg.fill(R, G, B, 100);
    pg.ellipse(X, Y, es, es);
    pg.fill(255, 20);
    pg.text(X, X+10, Y+10);
    pg.text(Y, X+10, Y+20);
    pg.endDraw();
  }
}