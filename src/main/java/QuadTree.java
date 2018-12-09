import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/16.
 */
public class QuadTree {
    QTreeNode root;
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    ArrayList<QTreeNode> storeNodes = new ArrayList<>();
    String imgfirst = "img/";
    String imglast = ".png";
    int maxDepth = 7;

    public QuadTree() {
        root = new QTreeNode();
    }

    public QuadTree(QTreeNode t) {
        root = t;
    }

    // make tree and return the root
    public QTreeNode makeTree(double ullon, double ullat, double lrlon,
        double lrlat, String filename, int level) {
        QTreeNode t = new QTreeNode(ullon, ullat, lrlon, lrlat, imgfirst + filename + imglast,
            level);
        if (t.getLevel() <= maxDepth) {
            t.setFirstChild(makeTree(ullon, ullat, (ullon + lrlon) / 2, (ullat + lrlat) / 2,
                filename + "1", level + 1));
            t.setSecondChild(makeTree((ullon + lrlon) / 2, ullat, lrlon, (ullat + lrlat) / 2,
                filename + "2", level + 1));
            t.setThirdChild(makeTree(ullon, (ullat + lrlat) / 2, (ullon + lrlon) / 2, lrlat,
                filename + "3", level + 1));
            t.setForthChild(makeTree((ullon + lrlon) / 2, (ullat + lrlat) / 2, lrlon, lrlat,
                filename + "4", level + 1));
        }
        return t;
    }

    // ullon, ullat, lrlon, lrlat are the location of box
    public void collectNodes(QTreeNode t, int depth, double ullon, double ullat,
        double lrlon, double lrlat) {
        if (depth != t.getLevel()) {
            if (intersectBox(t.firstChild, ullon, ullat, lrlon, lrlat)) {
                collectNodes(t.firstChild, depth, ullon, ullat, lrlon, lrlat);
            }
            if (intersectBox(t.secondChild, ullon, ullat, lrlon, lrlat)) {
                collectNodes(t.secondChild, depth, ullon, ullat, lrlon, lrlat);
            }
            if (intersectBox(t.thirdChild, ullon, ullat, lrlon, lrlat)) {
                collectNodes(t.thirdChild, depth, ullon, ullat, lrlon, lrlat);
            }
            if (intersectBox(t.forthChild, ullon, ullat, lrlon, lrlat)) {
                collectNodes(t.forthChild, depth, ullon, ullat, lrlon, lrlat);
            }
        } else {
            if (intersectBox(t, ullon, ullat, lrlon, lrlat)) {
                storeNodes.add(t);
            }
        }
    }

    public boolean intersectBox(QTreeNode t, double ullon, double ullat,
        double lrlon, double lrlat) {
        if (t.getUllon() > lrlon || ullon > t.getLrlon()) {
            return false;
        }
        if (t.getUllat() < lrlat || ullat < t.getLrlat()) {
            return false;
        }
        return true;
    }

    public void clearNodelist() {
        storeNodes.clear();
    }

    public ArrayList<QTreeNode> getStoreNodes() {
        return storeNodes;
    }

}
