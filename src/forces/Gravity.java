package forces;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Gravity extends Force{
	private float magnitude;
	private ArrayList<SuperMass> masses;

	public Gravity(float acceleration, ArrayList<SuperMass> m){
		magnitude = acceleration;
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
