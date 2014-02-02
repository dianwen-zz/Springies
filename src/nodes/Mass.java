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
	
	void calculateObjForce(){
		grav.calculateForce(mass);
	}
	
	public void hit (JGObject other){ //follow similar behavior as a bouncing ball
		//hit something, grab the velocity and dampen it if we hit a wall
		Vec2 velocity = myBody.getLinearVelocity();
		final double DAMPING_FACTOR = 0.8;
		//if it is a wall, we dampen the velocity
		 boolean isSide = other.getBBox().height > other.getBBox().width;
         if (isSide) {
             velocity.x *= -DAMPING_FACTOR;
         }
         else {
             velocity.y *= -DAMPING_FACTOR;
         }
         // apply the change
        myBody.setLinearVelocity(velocity);
	}
	 
	public void setVel(Vec2 v){
		myBody.setLinearVelocity(v);
	}

}
