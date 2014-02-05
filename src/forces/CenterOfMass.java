package forces;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import nodes.SuperMass;

public class CenterOfMass extends Force{
	float magnitude;
	float exponent;
	ArrayList<SuperMass> masses;

	public CenterOfMass(float cOmMag, float cOmExp, ArrayList<SuperMass> m){
		magnitude = cOmMag;
		exponent = cOmExp;
		masses = m;
		bitMask = 4;
	}

	@Override
	public void calculateForce() {
		if(isOn){
			Vec2 center = calculateCenter();

			for(SuperMass m: masses){
				//Vec2 displacement = center.sub(m.getBody().getWorldCenter());
				Vec2 displacement = center.sub(m.getPos());
				Vec2 cOmForce = displacement.mul((float) (magnitude/Math.pow(displacement.length(),exponent)));

				m.setForce(cOmForce.x, cOmForce.y);
			}
		}
	}

	public Vec2 calculateCenter(){
		float totalMass = 0;
		float weightedXLoc = 0; //Summation of m_i*x_i, where m_i is the mass of a mass, and x_i is its x coordinate
		float weightedYLoc = 0;

		if(isOn){
			for(SuperMass m: masses){
				weightedXLoc += (float) (m.getMass()*m.x);
				weightedYLoc += (float) (m.getMass()*m.y);
				totalMass += m.getMass();
			}
		}

		return new Vec2(weightedXLoc/totalMass, weightedYLoc/totalMass);
	}
}
