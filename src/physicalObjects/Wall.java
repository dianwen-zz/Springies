package physicalObjects;

import org.jbox2d.common.Vec2;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;



public class Wall extends PhysicalObjectRect {
	private String wall_side; 
	
	
	public Wall(String wall_side, double wallDimension_1, double wallDimension_2){
		super("wall", 2, JGColor.green,
				wallDimension_1,
				wallDimension_2);			
		this.wall_side = wall_side; 
	}
	
	public void changeWallSize(int WALLED_AREA_ADJUSTMENT){
		Vec2 point = this.getPos();
		if(wall_side.equals("top"))
			this.setPos(point.x, point.y + WALLED_AREA_ADJUSTMENT);
		if(wall_side.equals("bottom"))
			this.setPos(point.x, point.y - WALLED_AREA_ADJUSTMENT);
		if(wall_side.equals("left"))
			this.setPos(point.x + WALLED_AREA_ADJUSTMENT, point.y);
		if(wall_side.equals("right"))
			this.setPos(point.x - WALLED_AREA_ADJUSTMENT, point.y );
	}

	
	
}
