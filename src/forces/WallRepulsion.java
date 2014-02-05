package forces;

import java.util.ArrayList;

import nodes.SuperMass;

public class WallRepulsion extends Force{
	private float[] topWall = new float[2]; //1st element is magnitude, 2nd is exponent
	private float[] leftWall = new float[2];
	private float[] bottomWall = new float[2];
	private float[] rightWall = new float[2];
	private ArrayList<SuperMass> masses;
	private boolean isTopOn = true; 
	private boolean isBottomOn = true; 
	private boolean isRightOn = true; 
	private boolean isLeftOn = true;
	private int topMask = 8;
	private int leftMask = 16;
	private int bottomMask = 32;
	private int rightMask = 64;

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
		isTopOn = ((toggle&topMask) == topMask) ? true:false;
		isLeftOn = ((toggle&leftMask) == leftMask) ? true:false;
		isBottomOn = ((toggle&bottomMask) == bottomMask) ? true:false;
		isRightOn = ((toggle&rightMask) == rightMask) ? true:false;
	}
}
