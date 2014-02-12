import java.util.LinkedList;


public class LightDot{
	int x;
	int y;
	int vx;
	int vy;
	
	int col;
	
	int lifetime;
	LinkedList<Nozzle> clone = new LinkedList<Nozzle>();
	Nozzle current;
		
	
	public LightDot(int x, int y, int vx, int vy, int col, int lifetime, LinkedList<Nozzle> randomPath) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.col = col;
		this.lifetime = lifetime;
		for(int i=0; i<randomPath.size(); i++){
			clone.add(randomPath.get(i));
		}
		//LinkedList<Nozzle> clone = (LinkedList<Nozzle>) randomPath.clone();
		current = clone.removeLast();
		//current = clone.removeLast();
		//System.out.println(clone.toString());
	}
	
	public void next(){
		if(clone.size()!=0){
		current = clone.removeLast();
		}
	}
	
	
	
}
