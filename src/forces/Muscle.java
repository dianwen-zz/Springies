package forces;

import jgame.JGColor;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Muscle extends Force {

	private static final int MASS_COLLISION_ID = 3;
	private float restLength;
	private float length; 
	private float constant;
	private SineWave wave;
	private SuperMass massA;
	private SuperMass massB; 
	
	public Muscle(SuperMass a, SuperMass b, float rl, float c, float amp, float phaseShift, float maxAmplitude) {
		massA = a;
		massB = b;
		restLength = rl;
		length = restLength;
		constant = c;	 
		wave = new SineWave(maxAmplitude, 30, phaseShift);
	}

	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		Vec2 locA = massA.getPos();
		Vec2 locB = massB.getPos();
		if(length <= restLength)
			length = length*(1+wave.getAmplitude());
		else 
			length = length/2;
		
		System.out.println("length "+length);
		double distance = findDistance(locA,locB);
		massA.setForce(length/distance, length/distance);
		massB.setForce(length/distance, length/distance);
		
		return null;
	}

	@Override
	public void paint(){
		eng.drawLine(massA.getPos().x, massA.getPos().y, massB.getPos().x, massB.getPos().y, 1, JGColor.red);
	}
	
	public double findDistance(Vec2 a, Vec2 b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
}
