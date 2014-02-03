package forces;

import jboxGlue.WorldManager;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World; 

import common.wall;

public class WallRepulsion extends Force{

	public static Vec2 calculateForce(int ID, float magnitude, float exponent, float mass, float lastx, float lasty) {
		// TODO Auto-generated method stub
		Vec2 repulsion; 
		if(ID == 1) //repel from top wall
			repulsion = new Vec2((float) 0.0, -calculateForceHelper(magnitude, exponent,mass));//negative y should repel the object downwards		
		if(ID == 2) //repel from right wall 
			repulsion = new Vec2 (-calculateForceHelper(magnitude, exponent, mass), (float)0.0);				
		if(ID == 3) //repel from bottom wall 
			repulsion = new Vec2((float) calculateForceHelper(magnitude, exponent,mass), (float)0.0);	
		else //repel from left wall
			repulsion = new Vec2 (-calculateForceHelper(magnitude, exponent,mass), (float)0.0);
		return repulsion; 
	}

	static float calculateForceHelper(float magnitude, float exponent, float mass){
		if(exponent == 2){
			return magnitude;  
		}
		return magnitude;
	}
	

	@Override
	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
