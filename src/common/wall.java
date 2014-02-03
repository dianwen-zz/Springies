package common;

public class wall{
	int id; 
	float magnitude; 
	float exponent; 
	
	public int getId() {
		return id;
	}

	public float getMagnitude() {
		return magnitude;
	}

	public float getExponent() {
		return exponent;
	}

	
	 public wall(int id, float magnitude, float exponent){
		 this.id = id; 
		 this.magnitude = magnitude; 
		 this.exponent = exponent; 
	 }
	 
	 public String toString (){
		return "id: "+id+ " magnitude: "+magnitude+" exponent: "+exponent;
	 }
}