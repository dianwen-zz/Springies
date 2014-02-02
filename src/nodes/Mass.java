package nodes;

import org.jbox2d.common.Vec2;

import forces.Gravity;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends SuperMass {
	
	private static final JGColor MASS_COLOR = JGColor.red;  
	private static final int MASS_COLLISION_ID = 0;
	private Gravity grav;

	
	public Mass(String id, float x, float y, float mass, float g) {
		// TODO Auto-generated constructor stub
		super(id, MASS_COLLISION_ID, MASS_COLOR, x, y, mass);
		grav = new Gravity(g);
	}
	
	public void calculateObjForce(){
		Vec2 gravForce = grav.calculateForce(mass);
		this.setForce((double)gravForce.x, (double)gravForce.y);
		
	}
	
}
