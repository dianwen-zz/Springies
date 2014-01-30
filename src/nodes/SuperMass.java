package nodes;

import jboxGlue.PhysicalObjectCircle;
import jgame.JGColor;

public class SuperMass extends PhysicalObjectCircle{

	private float mass; 
	private float x; 
	private float y; 
	private String id; 
	private static final int RADIUS = 3; 

	
	public SuperMass(String id, int COLLISION_ID, JGColor color, float x, float y, float mass) {
		// TODO Auto-generated constructor stub
		
		super(id, COLLISION_ID, color, RADIUS, mass);
		this.x = x;
		this.y = y;
		this.mass = mass; 
		this.setPos(x, y);
		this.setForce(0, 0);
	}

}
