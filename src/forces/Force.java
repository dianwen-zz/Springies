package forces;

import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public abstract class Force {
	abstract Vec2 calculateForce();
}
