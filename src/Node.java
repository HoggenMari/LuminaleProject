import java.util.ArrayList;

import processing.core.PApplet;

public class Node {
	private PApplet p;
	ArrayList<Nozzle> nozzleList = new ArrayList<Nozzle>();


	public Node(PApplet p) {
		this.p = p;
	}
	
	public void add(int[] leds_of_nozzle) {
		for(int i=0; i<leds_of_nozzle.length; i++){
			nozzleList.add(new Nozzle(p,leds_of_nozzle[i]));
		}
	}
	
	public void drawOnGui(int x, int y) {
		for (int i=0; i<nozzleList.size(); i++) {
			nozzleList.get(i).update();
			nozzleList.get(i).drawOnGui(x, y+i*60);
			
		}
	}
}
