/**
 * Created by Aaron on 7/17/2016.
 */


public class Point {
    private double x;
    private double y;
    private long id;
    private String name;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, long id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getId() {
        return id;
    }

    public String getGetName() {
        return name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point findMiddle(Point point1, Point point2) {
        return new Point((point1.getX() + point2.getX()) / 2, (point1.getY() + point2.getY()) / 2);
    }


}

