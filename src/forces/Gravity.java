package forces;

import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public class Gravity extends Force{
	float accel;
	
	public Gravity(float a){
		super();
		accel = a;
	}

	public Vec2 calculateForce(float mass) {
		return new Vec2(0,mass*accel);
	}

	@Override
	public Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
