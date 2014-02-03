package forces;

import jgame.JGColor;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Muscle extends Force {

	private static final int MASS_COLLISION_ID = 3;
	private float restLength;
	private float constant;
	private SuperMass massA;
	private SuperMass massB; 
	private float amplitude;
	private int time;
	private float startTime;
	
	public Muscle(SuperMass a, SuperMass b, float rl, float c, float amp) {
		massA = a;
		massB = b;
		restLength = rl;
		constant = c;	 
		amplitude = amp;
		startTime = System.nanoTime();
	}
	@Override
	public Vec2 calculateForce() {
		time += time;
		Vec2 locA = massA.getPos();
		Vec2 locB = massB.getPos();
		float distance = (float) findDistance(locA,locB);
		float deltaRestLength = (float) ((float) (distance-restLength)+ amplitude*Math.sin((System.nanoTime()-startTime)));
		massA.setForce(constant*(deltaRestLength)*(locB.x-locA.x)/distance, constant*(deltaRestLength)*(locB.y-locA.y)/distance);
		massB.setForce(-constant*(deltaRestLength)*(locB.x-locA.x)/distance, -constant*(deltaRestLength)*(locB.y-locA.y)/distance);
				
		return null;
	}
	
	@Override
	public void paint(){
		eng.drawLine(massA.getPos().x, massA.getPos().y, massB.getPos().x, massB.getPos().y, 1, JGColor.white);
	}

	public float findDistance(Vec2 a, Vec2 b){
		return (float) Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
}
