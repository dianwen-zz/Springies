package forces;

import jgame.JGColor;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Muscle extends Force {

	private float restLength;
	private float constant;
	private SuperMass massA;
	private SuperMass massB; 
	private float amplitude;
	private float time;
	JGColor color;

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
		System.out.println(amplitude);
	}

	public double findDistance(Vec2 a, Vec2 b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}

	public void increaseAmplitude(){
		amplitude+=30;
	}

	public void decreaseAmplitude(){
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
