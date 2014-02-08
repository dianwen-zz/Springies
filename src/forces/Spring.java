package forces;
import jgame.JGColor;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;

public class Spring extends Muscle{

	public Spring(SuperMass a, SuperMass b, float rl, float c) {
		super(a, b, rl, c, 0);
		color = JGColor.white;
	}


}
