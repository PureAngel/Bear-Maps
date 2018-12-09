
/**
 * Created by Administrator on 2016/7/16.
 */
public class QTreeNode {
    /*four children on each node*/
    QTreeNode firstChild;
    QTreeNode secondChild;
    QTreeNode thirdChild;
    QTreeNode forthChild;

    private double ullon;
    private double ullat;
    private double lrlon;
    private double lrlat;

    private String name;
    private int level;

    public QTreeNode() {
        firstChild = null;
        secondChild = null;
        thirdChild = null;
        forthChild = null;
        this.ullon = QuadTree.ROOT_ULLON;
        this.ullat = QuadTree.ROOT_ULLAT;
        this.lrlon = QuadTree.ROOT_LRLON;
        this.lrlat = QuadTree.ROOT_LRLAT;
        name = "root";
        level = 0;
    }

    public QTreeNode(double ullon, double ullat, double lrlon,
                     double lrlat, String name, int level) {
        this.ullon = ullon;
        this.ullat = ullat;
        this.lrlon = lrlon;
        this.lrlat = lrlat;
        firstChild = null;
        secondChild = null;
        thirdChild = null;
        forthChild = null;
        this.name = name;
        this.level = level;
    }

    public QTreeNode(Point point1, Point point2, QTreeNode first, QTreeNode second,
                     QTreeNode third, QTreeNode forth, String name, int level) {
        this.ullon = point1.getX();
        this.ullat = point1.getY();
        this.lrlon = point2.getX();
        this.lrlat = point2.getY();
        firstChild = first;
        secondChild = second;
        thirdChild = third;
        forthChild = forth;
        this.name = name;
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setFirstChild(QTreeNode firstChild) {
        this.firstChild = firstChild;
    }

    public void setSecondChild(QTreeNode secondChild) {
        this.secondChild = secondChild;
    }

    public void setThirdChild(QTreeNode thirdChild) {
        this.thirdChild = thirdChild;
    }

    public void setForthChild(QTreeNode forthChild) {
        this.forthChild = forthChild;
    }

    public double getUllon() {
        return ullon;
    }

    public double getUllat() {
        return ullat;
    }

    public double getLrlon() {
        return lrlon;
    }

    public double getLrlat() {
        return lrlat;
    }

    public Point getUpper() {
        return new Point(ullon, ullat);
    }

    public Point getLower() {
        return new Point(lrlon, lrlat);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    //point: upper point of the query box
    public boolean containsBox(Point point) {
        if (ullat >= point.getY()
                && ullon <= point.getX()
                &&
                lrlat <= point.getY()
                && lrlon >= point.getX()) {
            return true;
        }
        return false;
    }

    //public boolean containsImg()

    //check whether this node is leaf
    public boolean isLeaf() {
        if (this.firstChild != null
                || this.secondChild != null
                ||
                this.thirdChild != null
                || this.forthChild != null) {
            return false;
        }
        return true;
    }

}
