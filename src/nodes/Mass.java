package nodes;

import org.jbox2d.common.Vec2;

import jgame.JGColor;
import jgame.JGObject;

public class Mass extends SuperMass {

	private static final JGColor MASS_COLOR = JGColor.red;  
	private static final int MASS_COLLISION_ID = 1;

	public Mass(String id, float x, float y, float m, float xv, float yv){
		// TODO Auto-generated constructor stub
		super(id, MASS_COLLISION_ID, MASS_COLOR, x, y, m);
		mass = m;
		myBody.setLinearVelocity(new Vec2(xv,yv));
	}
	
	 @Override
     public void hit (JGObject other)
     {
		 if(!(other instanceof Mass)){
         // we hit something! bounce off it!
         Vec2 velocity = myBody.getLinearVelocity();
         // is it a tall wall?
         final double DAMPING_FACTOR = 0.5;
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
     }
}
