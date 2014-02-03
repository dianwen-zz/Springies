package nodes;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import common.Environment;
import common.wall;

import springies.Springies;

import forces.CenterOfMass;
import forces.Gravity;
import forces.Viscosity;
import forces.WallRepulsion;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;

public class Mass extends SuperMass {
	
	private static final JGColor MASS_COLOR = JGColor.red;  
	private static final int MASS_COLLISION_ID = 0;
	private float mass; 
	private ArrayList<wall> walls = new ArrayList<wall>();
	private ArrayList<Vec2> wallForces  = new ArrayList<Vec2>(); 
	private Gravity grav;
	private Viscosity visc;
	private CenterOfMass COM;
	private WallRepulsion WR; 
	
	private float xv, yv; 
	
	public Mass(String id, float x, float y, float mass, float xv, float yv, 
			float gravAccel, float viscosityDampingConstant, float centerOfMass_Magnitude,
			float centerOfMass_Exponent, JGPoint GlobalCenter, ArrayList<wall> walls){

		
		super(id, MASS_COLLISION_ID, MASS_COLOR, x, y, mass);
		grav = new Gravity(gravAccel);
		visc = new Viscosity(viscosityDampingConstant);
		COM = new CenterOfMass(centerOfMass_Magnitude, centerOfMass_Magnitude, GlobalCenter);
		myBody.setLinearVelocity(new Vec2(xv,yv));
		this.setSpeed(xv, yv);
		this.xv = xv; 
		this.yv = yv;
		this.walls = walls;
		this.mass = mass; 
	}
	
	public void calculateObjForce(){
		Vec2 gravForce = grav.calculateForce(mass);
		Vec2 viscForce = visc.calculateForce(this.xv, this.yv);
		Vec2 COMForce = COM.calculateForce(this.mass, this.getLastX(), this.getLastY());
		Vec2 wallForce1, wallForce2, wallForce3, wallForce4;
		for(wall w: walls){
			wallForces.add(WallRepulsion.calculateForce(w.getId(), w.getMagnitude(), w.getExponent(),
					mass, (float)this.getLastX(), (float) this.getLastY()));
		}
		walls.clear();
		//this.setForce((double)gravForce.x, (double)gravForce.y);
		///this.setForce((double)viscForce.x, (double)viscForce.y);
		//this.setForce((double)COMForce.x, (double)COMForce.y);
		/*for(Vec2 wallForce: wallForces){
			this.setForce((double)wallForce.x, (double)wallForce.y);
		}
		wallForces.clear();*/
	}
	
	public void setGlobalCenter(JGPoint point){
		COM.setGlobalCenter(point);
	}
}
