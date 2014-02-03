package common;

import java.util.ArrayList;



import jgame.JGPoint;

public class ComputeWeightedCenter {

	private ArrayList<CenterAndMass> centers = new ArrayList<CenterAndMass>();
	private JGPoint GlobalCenter = new JGPoint();
	
	public void collectCenters(double x, double y, float mass){	
		CenterAndMass CAM = new CenterAndMass(new JGPoint((int)x,(int)y), mass);
		centers.add(CAM);
	}
	
	public JGPoint computeGlobalCenter(){
		double MassXProduct = 0; 
		double MassYProduct = 0;
		float SumOfMasses = 0;		
	
		for(CenterAndMass CAM: centers){
			SumOfMasses += CAM.mass;
			MassXProduct += CAM.mass * CAM.getCenter().x;
			MassYProduct += CAM.mass * CAM.getCenter().y;
		}	
		
		GlobalCenter.x = (int)(MassXProduct/SumOfMasses);
		GlobalCenter.y= (int)(MassYProduct/SumOfMasses);
		
		return GlobalCenter; 
	}
	
	public void clearList(){
		centers.clear();
	}
}
