package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import forces.CenterOfMass;
import forces.Force;
import forces.Gravity;
import forces.Muscle;
import forces.Spring;
import forces.Viscosity;
import forces.WallRepulsion;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import physicalObjects.Fixed;
import physicalObjects.Mass;
import physicalObjects.SuperMass;

public class XmlParser {
	//Environmental variables, will change to read the environment XML file later
	float gravityAcceleration = (float)5;
	float viscosity = (float)1;
	float centerOfMassMagnitude = (float)75;
	float centerOfMassExponent = (float)2;
	float[] topWall = {1350,1}; //1st element is magnitude, 2nd is exponent
	float[] leftWall = {1400,1};
	float[] bottomWall = {1350,1};
	float[] rightWall = {1400,1};
	static Map<String,SuperMass> obj = new HashMap<String,SuperMass>();
	static List<SuperMass> allSuperMasses = new ArrayList<SuperMass>();
	static List<Force> force = new ArrayList<Force>();


	List<SuperMass> getAllSuperMasses() {
		return allSuperMasses;
	}

	List<Force> getForce() {
		return force;
	}
	
	protected void parseEnvironment(){
		//Uses the FileChooser to let the user grab the XML file
		Document doc = setupDocument();
		
		gravityAcceleration = grabEnvironmentTag(doc, "gravity", "magnitude");
		System.out.println("Gravity: " + gravityAcceleration);
		
		viscosity = grabEnvironmentTag(doc, "viscosity", "magnitude");
		System.out.println("Viscosity: " + viscosity);
		
		centerOfMassMagnitude = grabEnvironmentTag(doc, "centermass", "magnitude");
		System.out.println("Center of Mass Magnitude: " + centerOfMassMagnitude);
		
		centerOfMassExponent = grabEnvironmentTag(doc, "centermass", "exponent");
		System.out.println("Center of Mass Exponent: " + centerOfMassExponent);

		NodeList walls = doc.getElementsByTagName("wall");
		topWall[0]=getFloatValue(walls.item(0),"magnitude");
		topWall[1]=getFloatValue(walls.item(0),"exponent");
		
		leftWall[0]=getFloatValue(walls.item(1),"magnitude");
		leftWall[1]=getFloatValue(walls.item(1),"exponent");
		
		bottomWall[0]=getFloatValue(walls.item(2),"magnitude");
		bottomWall[1]=getFloatValue(walls.item(2),"exponent");
		
		rightWall[0]=getFloatValue(walls.item(3),"magnitude");
		rightWall[1]=getFloatValue(walls.item(3),"exponent");

	}
	
	private float grabEnvironmentTag(Document doc, String tagName, String property){
		NodeList nodeNodes = doc.getElementsByTagName(tagName);
		return getFloatValue(nodeNodes.item(0),property);
	}

	private Document setupDocument() {
		final FileChooser fc = new FileChooser();
		File file = fc.getFile();
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}

	protected void parseAssembly(){
		//Uses the FileChooser to let the user grab the XML file
		Document doc = setupDocument();

			//Parse assembly xml file
			parseMasses(doc); 		
			parseFixedMasses(doc);
			parseMuscles(doc);
			parseSprings(doc); 
			
			//Pass list of masses to each environmental (non-spring/muscle) Force constructor
			force.add(new Gravity(gravityAcceleration, new ArrayList<SuperMass>(obj.values())));
			force.add(new Viscosity(viscosity, new ArrayList<SuperMass>(obj.values())));
			force.add(new CenterOfMass(centerOfMassMagnitude, centerOfMassExponent, new ArrayList<SuperMass>(obj.values())));
			force.add(new WallRepulsion(topWall, leftWall, bottomWall, rightWall, new ArrayList<SuperMass>(obj.values())));
	}
	
	private void parseMuscles(Document doc){
		System.out.println("muscles:");
		NodeList nodeNodes = doc.getElementsByTagName("muscle");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
					" constant: " + getNodeAttr("constant", node) + " amplitude: " + getNodeAttr("amplitude", node));

			float constant = getFloatValue(node, "constant");
			float restLength = getFloatValue(node, "restLength");
			float amplitude = getFloatValue(node, "amplitude");

			SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
			SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

			force.add(new Muscle(a, b, restLength, constant, amplitude));
		}
		System.out.println();

	}

	private void parseMasses(Document doc){
		System.out.println("dynamic masses:");
		NodeList nodeNodes = doc.getElementsByTagName("mass");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node) +
					" vx: " + getNodeAttr("vx", node) + " vy: " + getNodeAttr("vy", node) + " mass: " + getNodeAttr("mass", node));

			float mass = 1;
			if(!(getNodeAttr("mass", node).equals(""))){
				mass = Float.parseFloat(getNodeAttr("mass", node));
			}

			float xv = getFloatValue(node, "xv");
			float yv = getFloatValue(node, "yv");
			float x = getFloatValue(node, "x");
			float y = getFloatValue(node, "y");
			
			String id = getNodeAttr("id", node);
			
			Mass tempMass = new Mass(id, x, y+20, mass, xv, yv);
			allSuperMasses.add(tempMass);
			obj.put(id, tempMass);
		}
		System.out.println();
	}

	private void parseFixedMasses(Document doc){
		System.out.println("fixed masses:");
		NodeList nodeNodes = doc.getElementsByTagName("fixed");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node));

			String id = getNodeAttr("id", node);
			
			float x = getFloatValue(node, "x");
			float y = getFloatValue(node, "y");

			Fixed tempFixed = new Fixed(id, x, y);
			allSuperMasses.add(tempFixed);
			obj.put(id, tempFixed);
			System.out.println();
		}
	}
	
	private void parseSprings(Document doc){
		//parse links
		System.out.println("springs:");
		NodeList nodeNodes = doc.getElementsByTagName("spring");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
					" constant: " + getNodeAttr("constant", node));

			float constant = getFloatValue(node, "constant");
			float restLength = getFloatValue(node, "restLength");;

			SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
			SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

			force.add(new Spring(a, b, restLength, constant));
		}
		System.out.println();
	}
	
	private float getFloatValue(Node node, String name){
		float value = 1; 
		if(!(getNodeAttr(name, node).equals(""))){
			value = Float.parseFloat(getNodeAttr(name, node));
		}
		return value; 
	}
	
	private String getNodeAttr(String attrName, Node node ) {
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
