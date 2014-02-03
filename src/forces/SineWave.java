package forces;

import java.util.Timer;
import java.util.TimerTask;

public final class SineWave { 
	private double maxAmplitude;
	protected float amplitude = (float) 0; 
	private double frequency; 
	private double angle;
	private double phaseShift;
	private Timer timer; 
	private Oscillate oscillator; 
	
	public SineWave(double maxAmplitude,  double frequency, double phaseShift){
		angle = 0; 
		this.maxAmplitude = maxAmplitude; 
		this.frequency = frequency;
		this.phaseShift = phaseShift;
		timer = new Timer(true); 
		oscillator = new Oscillate(timer); 
		int firstSart = 1000 ; // it means after 1 second.
        int period = 1000 ; //after which the task will repeat;
        timer.schedule(oscillator, firstSart, period) ;//the time specified in millisecond.   
	}
	
	public SineWave(double amplitude, double phaseShift){
		angle = 0; 
		this.maxAmplitude = amplitude; 
		this.frequency = 1; 	
		timer = new Timer(true); 
		oscillator = new Oscillate(timer); 
		int firstSart = 0 ; // it means after 1 second.
        long period = 1 ; //after which the task will repeat;
        timer.scheduleAtFixedRate(oscillator, firstSart, period) ; 
	}
	
	public float getAmplitude(){
		System.out.println("amplitude "+ amplitude);
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}

	
	public class Oscillate extends TimerTask{
		Timer timer; 
		
		public Oscillate(Timer timer){
			this.timer = timer; 
		}
		@Override
		public void run() {
			amplitude = (float) (Math.sin(frequency*angle + phaseShift) * maxAmplitude);
			angle++; 
		}
		
	}
	
}
