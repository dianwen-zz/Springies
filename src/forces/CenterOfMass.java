package forces;

import java.util.ArrayList;

import jgame.JGPoint;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public final class CenterOfMass extends Force{
	
	private float magnitude; 
	private double exponent;
	private JGPoint GlobalCenter; 
	
	public CenterOfMass(float magnitude, double exponent, JGPoint GlobalCenter){
		this.magnitude = magnitude; 
		this.exponent = exponent;
		this.GlobalCenter = GlobalCenter;
	}
		
	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vec2 calculateForce(float mass, double x, double y) {
		// TODO Auto-generated method stub
		System.out.println("GlobalCenter: "+GlobalCenter);
		float X = (float) (GlobalCenter.x-x);
		float Y = (float) (GlobalCenter.y-y);
		
		Vec2 COMForce = new Vec2(X,Y).mul(magnitude);; 
		if(exponent == 2.0){		
			return COMForce.mul(1/(COMForce.length()*COMForce.length()));
		}
		return COMForce.mul(COMForce.length()*COMForce.length());
	}
	
	public void setGlobalCenter(JGPoint point){
		GlobalCenter = point; 
	}
	
	
	


}
