package forces;

import jgame.JGObject;

public abstract class Force extends JGObject{
	protected boolean isOn = true;
	protected int bitMask;
	
	public Force() {
		super("force", true, 0, 0, 0, null);
		// TODO Auto-generated constructor stub
	}

	public abstract void calculateForce();
	
	public void toggleForces(int toggle){ //toggling behavior is the same for all forces except WallRepulsion (all binary, except WallRepulsion has 4 walls to toggle)
		isOn = ((toggle&bitMask) == bitMask) ? true:false;
	}
	
}
