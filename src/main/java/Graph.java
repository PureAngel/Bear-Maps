import java.util.*;

/**
 * Created by Administrator on 2016/8/3.
 */
public class Graph {
    private GraphNode startNode;
    private GraphNode endNode;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    //  HashMap<Long, GraphNode> idToNode;
    //  HashMap<Long, GraphNode> idToWay = new HashMap<>();

    public Graph(GraphNode startNode, GraphNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.startX = startNode.getX();
        this.startY = startNode.getY();
        this.endX = endNode.getX();
        this.endY = endNode.getY();
    }

    public Graph(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startNode = new GraphNode(startX, startY);
        this.endNode = new GraphNode(endX, endY);
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

    public ArrayList<Long> shortestPath() {
        GraphNode node;

        // final path from startNode to endNode
        ArrayList<Long> finalpath = new ArrayList<>();
        finalpath.add(endNode.getId());
        ArrayList<Long> path = new ArrayList<>();

        //record distance of each node
        HashMap<GraphNode, Double> distance = new HashMap<>();
        // record the vertex along the path
        PriorityQueue<GraphNode> pq = new PriorityQueue<>((a, b) ->
                (distance.get(a) + a.getDistance(endNode)
                        >= distance.get(b) + b.getDistance(endNode) ? 1 : -1));
        // node, previous
        HashMap<GraphNode, GraphNode> previousNode = new HashMap<>();
        // known region
        HashSet<GraphNode> known = new HashSet<>();

        distance.put(startNode, 0.);
        pq.add(startNode);
        int j = 0;
        while (!pq.isEmpty()) {
            j++;
            node = pq.poll();
            if (known.contains(node)) {
                continue;
            }
            if (node.equals(endNode)) {
                if (previousNode.get(node) == null) {
                    finalpath.add(node.getId());
                } else {
                    while (!previousNode.get(node).equals(startNode)) {
                        finalpath.add(previousNode.get(node).getId());
                        node = previousNode.get(node);
                    }
                }
                finalpath.add(startNode.getId());
                for (int i = finalpath.size() - 1; i >= 0; i--) {
                    path.add(finalpath.get(i));
                }
                return path;
            }
            //add node to known
            known.add(node);

            for (int i = 0; i < node.getConnections().size(); i++) {
                GraphNode u = node.getConnections().get(i).endNode;

                // path(s -> node) + edge(node -> u) + distance(u, endNode)
                double newDist = distance.get(node) + node.getDistance(u);

                if (!known.contains(u) && (distance.get(u) == null)
                        || newDist < distance.get(u)) {
                    distance.put(u, newDist);
                    pq.add(u);
                    previousNode.put(u, node);
                }
            }
        }

        return path;
    }

    public void nearestNode() {
        //  double minStartDistance = Double.MAX_VALUE;
        double minStartDistance = Double.POSITIVE_INFINITY;
        double minEndDistance = Double.MAX_VALUE;
        GraphNode sn = new GraphNode(startNode);
        GraphNode en = new GraphNode(endNode);
        for (Map.Entry<Long, GraphNode> entry : MapServer.getG().idToWay.entrySet()) {
            double startDistance = startNode.getDistance(new GraphNode(entry.getValue().getX(),
                    entry.getValue().getY()));
            double endDistance = endNode.getDistance(new GraphNode(entry.getValue().getX(),
                    entry.getValue().getY()));
            if (startDistance <= minStartDistance) {
                minStartDistance = startDistance;
                sn = entry.getValue();
            }
            if (endDistance < minEndDistance) {
                minEndDistance = endDistance;
                en = entry.getValue();
            }
        }
        this.startNode = sn;
        this.endNode = en;
    }

}
