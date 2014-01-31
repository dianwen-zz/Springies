import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParser {

	public static void main(String args[]) {
		try {

			File stocks = new File("assets/example.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			System.out.println("==========================");

			//parse nodes
			NodeList nodes = doc.getElementsByTagName("nodes");
				System.out.println("total nodes: " + nodes.getLength());
				//get masses
				System.out.println("dynamic masses:");
				NodeList nodeNodes = doc.getElementsByTagName("mass");
				for( int j = 0; j < nodeNodes.getLength(); j++){
					Node node = nodeNodes.item(j);
					System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node) +
							" vx: " + getNodeAttr("vx", node) + " vy: " + getNodeAttr("vy", node) + " mass: " + getNodeAttr("mass", node));
				}
				System.out.println();

				//get fixed masses
				System.out.println("fixed masses:");
				nodeNodes = doc.getElementsByTagName("fixed");
				for( int j = 0; j < nodeNodes.getLength(); j++){
					Node node = nodeNodes.item(j);
					System.out.println("id: " + getNodeAttr("id", node) + " x: " + getNodeAttr("x", node) + " y: " + getNodeAttr("y", node));
				}
				System.out.println();

			

			//parse links
			nodes = doc.getElementsByTagName("links");
				//get springs
				System.out.println("springs:");
				nodeNodes = doc.getElementsByTagName("spring");
				for( int j = 0; j < nodeNodes.getLength(); j++){
					Node node = nodeNodes.item(j);
					System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
							" constant: " + getNodeAttr("constant", node));
				}
				System.out.println();

				//get fixed muscles
				System.out.println("muscles:");
				nodeNodes = doc.getElementsByTagName("muscle");
				for( int j = 0; j < nodeNodes.getLength(); j++){
					Node node = nodeNodes.item(j);
					System.out.println("a: " + getNodeAttr("a", node) + " b: " + getNodeAttr("b", node) + " restlength: " + getNodeAttr("restlength", node) +
							" constant: " + getNodeAttr("constant", node) + " amplitude: " + getNodeAttr("amplitude", node));				}
				System.out.println();

			
			
		} catch (Exception ex) {
			ex.printStackTrace();
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
