import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class hsvGradient {

	private static int HUE_RANGE = 360;
	private static int SATURATION_RANGE = 120;
	private static int BRIGHTNESS_RANGE = 120;

	private Nozzle nozzle;
	private PApplet p;
	private double hue;
	private double brightness;
	private double saturation;

	public hsvGradient(PApplet p, Nozzle nozzle, int hue, int saturation, int brightness) {
		this.p = p;
		this.nozzle = nozzle;
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}

	public void setHue(double value){
		this.hue = value;
		if(hue<0){
			hue = hue + 360;
		}
		if(hue>360){
			hue = hue - 360;
		}
		System.out.println(hue);

	}

	public void setSaturation(int value){
		this.saturation = value;
	}

	public void setBrightness(int value){
		this.brightness = value;
	}

	public void updateHue(double factor){
		hue = (hue-factor);
		if(hue<0){
			hue = hue + 360;
		}
		if(hue>360){
			hue = hue - 360;
		}
		System.out.println(hue);
	}

	public void updateBrightness(double factor){
		brightness = brightness-factor;
		//System.out.println(brightness);
	}

	public void updateSaturation(double factor){
		saturation = saturation-factor;
		//System.out.println(saturation);
	}

	public void drawSaturationGradient() {
		PGraphics pg = nozzle.sysA;
		pg.beginDraw();
		pg.colorMode(PConstants.HSB, HUE_RANGE, BRIGHTNESS_RANGE, SATURATION_RANGE);
		for(int iy=0; iy<pg.height; iy++){
			pg.fill((int) hue, 80-iy*20, (int) brightness);
			pg.noStroke();
			pg.rect(0, iy, pg.width, 1);
		}
		pg.endDraw();
	}

	public void drawHueGradient(){
		PGraphics pg = nozzle.sysA;
		pg.beginDraw();
		pg.colorMode(PConstants.HSB, 255, 100, 120);
		for(int iy=0; iy<pg.height; iy++){
			pg.fill((int) (hue-5*iy), (int) saturation, (int) brightness);
			pg.noStroke();
			pg.rect(0, iy, pg.width, 1);
		}
		/*if(p.frameCount%2==0){
			for(int iy=0; iy<pg.height; iy++){
				pg.fill((int) (hue-5*(pg.height-iy)), (int) saturation, (int) brightness);
				pg.noStroke();
				pg.rect(0, iy, pg.width, 1);
			}
		}*/
		pg.endDraw();
	}
}