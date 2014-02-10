package springies;

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

import nodes.Fixed;
import nodes.Mass;
import nodes.SuperMass;

import org.jbox2d.common.Vec2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	//Environmental variables, will change to read the environment XML file later
	float gravityAcceleration = (float)5;
	float viscosity = (float)1;
	float centerOfMassMagnitude = (float)75;
	float centerOfMassExponent = (float)2;
	float[] topWall = {1350,1};
	float[] leftWall = {1400,1};
	float[] bottomWall = {1350,1};
	float[] rightWall = {1400,1};
	static Map<String,SuperMass> obj = new HashMap<String,SuperMass>();
	static List<SuperMass> allSuperMasses = new ArrayList<SuperMass>();
	static List<Force> force = new ArrayList<Force>();


	public static List<SuperMass> getAllSuperMasses() {
		return allSuperMasses;
	}

	public static List<Force> getForce() {
		return force;
	}


	public void parse(){
		//Uses the FileChooser to let the user grab the XML file
		final FileChooser fc = new FileChooser();
		File file = fc.getFile();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			System.out.println("==========================");

			//Parse assembly xml file
			NodeList nodeNodes = doc.getElementsByTagName("mass");
			parseMasses(doc, nodeNodes); 		
			parseFixedMasses(doc, nodeNodes);
			parseMuscles(doc, nodeNodes);
			parseSprings(doc, nodeNodes); 
			
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
	
	public void parseMuscles(Document doc, NodeList nodeNodes){
		System.out.println("muscles:");
		nodeNodes = doc.getElementsByTagName("muscle");
		for( int j = 0; j < nodeNodes.getLength(); j++){
			Node node = nodeNodes.item(j);
			System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
					" constant: " + getNodeAttr("constant", node) + " amplitude: " + getNodeAttr("amplitude", node));

			float constant = getFloatValue(node, "constant");
			float restLength = getFloatValue(node, "restLength");;
			float amplitude = getFloatValue(node, "amplitude");;;

			SuperMass a = (SuperMass) obj.get(getNodeAttr("a", node));
			SuperMass b = (SuperMass) obj.get(getNodeAttr("b", node));

			force.add(new Muscle(a, b, restLength, constant, amplitude));
		}
		System.out.println();

	}

	public void parseMasses(Document doc, NodeList nodeNodes){
		System.out.println("dynamic masses:");
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

	public void parseFixedMasses(Document doc, NodeList nodeNodes){
		System.out.println("fixed masses:");
		nodeNodes = doc.getElementsByTagName("fixed");
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
	
	public void parseSprings(Document doc, NodeList nodeNodes){
		//parse links
		System.out.println("springs:");
		nodeNodes = doc.getElementsByTagName("spring");
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
	
	public float getFloatValue(Node node, String name){
		float value = 0; 
		if(!(getNodeAttr(name, node).equals(""))){
			value = Float.parseFloat(getNodeAttr(name, node));
		}
		return value; 
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
