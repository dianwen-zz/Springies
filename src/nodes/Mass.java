package nodes;

import org.jbox2d.common.Vec2;

import forces.Gravity;
import forces.Viscosity;
import jgame.JGColor;
import jgame.JGObject;

public class Mass extends SuperMass {
	
	private static final JGColor MASS_COLOR = JGColor.red;  
	private static final int MASS_COLLISION_ID = 0;
	private Gravity grav;
	private Viscosity visc;
	private float xv, yv; 
	
	public Mass(String id, float x, float y, float mass, float xv, float yv, float g){
		// TODO Auto-generated constructor stub
		super(id, MASS_COLLISION_ID, MASS_COLOR, x, y, mass);
		grav = new Gravity(g);
		visc = new Viscosity((float)0.5);
		this.setSpeed(xv, yv);
		this.xv = xv; 
		this.yv = yv; 
	}
	
	public void calculateObjForce(){
		Vec2 gravForce = grav.calculateForce(mass);
		Vec2 viscForce = visc.calculateForce(this.xv, this.yv);
		this.setForce((double)gravForce.x, (double)gravForce.y);
		this.setForce((double)viscForce.x, (double)viscForce.y);
	}
	
}
