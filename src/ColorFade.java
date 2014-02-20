import processing.core.PApplet;
import processing.core.PConstants;


public class ColorFade extends Thread {

	PApplet p;
	int hue;
	int saturation;
	int brightness;
	boolean running = false;
	
	int saturationSavedTime;
	int saturationTotalTime;
	int saturationEnd;
	int saturationStart;
	int saturationAdd;
	boolean activeSaturation = false;
	
	int brightnessSavedTime;
	int brightnessTotalTime;
	int brightnessEnd;
	int brightnessStart;
	int brightnessAdd;
	boolean activeBrightness = false;

	int hueSavedTime;
	int hueTotalTime;
	int hueEnd;
	int hueStart;
	int hueAdd;
	boolean activeHue = false;
	
	public ColorFade(PApplet p, int hueStart, int saturationStart, int brightnessStart) {
		this.p = p;
		this.hue = hueStart;
		this.hueStart = hueStart;
		this.saturation = saturationStart;
		this.saturationStart = saturationStart;
		this.brightness = brightnessStart;
		this.brightnessStart = brightnessStart;
		
	}
	
	public void hueFade(int hueEnd, int duration) {
		this.hueEnd = hueEnd;
		int diff = Math.abs(hue-hueEnd);
		if(diff!=0) {
		this.hueTotalTime = duration/diff;
		if(hueEnd < hue) {
			hueAdd = -1;
		}else{ 
			hueAdd = 1;
		}
		//System.out.println(diff+" "+hueTotalTime);	
		activeHue = true;
		}
	}
	
	public void saturationFade(int saturationEnd, int duration) {
		this.saturationEnd = saturationEnd;
		int diff = Math.abs(saturation-saturationEnd);
		if(diff!=0) {
		this.saturationTotalTime = duration/diff;
		if(saturationEnd < saturation) {
			saturationAdd = -1;
		}else{ 
			saturationAdd = 1;
		}
		//System.out.println(diff+" "+saturationTotalTime);	
		activeSaturation = true;
		}
	}
	
	public void brightnessFade(int brightnessEnd, int duration) {
		this.brightnessEnd = brightnessEnd;
		int diff = Math.abs(brightness-brightnessEnd);
		this.brightnessTotalTime = duration/(int)diff;
		if(brightnessEnd < brightness) {
			brightnessAdd = -1;
		}else{ 
			brightnessAdd = 1;
		}
		//System.out.println(diff+" "+brightnessTotalTime);	
		activeBrightness = true;
	}
	
	public void start() {
	    running = true;
	    super.start();
	}
	
	public void run() {
	    while (true) {
	      draw();
	    }
	  }
	
	public void draw() {
		int saturationPassedTime = p.millis() - saturationSavedTime;
		int brightnessPassedTime = p.millis() - brightnessSavedTime;
		int huePassedTime = p.millis() - hueSavedTime;
		if (saturationPassedTime >= saturationTotalTime) {
			//System.out.println(p.millis());
			p.colorMode(PConstants.HSB, 360, 100, 100);
		    saturation = saturation + saturationAdd;
		    //p.background(hue, saturation, brightness);
			saturationSavedTime = p.millis(); // Save the current time to restart the timer!
		if(saturation == saturationEnd) {
			saturationEnd = saturationStart;
			saturationStart = saturation;
			if(saturationEnd < saturation) {
				saturationAdd = -1;
			}else{ 
				saturationAdd = 1;
			}
		}
		}
		if(activeBrightness) {
		if (brightnessPassedTime >= brightnessTotalTime) {
			//System.out.println(p.millis());
			p.colorMode(PConstants.HSB, 360, 100, 100);
		    brightness = brightness + brightnessAdd;
		    //p.background(hue, saturation, brightness);
			brightnessSavedTime = p.millis(); // Save the current time to restart the timer!
		}
		if(brightness == brightnessEnd) {
			brightnessEnd = brightnessStart;
			brightnessStart = brightness;
			if(brightnessEnd < brightness) {
				brightnessAdd = -1;
			}else{ 
				brightnessAdd = 1;
			}
		}
		}
		if(activeHue) {
			if (huePassedTime >= hueTotalTime) {
				//System.out.println(p.millis());
				p.colorMode(PConstants.HSB, 360, 100, 100);
			    hue = hue + hueAdd;
			    //p.background(hue, saturation, brightness);
				hueSavedTime = p.millis(); // Save the current time to restart the timer!
			}
			if(hue == hueEnd) {
				hueEnd = hueStart;
				hueStart = hue;
				if(hueEnd < hue) {
					hueAdd = -1;
				}else{ 
					hueAdd = 1;
				}
		}
		}
	}
}
