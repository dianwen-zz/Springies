package forces;

import jboxGlue.PhysicalObject;
import jgame.JGObject;

import org.jbox2d.common.Vec2;

import nodes.Mass;
import nodes.SuperMass;

public class Viscosity extends Force{

	private float dampingFactor; 
	
	public Viscosity(float dampingFactor) {
		this.dampingFactor = dampingFactor; 
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

	public Vec2 calculateForce(float xv, float yv) { //che
		// TODO Auto-generated method stub
		return new Vec2(dampingFactor*xv, dampingFactor*yv);
	}


}