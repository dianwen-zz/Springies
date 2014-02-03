package common;
import jgame.JGPoint;

public class CenterAndMass{
	public JGPoint center;
	public float mass;

	public CenterAndMass(JGPoint center, float mass){
		this.center = center; 
		this.mass = mass; 
	}

	public JGPoint getCenter(){
		return center; 
	}

	public float getMass(){
		return mass; 
	}
}