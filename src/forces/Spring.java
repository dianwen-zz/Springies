package forces;
import java.awt.Color;

import jgame.JGColor;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Spring extends Force{
	private SuperMass massA;
	private SuperMass massB;
	private double constant;
	private float restLength;

	public Spring(SuperMass a, SuperMass b, float rl, double d) {
		super();
		massA = a;
		massB = b;
		constant = d;
		restLength = rl;
	}

	@Override
	public Vec2 calculateForce() {
		Vec2 locA = massA.getPos();
		Vec2 locB = massB.getPos();
		double distance = findDistance(locA,locB);
		double theta = Math.abs(Math.atan(Math.abs((locB.y-locA.y)/(locB.x-locA.x))));
		massA.setForce(constant*(distance-restLength)*Math.cos(theta), constant*(distance-restLength)*Math.sin(theta));
		massB.setForce(-constant*(distance-restLength)*Math.cos(theta), -constant*(distance-restLength)*Math.sin(theta));
		
		System.out.println(-constant*(distance-restLength)*Math.cos(theta));
		
		drawSpring();
		
		return null;
	}
	
	@Override
	public void paint(){
		eng.drawLine(massA.getPos().x, massA.getPos().y, massB.getPos().x, massB.getPos().y, 1, JGColor.white);
	}
	private void drawSpring(){

		//float[] xBorder = massA.getPos().x
		//eng.drawPolygon();

	}

	public double findDistance(Vec2 a, Vec2 b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}

}
