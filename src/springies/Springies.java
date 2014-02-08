package springies;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import nodes.Fixed;
import nodes.Mass;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import forces.CenterOfMass;
import forces.Force;
import forces.Gravity;
import forces.Muscle;
import forces.Spring;
import forces.Viscosity;
import forces.WallRepulsion;


@SuppressWarnings("serial")
public class Springies extends JGEngine
{
	private static final int WALLED_AREA_ADJUSTMENT = 20;
	static List<Force> force = new ArrayList<Force>();
	static List<SuperMass> allSuperMasses = new ArrayList<SuperMass>();
	static List<Wall> allWalls = new ArrayList<Wall>();
	static Map<Integer, Integer> inGameControls = new HashMap<Integer, Integer>();

	//Toggle is bitfield of 1s in Binary, indicating that all forces are on by default
	private int toggle =
			(int)  (Math.pow(2,0) + Math.pow(2,1) + Math.pow(2,2) + Math.pow(2,3) +
					Math.pow(2,4) + Math.pow(2,5) + Math.pow(2,6) + Math.pow(2,7) +
					Math.pow(2,8)); //only 7 out of 9 bits are used for toggling
	//bit 8 and 9 are default 0 because a value of 1 calls muscle amplitude change methods

	public Springies ()
	{
		// set the window size
		int height = 700;
		double aspect = 16.0 / 9.0;
		initEngineComponent((int) (height * aspect), height);
		//set in game controls map
		setInGameControlsMap();
	}

	public void setInGameControlsMap(){
		inGameControls.put(KeyEvent.VK_G, 1); 
		inGameControls.put(KeyEvent.VK_V, 2); 
		inGameControls.put(KeyEvent.VK_M, 4); 
		inGameControls.put(KeyEvent.VK_1, 8); 
		inGameControls.put(KeyEvent.VK_2, 16); 
		inGameControls.put(KeyEvent.VK_3, 32); 
		inGameControls.put(KeyEvent.VK_4, 64); 
		inGameControls.put(KeyEvent.VK_MINUS, 128); 
		inGameControls.put(KeyEvent.VK_PLUS, 256); 
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
		parseXML();
	}

	@Override
	public void doFrame ()
	{
		// update game objects
		WorldManager.getWorld().step(1f, 1);

		for(Force f: force){
			f.calculateForce();
			f.toggleForces(toggle);
		}

		int clearBits8And9 = (int) (Math.pow(2,0) + Math.pow(2,1) + Math.pow(2,2) + Math.pow(2,3) +
				Math.pow(2,4) + Math.pow(2,5) + Math.pow(2,6));
		toggle=toggle&clearBits8And9; //clears bits 8 and 9 that are for changing muscle amplitude so it's only called once when toggled

		addAndClearAssemblies(getLastKey()); 
		toggler(getLastKey());
		changeWallSize(getLastKey()); 	
		moveObjects();
		checkCollision(1 + 2, 1);
	}
	
	public void addAndClearAssemblies(int keyEvent){
		if(keyEvent == KeyEvent.VK_N ){
			parseXML();
			clearLastKey();
		}
		if(keyEvent == KeyEvent.VK_C){ 
			clearAllTheDamnAssemblies();
			clearLastKey();
		}
	}

	public void changeWallSize(int keyEvent){
		if(keyEvent == KeyEvent.VK_UP || keyEvent == KeyEvent.VK_DOWN){ 
			for(Wall wall: allWalls){
				wall.changeWallSize(WALLED_AREA_ADJUSTMENT * inGameControls.get(keyEvent));
			}
			clearLastKey();
		}	
	}

	public void toggler(int keyEvent){
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
	{
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

	public static void parseXML() {
		//Environmental variables, will change to read the environment XML file later
		float gravityAcceleration = (float)5;
		float viscosity = (float)1;
		float centerOfMassMagnitude = (float)75;
		float centerOfMassExponent = (float)2;
		float[] topWall = {1350,1};
		float[] leftWall = {1400,1};
		float[] bottomWall = {1350,1};
		float[] rightWall = {1400,1};

		//Uses the FileChooser to let the user grab the XML file
		final FileChooser fc = new FileChooser();
		File file = fc.getFile();

		HashMap<String,SuperMass> obj = new HashMap<String,SuperMass>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			System.out.println("==========================");

			//parse fixed and dynamic masses
			System.out.println("dynamic masses:");
			NodeList nodeNodes = doc.getElementsByTagName("mass");
			for( int j = 0; j < nodeNodes.getLength(); j++){
				Node node = nodeNodes.item(j);
				System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node) +
						" vx: " + getNodeAttr("vx", node) + " vy: " + getNodeAttr("vy", node) + " mass: " + getNodeAttr("mass", node));

				float mass = 1;
				float xv = 0;
				float yv = 0;

				if(!(getNodeAttr("mass", node).equals(""))){
					mass = Float.parseFloat(getNodeAttr("mass", node));
				}
				if(!(getNodeAttr("xv", node).equals(""))){
					xv = Float.parseFloat(getNodeAttr("xv", node));
				}
				if(!(getNodeAttr("yv", node).equals(""))){
					yv = Float.parseFloat(getNodeAttr("yv", node));
				}
				float x = Float.parseFloat(getNodeAttr("x", node));
				float y = Float.parseFloat(getNodeAttr("y", node));
				String id = getNodeAttr("id", node);
				Mass tempMass = new Mass(id, x, y+20, mass, xv, yv);
				allSuperMasses.add(tempMass);
				obj.put(id, tempMass);
			}
			System.out.println();

			System.out.println("fixed masses:");
			nodeNodes = doc.getElementsByTagName("fixed");
			for( int j = 0; j < nodeNodes.getLength(); j++){
				Node node = nodeNodes.item(j);
				System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node));

				String id = getNodeAttr("id", node);
				float x = Float.parseFloat(getNodeAttr("x", node));
				float y = Float.parseFloat(getNodeAttr("y", node));

				Fixed tempFixed = new Fixed(id, x, y);
				allSuperMasses.add(tempFixed);
				obj.put(id, tempFixed);
				System.out.println();
			}


			//parse links
			System.out.println("springs:");
			nodeNodes = doc.getElementsByTagName("spring");
			for( int j = 0; j < nodeNodes.getLength(); j++){
				Node node = nodeNodes.item(j);
				System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
						" constant: " + getNodeAttr("constant", node));

				float constant = 1;
				if(!(getNodeAttr("constant", node).equals(""))){
					constant = Float.parseFloat(getNodeAttr("constant", node));
				}
				float restLength = 50;
				if(!(getNodeAttr("restlength", node).equals(""))){
					restLength = Float.parseFloat(getNodeAttr("restlength", node));
				}
				SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
				SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

				force.add(new Spring(a, b, restLength, constant));
			}
			System.out.println();

			System.out.println("muscles:");
			nodeNodes = doc.getElementsByTagName("muscle");
			for( int j = 0; j < nodeNodes.getLength(); j++){
				Node node = nodeNodes.item(j);
				System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
						" constant: " + getNodeAttr("constant", node) + " amplitude: " + getNodeAttr("amplitude", node));

				float constant = 1;
				if(!(getNodeAttr("constant", node).equals(""))){
					constant = Float.parseFloat(getNodeAttr("constant", node));
				}
				float restLength = 50;
				if(!(getNodeAttr("restlength", node).equals(""))){
					restLength = Float.parseFloat(getNodeAttr("restlength", node));
				}
				float amplitude = 50;
				if(!(getNodeAttr("amplitude", node).equals(""))){
					amplitude = Float.parseFloat(getNodeAttr("amplitude", node));
				}
				SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
				SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

				force.add(new Muscle(a, b, restLength, constant, amplitude));
			}
			System.out.println();

			//Pass list of masses to each environmental (non-spring/muscle) Force constructor
			force.add(new Gravity(gravityAcceleration, new ArrayList<SuperMass>(obj.values())));
			force.add(new Viscosity(viscosity, new ArrayList<SuperMass>(obj.values())));
			force.add(new CenterOfMass(centerOfMassMagnitude, centerOfMassExponent, new ArrayList<SuperMass>(obj.values())));
			force.add(new WallRepulsion(topWall, leftWall, bottomWall, rightWall, new ArrayList<SuperMass>(obj.values())));

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected static String getNodeAttr(String attrName, Node node ) {
		NamedNodeMap attrs = node.getAttributes();
		for (int y = 0; y < attrs.getLength(); y++ ) {
			Node attr = attrs.item(y);
			if (attr.getNodeName().equalsIgnoreCase(attrName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	public void clearAllTheDamnAssemblies(){
		for(SuperMass m: allSuperMasses){
			m.remove();
		}
		for(Force f: force){
			f.remove();
		}
	}
}
