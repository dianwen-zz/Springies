package links;

import jboxGlue.PhysicalObject;
import jgame.JGObject;

import org.jbox2d.common.Vec2;

import nodes.Mass;

public class Viscosity extends Force{

	public Viscosity(int xdir, int ydir, int factor) {
		super(xdir, ydir, factor);
		// TODO Auto-generated constructor stub
	}
	
	public void gravitationalPull(PhysicalObject object){//to be called on all objects with each frame
		object.setForce(xdir, ydir);
	}

}