import java.util.LinkedList;

import processing.core.PGraphics;

public class DrawPath {
	
	private LinkedList<Nozzle> path;
	private boolean next;
	private Nozzle nozzle;
	private ColorPoint colorPoint;
	private boolean dead;

	public DrawPath(LinkedList<Nozzle> path, ColorPoint colorPoint) {
		this.path = path;
		this.colorPoint = colorPoint;
		next = true;
		dead = false;
	}
	
	public void update() {
		if(path.size()!=0 && next) {
		   nozzle = path.removeLast();
		   System.out.println(nozzle.id);
		   colorPoint.update(nozzle);
		   next = false;
		} else if (path.size()==0){
			dead = true;
		}
		colorPoint.update(nozzle);
	}
	
	public void draw() {
		if(colorPoint.draw()){
			next = true;
		};
	}
	
	public boolean isDead() {
		return dead;
	}

}
