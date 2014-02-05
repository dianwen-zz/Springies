package forces;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Viscosity extends Force{
	private float magnitude;
	private ArrayList<SuperMass> masses;

	public Viscosity(float visc, ArrayList<SuperMass> m){
		magnitude = visc;
		masses = m;
		bitMask = 2;
	}

	public void calculateForce() {
		if(isOn){
			for(SuperMass m: masses){
				Vec2 viscosityForce = m.getBody().getLinearVelocity().mul(-magnitude);
				m.setForce(viscosityForce.x, viscosityForce.y);
			}
		}
	}
}
