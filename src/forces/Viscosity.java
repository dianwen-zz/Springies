package forces;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class Viscosity extends Force{
	float magnitude;
	ArrayList<SuperMass> masses;
	
	public Viscosity(float visc, ArrayList<SuperMass> m){
		magnitude = visc;
		masses = m;
	}
	
	public void calculateForce() {
		for(SuperMass m: masses){
			Vec2 viscForce = m.getBody().getLinearVelocity().mul(-magnitude);
			m.setForce(viscForce.x, viscForce.y);
		}
	}

}
