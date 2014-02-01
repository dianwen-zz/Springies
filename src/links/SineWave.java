package links;

public final class SineWave {
	private double maxAmplitude; 
	private double amplitude; 
	private double oscillations; 
	private double angle;
	
	public SineWave(double maxAmplitude,  double oscillations){
		angle = 0; 
		this.maxAmplitude = maxAmplitude; 
		this.oscillations = oscillations; 	
		runWave(); 
	}
	
	public SineWave(double maxAmplitude){
		angle = 0; 
		this.maxAmplitude = maxAmplitude; 
		this.oscillations = 1; 	
		runWave(); 
	}
	
	public void runWave(){
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
