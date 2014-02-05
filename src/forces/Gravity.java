package forces;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Gravity extends Force{
	float magnitude;
	ArrayList<SuperMass> masses;

	public Gravity(float accel, ArrayList<SuperMass> m){
		magnitude = accel;
		masses = m;
	}

	public void calculateForce() {
		if(isOn){
			for(SuperMass m: masses){
				m.setForce(0, m.getMass()*magnitude);
			}
		}
	}

	@Override
	public void toggleForces(int toggle){
		if(toggle == 1)
			isOn = true;
		if(toggle == 2)
			isOn = false;
		//System.out.println("Gravity is on: "+isOn);
		// TODO Auto-generated method stub
	}

}
