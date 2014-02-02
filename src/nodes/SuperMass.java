package nodes;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;

import org.jbox2d.collision.CircleDef;

import forces.Gravity;
import jgame.JGColor;

public class SuperMass extends PhysicalObject{

	protected float mass; 
	private float x; 
	private float y; 
	private static final int RADIUS = 3;
	
	public SuperMass(String id, int COLLISION_ID, JGColor color, float x, float y, float m) {
		// TODO Auto-generated constructor stub	
		super(id, COLLISION_ID, color);
		this.x = x;
		this.y = y;
		mass = m;
		
		init(x,y,m,0);
	}
	
	void init(float x, float y, float m, float g){
		CircleDef shape = new CircleDef();
		shape.radius = (float) RADIUS;
		shape.density = (float) m;
		
		createBody(shape);
        setBBox(-(int)RADIUS, -(int)RADIUS, 2 * (int)RADIUS, 2 * (int)RADIUS);
		
		this.setPos(x,y);
		this.setForce(0, 0);
	}
	
	


    @Override
    public void paintShape ()
    {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)RADIUS * 2, (float)RADIUS * 2, true, true);
    }




}
