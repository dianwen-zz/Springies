package nodes;

import jgame.JGColor;

public class Fixed extends SuperMass {

	private static final JGColor FIXED_COLOR = JGColor.blue;  
	private static final int FIXED_COLLISION_ID = 1;
	
	public Fixed(String id, float x, float y, float mass) {
		// TODO Auto-generated constructor stub
		super(id, FIXED_COLLISION_ID, FIXED_COLOR, x, y, mass);
	}

}
