package forces;

import java.util.ArrayList;

import nodes.SuperMass;

public class WallRepulsion extends Force{
	float[] topWall = new float[2]; //1st element is magnitude, 2nd is exponent
	float[] leftWall = new float[2];
	float[] bottomWall = new float[2];
	float[] rightWall = new float[2];
	ArrayList<SuperMass> masses;
	boolean isTopOn = true; 
	boolean isBottomOn = true; 
	boolean isRightOn = true; 
	boolean isLeftOn = true; 


	public WallRepulsion(float[] top, float[] left, float[] bottom, float[] right, ArrayList<SuperMass> m){
		topWall = top;
		leftWall = left;
		bottomWall = bottom;
		rightWall = right;
		masses = m;
	}

	@Override
	public void calculateForce() {
		for(SuperMass m: masses){
			float topDisplacement = m.getPos().y;
			if(isTopOn)
				m.setForce(0, topWall[0]/Math.pow(topDisplacement, topWall[1]));
			float leftDisplacement = m.getPos().x;
			if(isLeftOn)
				m.setForce(leftWall[0]/Math.pow(leftDisplacement, leftWall[1]),0);
			float bottomDisplacement = (eng.pfHeight())-m.getPos().y;
			if(isBottomOn)
				m.setForce(0, -bottomWall[0]/Math.pow(bottomDisplacement, bottomWall[1]));
			float rightDisplacement = eng.pfWidth()-m.getPos().x;
			if(isRightOn)
				m.setForce(-rightWall[0]/Math.pow(rightDisplacement, rightWall[1]),0);
		}
	}

	@Override
	public void toggleForces(int toggle) {
		if(toggle == 7)
			isTopOn = true;
		if(toggle == 8)
			isTopOn = false;
		if(toggle == 9)
			isLeftOn = true;
		if(toggle == 10)
			isLeftOn = false;
		if(toggle == 11)
			isBottomOn = true;
		if(toggle == 12)
			isBottomOn = false;
		if(toggle == 13)
			isRightOn = true;
		if(toggle == 14)
			isRightOn = false;

	}
}
