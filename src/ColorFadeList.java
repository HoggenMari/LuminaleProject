import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;


public class ColorFadeList extends Thread {
	
	ArrayList<ColorFade> colorFadeList = new ArrayList<ColorFade>();
	private PApplet p;
	
	public ColorFadeList(PApplet p) {
		this.p = p;
	}
		
	public void addColorFade(ColorFade cf) {
		colorFadeList.add(cf);
	}
	
	public void start() {
		super.start();
	}

	public void run() {
		while (true) {
		      //System.out.println("PASSED TIME: "+passedTime);
		      draw();
		      try {
		        sleep((long)(0));
		      } catch (Exception e) {
		      }
		    }
	}
	
	public synchronized void draw() {
		int timer = p.millis();
		//System.out.println("TIMER IN: "+timer);
		for(ColorFade cf : colorFadeList) {
			int saturationPassedTime = timer - cf.saturationSavedTime;
			int brightnessPassedTime = timer - cf.brightnessSavedTime;
			int huePassedTime = timer - cf.hueSavedTime;
			/*if(cf.activeSaturation) {
			if (saturationPassedTime >= cf.saturationTotalTime) {
				System.out.println("SAURATIONTIME "+p.millis());
				p.colorMode(PConstants.HSB, 360, 100, 100);
			    cf.saturation = cf.saturation + cf.saturationAdd;
			    //p.background(hue, saturation, brightness);
				cf.saturationSavedTime = p.millis(); // Save the current time to restart the timer!
			if(cf.saturation == cf.saturationEnd) {
				cf.saturationEnd = cf.saturationStart;
				cf.saturationStart = cf.saturation;
				if(cf.saturationEnd < cf.saturation) {
					cf.saturationAdd = -1;
				}else{ 
					cf.saturationAdd = 1;
				}
			}
			}
			}*/
			if(cf.activeBrightness) {
			if (brightnessPassedTime >= cf.brightnessTotalTime) {
				//System.out.println("ACTIVEBRIGHTNESS: "+cf+" "+p.millis()+" "+cf.brightness);
				p.colorMode(PConstants.HSB, 360, 100, 100);
			    cf.brightness = cf.brightness + cf.brightnessAdd;
			    //p.background(hue, saturation, brightness);
				cf.brightnessSavedTime = timer; // Save the current time to restart the timer!
			}
			if(cf.brightness == cf.brightnessEnd) {
				cf.brightnessEnd = cf.brightnessStart;
				cf.brightnessStart = cf.brightness;
				if(cf.brightnessEnd < cf.brightness) {
					cf.brightnessAdd = -1;
				}else{ 
					cf.brightnessAdd = 1;
				}
			}
			}
			/*if(cf.activeHue) {
				if (huePassedTime >= cf.hueTotalTime) {
					System.out.println("ACTIVEHUE: "+p.millis());
					p.colorMode(PConstants.HSB, 360, 100, 100);
				    cf.hue = cf.hue + cf.hueAdd;
				    //p.background(hue, saturation, brightness);
					cf.hueSavedTime = p.millis(); // Save the current time to restart the timer!
				}
				if(cf.hue == cf.hueEnd) {
					cf.hueEnd = cf.hueStart;
					cf.hueStart = cf.hue;
					if(cf.hueEnd < cf.hue) {
						cf.hueAdd = -1;
					}else{ 
						cf.hueAdd = 1;
					}
			}
			}*/
		}
		//System.out.println("FERTIG: "+p.millis());
		}
}
