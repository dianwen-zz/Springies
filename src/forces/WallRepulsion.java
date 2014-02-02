package forces;

import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class WallRepulsion extends Force{

	private int ID; 
	private float magnitude;
	private float exponent;
	
	public WallRepulsion(int ID, float magnitude, float exponent){
		this.ID = ID; 
		this.magnitude = magnitude;
		this.exponent = exponent; 
	}
	@Override
	Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

<<<<<<< HEAD:src/links/WallRepulsion.java
	@Override
	Vec2 calculateForce(SuperMass m) {
		// TODO Auto-generated method stub
		Vec2 repulsion; 
		if(ID == 1)
			repulsion = new Vec2((float) 0.0, -calculateForceHelper(magnitude, exponent,m));//negative y should repel the object downwards		
		if(ID == 2)
			repulsion = new Vec2 (-calculateForceHelper(magnitude, exponent,m), (float)0.0);				
		if(ID == 3)
			repulsion = new Vec2((float) 0.0, -calculateForceHelper(magnitude, exponent,m));	
		else
			repulsion = new Vec2 (calculateForceHelper(magnitude, exponent,m), (float)0.0);
		return repulsion; 
	}

	float calculateForceHelper(float magnitude, float exponent, SuperMass m){
		if(exponent == 2){
			return magnitude *  
		}
		return magnitude;
		
	}
	
	float getDistanceFromWall(int ID, SuperMass m){
		Body body = m.getBody(); 
		
		
	}

=======
>>>>>>> 3b85ff0c7c1084f1335faab623e12e0b1d536dea:src/forces/WallRepulsion.java
}
