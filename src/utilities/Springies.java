package utilities;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import physicalObjects.Fixed;
import physicalObjects.Mass;
import physicalObjects.SuperMass;
import physicalObjects.Wall;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import forces.Force;
import forces.Spring;


@SuppressWarnings("serial")
public class Springies extends JGEngine
{
	private static final int WALLED_AREA_ADJUSTMENT = 20;
	private static List<Force> allForces = new ArrayList<Force>();
	private static List<SuperMass> allSuperMasses = new ArrayList<SuperMass>();
	private static List<Wall> allWalls = new ArrayList<Wall>();
	private static Map<Integer, Integer> inGameControls = new HashMap<Integer, Integer>();
	private static XmlParser parser = new XmlParser(); 
	private static ClickAndDragCalculations dragger;
	private static boolean createdMousePositionMass = false;
	Mass massAtMouse;
	SuperMass closestMass;
	Spring mouseSpring;

	//Toggle is bitfield of 1s in Binary, indicating that all forces are on by default
	private int toggle = 0b001111111;
	//bit 8 and 9 are default 0 because a value of 1 calls muscle amplitude change methods, refer to setInGameControlsMap method
	//all other environmental forces are on

	public Springies ()
	{
		// set the window size
		int height = 700;
		double aspect = 16.0 / 9.0; //aspect ratio for the window
		initEngineComponent((int) (height * aspect), height);
		//set in game controls map
		setInGameControlsMap();

	}

	public void setInGameControlsMap(){ //maps keypresses with the bit associated with them
		inGameControls.put(KeyEvent.VK_G, 1); 
		inGameControls.put(KeyEvent.VK_V, 2); 
		inGameControls.put(KeyEvent.VK_M, 4); 
		inGameControls.put(KeyEvent.VK_1, 8); 
		inGameControls.put(KeyEvent.VK_2, 16); 
		inGameControls.put(KeyEvent.VK_3, 32); 
		inGameControls.put(KeyEvent.VK_4, 64); 
		inGameControls.put(KeyEvent.VK_PLUS, 128); 
		inGameControls.put(KeyEvent.VK_MINUS, 256); 
		inGameControls.put(KeyEvent.VK_EQUALS, 256); 
		inGameControls.put(KeyEvent.VK_UP, 1); 
		inGameControls.put(KeyEvent.VK_DOWN, -1); 
	}

	@Override
	public void initCanvas ()
	{
		// I have no idea what tiles do...
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null); // standard font -> use default font
	}

	@Override
	public void initGame ()
	{
		setFrameRate(60, 2);
		// NOTE:
		//   world coordinates have y pointing down
		//   game coordinates have y pointing up
		// so gravity is up in world coords and down in game coords
		// so set all directions (e.g., forces, velocities) in world coords
		WorldManager.initWorld(this);
		addWalls();
		parser.parseEnvironment();
		parseAssemblyXML(); //has its own method because it needs to be called outside of initGame
		dragger = new ClickAndDragCalculations(this);
	}


	@Override
	public void doFrame ()
	{
		// update game objects
		WorldManager.getWorld().step(1f, 1);

		for(Force f: allForces){
			f.calculateForce();
			f.toggleForces(toggle);
		}
		clearMuscleToggleBits();
		addAndClearAssembliesToggle(getLastKey()); //checks whether or not keypresses have been made to clear or add assemblies
		environmentalForceToggle(getLastKey()); //checks whether or not environmental forces have been toggled
		changeWallSizeToggle(getLastKey()); //checks toggles for changing the wall size
		checkMouseClickAndDrag();
		
		moveObjects();
		checkCollision(1 + 2, 1);
	}

	public void checkMouseClickAndDrag() {
		if(getMouseButton(1) && !createdMousePositionMass && allSuperMasses.size()!=0){
			closestMass = dragger.getClosestMass(allSuperMasses); //detects mouse click and creates spring for dragging if neccessary
			massAtMouse = new Mass("massAtMouse",getMousePos().x,getMousePos().y,0,0,0);
			mouseSpring = new Spring(massAtMouse, closestMass, 0,12);
			allForces.add(mouseSpring);
			createdMousePositionMass = true;
		}
		if(massAtMouse != null){
			massAtMouse.setPos(getMousePos().x, getMousePos().y);
		}
		if(!getMouseButton(1) && createdMousePositionMass){
			massAtMouse.remove();
			mouseSpring.remove();
			allForces.remove(mouseSpring);
			createdMousePositionMass = false;
		}
	}

	public void clearMuscleToggleBits(){
		int clearBits8And9 = (int) (Math.pow(2,0) + Math.pow(2,1) + Math.pow(2,2) + Math.pow(2,3) +
				Math.pow(2,4) + Math.pow(2,5) + Math.pow(2,6));
		toggle=toggle&clearBits8And9; //clears bits 8 and 9 that are for changing muscle amplitude so it's only called once when toggled


	}

	public void addAndClearAssembliesToggle(int keyEvent){
		if(keyEvent == KeyEvent.VK_N ){
			parseAssemblyXML();
			clearLastKey();
		}
		if(keyEvent == KeyEvent.VK_C){ 
			clearAllTheDamnAssemblies();
			clearLastKey();
		}
	}

	public void changeWallSizeToggle(int keyEvent){
		if(keyEvent == KeyEvent.VK_UP || keyEvent == KeyEvent.VK_DOWN){ 
			for(Wall wall: allWalls){
				wall.changeWallSize(WALLED_AREA_ADJUSTMENT * inGameControls.get(keyEvent));
			}
			clearLastKey();
		}	
	}

	public void environmentalForceToggle(int keyEvent){
		if(!(keyEvent == KeyEvent.VK_UP || keyEvent == KeyEvent.VK_DOWN)){ 
			Integer toggleVal = inGameControls.get(keyEvent);
			if(toggleVal!= null){
				toggle ^=toggleVal; 
			}
			clearLastKey();
		}

	}
	@Override
	public void paintFrame ()
	{ //draws the status of environmental force toggles
		drawString("G: " + ((toggle&1)==1),20,20,-1);
		drawString("V: " + ((toggle&2)==2),20,50,-1);
		drawString("M: " + ((toggle&4)==4),20,80,-1);
		drawString("1: " + ((toggle&8)==8),20,110,-1);
		drawString("2: " + ((toggle&16)==16),20,140,-1);
		drawString("3: " + ((toggle&32)==32),20,170,-1);
		drawString("4: " + ((toggle&64)==64),20,200,-1);

	}



	private void addWalls ()
	{
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
		Wall wall = new Wall("top", WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		allWalls.add(wall);

		wall = new Wall ("bottom",WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		allWalls.add(wall);

		wall = new Wall ("left",WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		allWalls.add(wall);

		wall = new Wall("right",WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
		allWalls.add(wall);

	}

	public void parseAssemblyXML(){
		parser.parseAssembly(); 
		allForces = parser.getForce(); 
		allSuperMasses = parser.getAllSuperMasses();
	}

	public void clearAllTheDamnAssemblies(){
		for(SuperMass m: allSuperMasses){
			m.remove();
		}
		allSuperMasses.clear();
		for(Force f: allForces){
			f.remove();
		}
		allForces.clear();
	}
}
