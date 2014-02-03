package nodes;

import org.jbox2d.common.Vec2;

import common.Environment;

import springies.Springies;

import forces.CenterOfMass;
import forces.Gravity;
import forces.Viscosity;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;

public class Mass extends SuperMass {
	
	private static final JGColor MASS_COLOR = JGColor.red;  
	private static final int MASS_COLLISION_ID = 0;
	private Gravity grav;
	private Viscosity visc;
	private CenterOfMass COM; 
	private float xv, yv; 
	
	public Mass(String id, float x, float y, float mass, float xv, float yv, Environment environment){
		// TODO Auto-generated constructor stub
		super(id, MASS_COLLISION_ID, MASS_COLOR, x, y, mass);
		grav = new Gravity(environment.getGravAccel());
		visc = new Viscosity(environment.getViscosityDampingConstant());
		COM = new CenterOfMass(environment.getCenterOfMass_Magnitude(), environment.getCenterOfMass_Magnitude());
		myBody.setLinearVelocity(new Vec2(xv,yv));
		this.setSpeed(xv, yv);
		this.xv = xv; 
		this.yv = yv; 
	}
	
	public void calculateObjForce(){
		Vec2 gravForce = grav.calculateForce(mass);
		Vec2 viscForce = visc.calculateForce(this.xv, this.yv);
		//Vec2 COMForce = COM.calculateForce(this.mass, this.getLastX(), this.getLastY());
		this.setForce((double)gravForce.x, (double)gravForce.y);
		this.setForce((double)viscForce.x, (double)viscForce.y);
		//this.setForce((double)COMForce.x, (double)COMForce.y);
	}
	
	public void setGlobalCenter(JGPoint point){
		COM.setGlobalCenter(point);
	}
}
