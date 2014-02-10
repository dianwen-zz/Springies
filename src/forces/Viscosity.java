package forces;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import physicalObjects.SuperMass;

public class Viscosity extends Force{
	private float magnitude;
	private List<SuperMass> masses;

	public Viscosity(float viscosity, ArrayList<SuperMass> m){
		magnitude = viscosity;
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
