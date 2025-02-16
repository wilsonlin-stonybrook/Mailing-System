
import java.util.Comparator;
/**
 * The <code>DateComparatorDescend</code> class implements comparator
 * and it is used to compare the timestamp of the emails in descending order.
 */
public class DateComparatorDescend implements Comparator {
    public int compare(Object o1, Object o2) {
        Email e1 = (Email) o1;
        Email e2 = (Email) o2;

        return (e1.getTimestamp().compareTo(e2.getTimestamp()));
    }
}
