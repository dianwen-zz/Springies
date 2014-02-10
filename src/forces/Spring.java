package forces;
import jgame.JGColor;

import physicalObjects.SuperMass;

public class Spring extends Muscle{
	
/**
 * 
 * @param a is mass at one end
 * @param b is the mass at the other end
 * @param rl is the rest length
 * @param c is the spring constant
 * 
 * A spring is just a muscle with 0 amplitude. Although this constructor does not do much, we decided not to make a separate class
 *  that handles the creation of muscles and springs (and simply calls the muscle constructor with an amplitude of 0 to create a
 *  spring) because it's excessive for this one inheritance hierarchy with only 2 objects.
 */
	
	public Spring(SuperMass a, SuperMass b, float rl, float c) {
		super(a, b, rl, c, 0);
		color = JGColor.white;
	}

}
