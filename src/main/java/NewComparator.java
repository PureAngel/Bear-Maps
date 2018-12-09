import java.util.Comparator;

/**
 * Created by Administrator on 2016/7/22.
 */
public class NewComparator implements Comparator<QTreeNode> {

    @Override
    public int compare(QTreeNode o1, QTreeNode o2) {
        if (o1.getUllat() > o2.getUllat()) {
            return -1;
        } else if (o1.getUllat() < o2.getUllat()) {
            return 1;
        } else {
            if (o1.getUllon() > o2.getUllon()) {
                return 1;
            } else if (o1.getUllon() < o2.getUllon()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
