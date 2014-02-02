package springies;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectCircle;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import nodes.Fixed;
import nodes.Mass;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import forces.Force;
import forces.Spring;


@SuppressWarnings("serial")
public class Springies extends JGEngine
{
	static HashMap<String,SuperMass> obj = new HashMap<String,SuperMass>();
	static ArrayList<Force> force = new ArrayList<Force>();
	
	public Springies ()
	{
		// set the window size
		int height = 1000;
		double aspect = 16.0 / 9.0;
		initEngineComponent((int) (height * aspect), height);
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
		chooseFile();
		System.out.println(pfWidth() + " " + pfHeight());
	}

	private void addWalls ()
	{
		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
		PhysicalObject wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		wall = new PhysicalObjectRect("wall", 2, JGColor.green,
				WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
	}

	@Override
	public void doFrame ()
	{
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		for(SuperMass o: obj.values()){
			o.calculateObjForce();
		}
		for(Force f: force){
			f.calculateForce();
		}

		moveObjects();
		checkCollision(1 + 2, 1);
	}

	@Override
	public void paintFrame ()
	{
		// nothing to do
		// the objects paint themselves
	}


	public static void chooseFile() {
		final FileChooser fc = new FileChooser();
		File file = fc.start();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			System.out.println("==========================");

			//parse fixed and dynamic masses
			buildDynamicMasses(doc);
			System.out.println();
			buildFixedMasses(doc);
			System.out.println();

			//parse links
			buildSprings(doc);
			System.out.println();
			buildMuscles(doc);
			System.out.println();



		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void buildMuscles(Document doc) {
		NodeList nodeNodes;
		System.out.println("muscles:");
		nodeNodes = doc.getElementsByTagName("muscle");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
					" constant: " + getNodeAttr("constant", node) + " amplitude: " + getNodeAttr("amplitude", node));				}
	}

	public static void buildSprings(Document doc) {
		NodeList nodeNodes;
		System.out.println("springs:");
		nodeNodes = doc.getElementsByTagName("spring");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
					" constant: " + getNodeAttr("constant", node));

			double constant = 1;
			if(!(getNodeAttr("constant", node).equals(""))){
				constant = Double.parseDouble(getNodeAttr("constant", node));
			}
			float rl = 50;
			if(!(getNodeAttr("restlength", node).equals(""))){
				rl = Float.parseFloat(getNodeAttr("restlength", node));
			}
			SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
			SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

			force.add(new Spring(a, b, rl, constant));
		}
	}

	public static void buildFixedMasses(Document doc) {
		System.out.println("fixed masses:");
		NodeList nodeNodes = doc.getElementsByTagName("fixed");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node));
			
			String id = getNodeAttr("id", node);
			float x = Float.parseFloat(getNodeAttr("x", node));
			float y = Float.parseFloat(getNodeAttr("y", node));
			
			obj.put(id, new Fixed(id, x, y));
		}
	}

	public static void buildDynamicMasses(Document doc) {
		System.out.println("dynamic masses:");
		NodeList nodeNodes = doc.getElementsByTagName("mass");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node) +
					" vx: " + getNodeAttr("vx", node) + " vy: " + getNodeAttr("vy", node) + " mass: " + getNodeAttr("mass", node));

			float gravAccel = (float)1;
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
			System.out.println(mass);

			obj.put(id, new Mass(id, x, y, mass, xv, yv, gravAccel));
		}
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
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
}
