/**
 * Wilson Lin
 * 115091711
 * wilson.lin.2@stonybrook.edu
 * Hw 5
 * CSE214.R04 Summer 2024
 */
import java.util.Comparator;
/**
 * The <code>SubjectComparatorDescend</code> class implements comparator
 * and it is used to compare the subjects of the emails in descending order.
 */
public class SubjectComparatorDescend implements Comparator {
    public int compare(Object o1, Object o2) {
        Email e1 = (Email) o1;
        Email e2 = (Email) o2;

        return (e1.getSubject().compareTo(e2.getSubject()));
    }
}