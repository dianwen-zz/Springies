package forces;

import jgame.JGColor;

import org.jbox2d.common.Vec2;

import physicalObjects.SuperMass;

public class Muscle extends Force {

	private float restLength;
	private float constant;
	private SuperMass massA;
	private SuperMass massB; 
	private float amplitude;
	private float time;
	JGColor color;
	
	/**
	 * @param a is mass at one end
	 * @param b is the mass at the other end
	 * @param rl is the rest length
	 * @param c is the spring constant
	 * @param amp is the amplitude of the sinusoid
	 */

	public Muscle(SuperMass a, SuperMass b, float rl, float c, float amp) {
		massA = a;
		massB = b;
		restLength = rl;
		constant = c;	 
		amplitude = amp;
		color = JGColor.pink;
	}

	@Override
	public void calculateForce() {
		time+=0.15;
		float newRestLength = (float) (restLength + amplitude*Math.sin(time));

		Vec2 locA = massA.getPos();
		Vec2 locB = massB.getPos();
		double distance = findDistance(locA,locB);

		massA.setForce(constant*(distance-newRestLength)*(locB.x-locA.x)/distance, constant*(distance-newRestLength)*(locB.y-locA.y)/distance);
		massB.setForce(-constant*(distance-newRestLength)*(locB.x-locA.x)/distance, -constant*(distance-newRestLength)*(locB.y-locA.y)/distance);
	}

	private double findDistance(Vec2 a, Vec2 b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}

	private void increaseAmplitude(){
		amplitude+=30;
	}

	private void decreaseAmplitude(){
		if(amplitude>=0)
			amplitude-=30;
	}

	@Override
	public void paint(){
		eng.drawLine(massA.getPos().x, massA.getPos().y, massB.getPos().x, massB.getPos().y, 1, color);
	}

	@Override
	public void toggleForces(int toggle) {
		if((toggle&128)==128)
			decreaseAmplitude();
		if((toggle&256)==256)
			increaseAmplitude();
	}
}
