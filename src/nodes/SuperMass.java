package nodes;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;

import org.jbox2d.collision.CircleDef;

import forces.Gravity;
import jgame.JGColor;

public abstract class SuperMass extends PhysicalObject{

	protected float mass; 

	private static final int RADIUS = 3;
	
	public SuperMass(String id, int COLLISION_ID, JGColor color, float xpos, float ypos, float m) {
		// TODO Auto-generated constructor stub	
		super(id, COLLISION_ID, color);
		mass = m;
		
		init(xpos,ypos,m,0);
	}
	
	void init(float xpos, float ypos, float m, float g){
		CircleDef shape = new CircleDef();
		shape.radius = (float) RADIUS;
		shape.density = (float) m;
		
		createBody(shape);
        setBBox(-(int)RADIUS, -(int)RADIUS, 2 * (int)RADIUS, 2 * (int)RADIUS);
		
		this.setPos(xpos,ypos);
		this.setForce(0, 0);
	}
	
	

	public float getMass(){
		return mass; 
	}
    @Override
    public void paintShape ()
    {
        myEngine.setColor(myColor);
        myEngine.drawOval(x, y, (float)RADIUS * 2, (float)RADIUS * 2, true, true);
    }

	public abstract void calculateObjForce();




}
