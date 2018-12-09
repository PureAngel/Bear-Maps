import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 *
 * @author Alan Yao
 */
public class GraphDB {
    /**
     * Example constructor shows how to create and start an XML parser.
     *
     * @param dbPath Path to the XML file to be parsed.
     */

    // key: id, value: node
    HashMap<Long, GraphNode> idToNode = new HashMap<>();
    // key: id, value: way
    HashMap<Long, GraphNode> idToWay = new HashMap<>();
    // key: id, value: location
    HashMap<Long, String> location = new HashMap<>();

    //Trie trie = new Trie();
    Trie trie = null;

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
    }

    public HashMap<Long, GraphNode> getIdToNode() {
        return idToNode;
    }

    public void setIdToNode(HashMap<Long, GraphNode> idToNode) {
        this.idToNode = idToNode;
    }

    public HashMap<Long, GraphNode> getIdToWay() {
        return idToWay;
    }

    public void setIdToWay(HashMap<Long, GraphNode> idToWay) {
        this.idToWay = idToWay;
    }

    public HashMap<Long, String> getLocation() {
        return location;
    }

    public void setLocation(HashMap<Long, String> location) {
        this.location = location;
    }

    public Trie getTrie() {
        if (trie == null) {
            trie = new Trie();
            for (Map.Entry<Long, String> entry : location.entrySet()) {
                trie.addDefinition(entry.getValue(), entry.getKey());
            }
        }
        return trie;
    }
}
