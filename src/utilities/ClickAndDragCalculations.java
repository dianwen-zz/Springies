package utilities;

import java.util.List;

import jgame.JGPoint;

import org.jbox2d.common.Vec2;

import physicalObjects.SuperMass;

public class ClickAndDragCalculations {
	
	Springies engine;

	public ClickAndDragCalculations(Springies springies) {
		engine = springies;
	}

	protected SuperMass getClosestMass(List<SuperMass> masses) {
		double minDistance = Double.MAX_VALUE;
		SuperMass mass = null;
		for(SuperMass m: masses){
			double distance = findDistance(m.getPos(), engine.getMousePos());
			if(distance<minDistance){
				minDistance = distance;
				mass = m;
			}
		}
		System.out.println(mass.getPos().toString());
		return mass;
	}

	public double findDistance(Vec2 a, JGPoint b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}
}
