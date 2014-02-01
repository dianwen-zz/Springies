package links;

public final class SineWave {
	private double maxAmplitude; 
	private double amplitude; 
	private double frequency;
	private double period; 
	private double oscillations; 
	private double angle;
	
	public SineWave(double maxAmplitude, double frequency, double oscillations){
		angle = 0; 
		this.maxAmplitude = maxAmplitude; 
		this.frequency = frequency; 
		this.period = 1/frequency; 
		this.oscillations = oscillations; 
		
		for(int i=0; i<oscillations; i++){
			oscillate();
			angle = 0; 
		}
	}
	
	public void oscillate(){
		while(angle<=90){
			amplitude = Math.sin(angle) * maxAmplitude;
			angle++;
		}
	}
	
	public double phaseInterpreter(double phaseAngle){
		return maxAmplitude*Math.sin(angle - phaseAngle);
	}
	
}
