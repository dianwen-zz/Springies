package links;

public final class SineWave {
	private double maxAmplitude; 
	private double [] amplitude; 
	private double frequency;
	private double period; 
	private double oscilations; 
	
	public SineWave(double maxAmplitude, double frequency, double oscilations){
		this.maxAmplitude = maxAmplitude; 
		this.frequency = frequency; 
		this.period = 1/frequency; 
		this.oscilations = oscilations; 
	}
	
	public double phaseInterpreter(double phaseAngle){
		return Math.sin();
	}
	
}
