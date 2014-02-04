package forces;
import jgame.JGColor;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public class Spring extends Force{
	private SuperMass massA;
	private SuperMass massB;
	private float constant;
	private float restLength;

	public Spring(SuperMass a, SuperMass b, float rl, float c) {
		massA = a;
		massB = b;
		constant = c;
		restLength = rl;
	}

	@Override
	public void calculateForce() {
		Vec2 locA = massA.getPos();
		Vec2 locB = massB.getPos();
		
		double distance = findDistance(locA,locB);
		massA.setForce(constant*(distance-restLength)*(locB.x-locA.x)/distance, constant*(distance-restLength)*(locB.y-locA.y)/distance);
		massB.setForce(-constant*(distance-restLength)*(locB.x-locA.x)/distance, -constant*(distance-restLength)*(locB.y-locA.y)/distance);
	}
	
	@Override
	public void paint(){
		eng.drawLine(massA.getPos().x, massA.getPos().y, massB.getPos().x, massB.getPos().y, 1, JGColor.white);
	}

	public double findDistance(Vec2 a, Vec2 b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}

}
