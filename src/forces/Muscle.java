package forces;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Muscle extends Force {

	private static final int MASS_COLLISION_ID = 3;
	private float restLength;
	private float constant;
	private SineWave wave;
	private SuperMass massA;
	private SuperMass massB;
	
	public Muscle(SuperMass a, SuperMass b, float rl, float c, float amp) {
		massA = a;
		massB = b;
		restLength = rl;
		constant = c;
		wave = new SineWave(amp);
	}

	@Override
	Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

}
