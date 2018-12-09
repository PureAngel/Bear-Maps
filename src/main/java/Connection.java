/**
 * Created by Administrator on 2016/8/2.
 */
public class Connection {
    GraphNode startNode;
    GraphNode endNode;
    double distance;

    public Connection(GraphNode startNode, GraphNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.distance = Math.sqrt((startNode.getX() - endNode.getX())
                * (startNode.getX() - endNode.getX())
                + (startNode.getY() - endNode.getY())
                * (startNode.getY() - endNode.getY()));
    }

    public GraphNode getStartNode() {
        return startNode;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    public double getDistance() {
        return distance;
    }

}
