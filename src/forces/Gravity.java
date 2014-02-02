package forces;

import org.jbox2d.common.Vec2;

public class Gravity extends Force{
	float accel;
	
	public Gravity(float a){
		accel = a;
	}

	public Vec2 calculateForce(float mass) {
		return new Vec2(0,mass*accel);
	}

	@Override
	Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
