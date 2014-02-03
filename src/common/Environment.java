package common;

import java.util.ArrayList;

import jgame.JGPoint;

public class Environment {
	static float gravAccel; 
	static float viscosityDampingConstant; 
	static float centerOfMass_Magnitude; 
	static float centerOfMass_Exponent;
	static JGPoint GlobalCenter = new JGPoint();
	ArrayList<wall> walls = new ArrayList<wall>();
	
	public wall getWall(int id) {
		for(wall w: walls ){
			if(w.getId() == id)
				return w;
		}
		return null;  //what to return 
	}
	
	public ArrayList<wall> getAllWalls(){
		return walls;
	}


	public void setWall(int id, float magnitude, float exponent ) {
		wall w = new wall(id, magnitude, exponent);
		walls.add(w);
	}


	public static JGPoint getGlobalCenter() {
		return GlobalCenter;
	}


	public static void setGlobalCenter(JGPoint globalCenter) {
		GlobalCenter = globalCenter;
	}

	public static float getGravAccel() {
		return gravAccel;
	}
	
	public static void setGravAccel(float gravAccel) {
		Environment.gravAccel = gravAccel;
	}
	
	public static float getViscosityDampingConstant() {
		return viscosityDampingConstant;
	}
	
	public static void setViscosityDampingConstant(float viscosityDampingConstant) {
		Environment.viscosityDampingConstant = viscosityDampingConstant;
	}
	
	public static float getCenterOfMass_Magnitude() {
		return centerOfMass_Magnitude;
	}
	
	public static void setCenterOfMass_Magnitude(float centerOfMass_Magnitude) {
		Environment.centerOfMass_Magnitude = centerOfMass_Magnitude;
	}
	
	public static float getCenterOfMass_Exponent() {
		return centerOfMass_Exponent;
	}
	
	public static void setCenterOfMass_Exponent(float centerOfMass_Exponent) {
		Environment.centerOfMass_Exponent = centerOfMass_Exponent;
	}
	 
}
