import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Nozzle {
	

	public static final int TYPE1 = 0;
	public static final int TYPE2 = 1;
	public static final int TYPE3 = 2;
	public static final int TYPE4 = 3;
	
	static int HEIGHT_A = 5;
	static int HEIGHT_B = 1;

	private PApplet p;
	
	public byte data[];

	private int widthA, heightA;
	private int widthB, heightB;

	public PGraphics sysA, sysB;
	public int ledsTotal;
	
	public boolean marked;
	public int id;
	public ArrayList<Nozzle> neighbour= new ArrayList<Nozzle>();


	public Nozzle(PApplet p, int type) {
		this.p = p;
		this.heightA = HEIGHT_A;
		this.heightB = HEIGHT_B;
		if(type==90){
			System.out.println("Type1");
			this.ledsTotal = 90;
			this.widthA = 12;
			this.widthB = 30;
			sysA = p.createGraphics(widthA, heightA, PConstants.JAVA2D);
			sysB = p.createGraphics(widthB, heightB, PConstants.JAVA2D);
		}
		if(type==75){
			System.out.println("Type2");
			this.ledsTotal = 75;
			this.widthA = 10;
			this.widthB = 25;
			sysA = p.createGraphics(widthA, heightA, PConstants.JAVA2D);
			sysB = p.createGraphics(widthB, heightB, PConstants.JAVA2D);
		}
		if(type==60){
			System.out.println("Type3");
			this.ledsTotal = 60;
			this.widthA = 8;
			this.widthB = 20;
			sysA = p.createGraphics(widthA, heightA, PConstants.JAVA2D);
			sysB = p.createGraphics(widthB, heightB, PConstants.JAVA2D);
		}
		if(type==45){
			System.out.println("Type4");
			this.ledsTotal = 45;
			this.widthA = 6;
			this.widthB = 15;
			sysA = p.createGraphics(widthA, heightA, PConstants.JAVA2D);
			sysB = p.createGraphics(widthB, heightB, PConstants.JAVA2D);
		}
		this.data = new byte[((widthA)*(heightA))*3+((widthB)*(heightB)*3)];

	}
	
	void setID(int id) {
		this.id = id;
	}
	
	void update() {
		//System.out.print("Ausgabe: "+data.length);
		
		
		int dataIndex = 0;
		
		for(int ix=0; ix<widthA; ix++) {
			for(int iy=0; iy<heightA; iy++) {	
				int rgb = sysA.get(ix, iy);
				data[dataIndex+2] = (byte) (rgb & 0xff);
				data[dataIndex+1] = (byte) ((rgb >> 8) & 0xff);
				data[dataIndex] = (byte) ((rgb >> 16) & 0xff);
				//System.out.println("i: "+ix+" j:"+iy+" r: "+data[(ix*heightA+iy)+2]+" g:"+data[(ix*heightA+iy)+1]+" b:"+data[(ix*heightA+iy)]);
				dataIndex +=3;
			}
			ix++;
			for (int iy = heightA-1; iy >= 0; iy--) {
				int rgb = sysA.get(ix, iy);	
				data[dataIndex+2] = (byte) (rgb & 0xff);
				data[dataIndex+1] = (byte) ((rgb >> 8) & 0xff);
				data[dataIndex] = (byte) ((rgb >> 16) & 0xff);
				//System.out.println("i: "+ix+" j:"+iy+" r: "+data[(ix*heightA+iy)+2]+" g:"+data[(ix*heightA+iy)+1]+" b:"+data[(ix*heightA+iy)]);
				dataIndex +=3;
			}
		}
		
		for(int ix=0; ix<widthB; ix++) {
			for(int iy=0; iy<heightB; iy++) {	
				int rgb = sysB.get(ix, iy);
				data[dataIndex+2] = (byte) (rgb & 0xff);
				data[dataIndex+1] = (byte) ((rgb >> 8) & 0xff);
				data[dataIndex] = (byte) ((rgb >> 16) & 0xff);
				dataIndex +=3;
			}
		}
		
	}
	
	void drawOnGui(int pos_x, int pos_y) {
		for (int ix = 0; ix < widthA; ix++) {
			for (int iy = 0; iy < heightA; iy = iy + 1) {
				p.stroke(0);
				p.strokeWeight(1);
				int rgb = sysA.get(ix, iy);
				//System.out.println("RGB: "+rgb);
				p.fill(rgb);
				p.rect(ix * 10 + pos_x, iy * 10 + pos_y, 10, 10);
			}
		}
		for (int ix = 0; ix < widthB; ix++) {
			for (int iy = 0; iy < heightB; iy = iy + 1) {
				p.stroke(0);
				p.strokeWeight(1);
				int rgb = sysB.get(ix, iy);
				//System.out.println("RGB: "+rgb);
				p.fill(rgb);
				p.rect(ix * 4 + pos_x, iy * 4 + (heightA * 10 + pos_y), 4, 4);
			}
		}
	}
    
}
