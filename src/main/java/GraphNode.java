import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/3.
 */
public class GraphNode {
    private long id;
    private String name;
    private Point point;
    private double x;
    private double y;
    private ArrayList<GraphNode> neighbors;
    private ArrayList<Connection> connections;

    public GraphNode(long id, String name, Point point) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.x = point.getX();
        this.y = point.getY();
        this.neighbors = null;
    }

    public GraphNode(long id, String name, double x, double y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        //this.point = new Point(x, y, id, name);
        this.neighbors = null;
    }

    public GraphNode(long id, String name, double x, double y, ArrayList<GraphNode> neighbors) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.neighbors = neighbors;
    }

    public GraphNode(long id, String name, Point point, ArrayList<GraphNode> neighbors) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.x = point.getX();
        this.y = point.getY();
        this.neighbors = neighbors;
    }

    public GraphNode(long id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = null;
        this.neighbors = null;
    }

    public GraphNode(long id, double x, double y, ArrayList<Connection> connection) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.connections = connection;
        this.neighbors = null;
    }

    public GraphNode(long id) {
        this.id = id;
        this.neighbors = null;
    }

    public GraphNode(double x, double y) {
        this.x = x;
        this.y = y;
        this.neighbors = null;
    }

    public GraphNode(GraphNode gn) {
        this.id = gn.getId();
        this.name = gn.getName();
        this.point = gn.getPoint();
        this.x = gn.getX();
        this.y = gn.getY();
        this.neighbors = gn.getNeighbors();
        this.connections = gn.getConnections();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ArrayList<GraphNode> getNeighbors() {
        return neighbors;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public void setNeighbors(ArrayList<GraphNode> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbors(GraphNode neighbor) {
        this.neighbors.add(neighbor);
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public double getDistance(GraphNode node) {
        return Math.sqrt((x - node.getX())
                * (x - node.getX()) + (y - node.getY())
                * (y - node.getY()));
    }

    @Override
    public boolean equals(Object obj)  {
        if (!(obj instanceof GraphNode)) return false;
        if (((GraphNode) obj).getId() == this.getId()) return true;
        return false;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
