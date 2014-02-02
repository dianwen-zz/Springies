package forces;
import nodes.SuperMass;
import org.jbox2d.common.Vec2;

public class Spring extends Force{
	private SuperMass massA;
	private SuperMass massB;
	private float constant;
	private float restLength;
	
	public Spring(SuperMass a, SuperMass b, float rl, float c) {
		massA = a;
		massB = b;
		constant = c;
		restLength = rl;
	}

	@Override
	Vec2 calculateForce() {
		// TODO Auto-generated method stub
		return null;
	}

<<<<<<< HEAD:src/links/Spring.java
	@Override
	Vec2 calculateForce(SuperMass m) {
		// TODO Auto-generated method stub
		return null;
	}

	
=======
>>>>>>> 3b85ff0c7c1084f1335faab623e12e0b1d536dea:src/forces/Spring.java
}
