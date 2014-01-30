import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

public class Splash
{
PVector position,speed;
PApplet p;
float colR, colG, colB;
int size;
   
  public Splash(float x,float y, PApplet p)
  {
	this.p = p;
    float angle = p.random(PConstants.PI,PConstants.TWO_PI);
    float distance = p.random(2,4);
    float xx = PApplet.cos(angle)*distance;
    float yy = PApplet.sin(angle)*distance;
    position = new PVector(x,y);
    speed = new PVector(xx,yy);
    colR = 255;
    colG = 255;
    colB = 255; 
    size = 3;
  }
  
  public Splash(float x,float y, PApplet p, int Color)
  {
	this.p = p;
    float angle = p.random(PConstants.PI,PConstants.TWO_PI);
    float distance = p.random(2,4);
    float xx = PApplet.cos(angle)*distance;
    float yy = PApplet.sin(angle)*distance;
    position = new PVector(x,y);
    speed = new PVector(xx,yy);
	colR = p.random(180,255);
	colG = p.random(180, 255);
	colB = p.random(180, 255); 
	size = 10;
  }
  
  public void draw(PGraphics pg)
  {
	pg.beginDraw();  
    pg.strokeWeight(3);
    pg.stroke(100,50);
    pg.fill(colR,colG,colB);
    pg.ellipse(position.x,position.y,size,size);
    pg.endDraw();
    
  }
   
  void calculate()
  {
    gravity();
      
    speed.x*=1.02;
    speed.y*=1.02;
            
    position.add(speed);
  }
   
  void gravity()
  {
    speed.y+=.2;
  }
   
}