package forces;

import java.util.ArrayList;

import jgame.JGPoint;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public final class CenterOfMass extends Force{
	
	private float magnitude; 
	private double exponent;
	private JGPoint GlobalCenter; 
	
	public CenterOfMass(float magnitude, double exponent){
		this.magnitude = magnitude; 
		this.exponent = exponent;
	}
		
	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vec2 calculateForce(float mass, double x, double y) {
		// TODO Auto-generated method stub
		float X = (float) ((GlobalCenter.x-x)*(GlobalCenter.x-x));
		float Y = (float) ((GlobalCenter.y-y)*(GlobalCenter.y-y));
		
		if(exponent == 2.0){
			return new Vec2(magnitude*(1/X)*X, magnitude*(1/Y)*Y);
		}
		return new Vec2(magnitude*X, magnitude*Y);
	}
	
	public void setGlobalCenter(JGPoint point){
		GlobalCenter = point; 
	}
	
	
	


}
