package nodes;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;
import org.jbox2d.collision.CircleDef;
import jgame.JGColor;

public class SuperMass extends PhysicalObject{

	private float mass; 
	private float x; 
	private float y; 
	private String id; 
	private static final int RADIUS = 3; 

	
	public SuperMass(String id, int COLLISION_ID, JGColor color, float x, float y, float mass) {
		// TODO Auto-generated constructor stub	
		super(id, COLLISION_ID, color);
		this.x = x;
		this.y = y;
		this.mass = mass;
		
		//init(RADIUS, mass);
		this.setPos(x, y);
		this.setForce(0, 0);
	}


    @Override
    public void paintShape ()
    {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)RADIUS * 2, (float)RADIUS * 2, true, true);
    }




}
