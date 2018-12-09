
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 * pathfinding, under some constraints.
 * See OSM documentation on
 * <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 * and the java
 * <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 *
 * @author Alan Yao
 */
public class MapDBHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    private String activeState = "";
    private final GraphDB g;

    private boolean isWay = false;
    Long nodeId = null;
    String way = null;
    ArrayList<Long> wayNode = new ArrayList<>();

    public MapDBHandler(GraphDB g) {
        this.g = g;
    }

    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     *
     * @param uri        The Namespace URI, or the empty string if the element
     *                   has no Namespace URI or
     *                   if Namespace processing is not being performed.
     * @param localName  The local name (without prefix), or the empty string if Namespace
     *                   processing is not being performed.
     * @param qName      The qualified name (with prefix),
     *                   or the empty string if qualified names are
     *                   not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes)
            throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("node")) {
            activeState = "node";
            nodeId = Long.parseLong(attributes.getValue("id"));
            Double nodeX = Double.parseDouble(attributes.getValue("lon"));
            Double nodeY = Double.parseDouble(attributes.getValue("lat"));
            ArrayList<Connection> connectionsList = new ArrayList<>();
            GraphNode node = new GraphNode(nodeId, nodeX, nodeY, connectionsList);
            g.getIdToNode().put(nodeId, node);

        } else if (qName.equals("way")) {
            activeState = "way";
            way = attributes.getValue("ref");
//            System.out.println("Beginning a way...");
        } else if (activeState.equals("way") && qName.equals("nd")) {
            Long connectedNode = Long.parseLong(attributes.getValue("ref"));
            wayNode.add(connectedNode);
        } else if (activeState.equals("way") && qName.equals("tag")) {
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
            GraphDB.cleanString(v);
            if (k.equals("highway") && ALLOWED_HIGHWAY_TYPES.contains(v)) {
                isWay = true;
            }
//            System.out.println("Tag with k=" + k + ", v=" + v + ".");
        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            String s = attributes.getValue("v");
            g.location.put(nodeId, s);
            //   System.out.println("Node with name: " + attributes.getValue("v"));
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     *
     * @param uri       The Namespace URI, or the empty string
     *                  if the element has no Namespace URI or
     *                  if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName     The qualified name (with prefix),
     *                  or the empty string if qualified names are
     *                  not available.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("way")) {
            if (isWay) {
                for (int i = 0; i <= wayNode.size() - 1; i++) {
                    GraphNode startNode = g.getIdToNode().get(wayNode.get(i));
                    g.idToWay.put(startNode.getId(), startNode);
                    if (i < wayNode.size() - 1) {
                        GraphNode endNode = g.getIdToNode().get(wayNode.get(i + 1));
                        Connection conB = new Connection(startNode, endNode);
                        Connection conE = new Connection(endNode, startNode);
                        endNode.addConnection(conE);
                        startNode.addConnection(conB);
                    }
                }
                isWay = false;
            }
            wayNode.clear();
        }
        if (qName.equals("node")) {
            activeState = "";
            nodeId = null;
        }
    }

}
