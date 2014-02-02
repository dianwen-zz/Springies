package forces;

import jboxGlue.PhysicalObject;
import jgame.JGObject;

import org.jbox2d.common.Vec2;

import nodes.Mass;
import nodes.SuperMass;

public class Viscosity extends Force{

	private float scale; 
	
	public Viscosity(float scale) {
		this.scale = scale; 
		// TODO Auto-generated constructor stub
	}

	@Override
	Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

	Vec2 calculateForce(SuperMass m) {
		// TODO Auto-generated method stub
		return new Vec2(-scale*m.getMass(), -scale*m.getMass());
	}


}