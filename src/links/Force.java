package links;

import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public abstract class Force {
	abstract Vec2 calculateForce();
	abstract Vec2 calculateForce(SuperMass m);
}
