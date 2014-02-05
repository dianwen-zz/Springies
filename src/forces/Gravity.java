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
		bitMask = 1;
	}

	public void calculateForce() {
		if(isOn){
			for(SuperMass m: masses){
				m.setForce(0, m.getMass()*magnitude);
			}
		}
	}
}
