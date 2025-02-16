
import java.util.Comparator;
/**
 * The <code>SubjectComparatorAscend</code> class implements comparator
 * and it is used to compare the subjects of the emails in ascending order.
 */
public class SubjectComparatorAscend implements Comparator {
    public int compare(Object o1, Object o2) {
        Email e1 = (Email) o1;
        Email e2 = (Email) o2;

        return -1 * (e1.getSubject().compareTo(e2.getSubject()));
    }
}
