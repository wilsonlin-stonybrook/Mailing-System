
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

/**
 * The <code>Folder</code> class is used for the creation of a folder object
 * which holds a linked list of emails.
 */
public class Folder implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private LinkedList<Email> emails;
    private String currentSortingMethod;

    /**
     * Parameterized constructor to create a folder with specific name.
     * It also sets emails to an empty linked list and the current sorting method
     * to the date descending.
     * @param name
     * The name of the folder.
     */
    public Folder(String name) {
        this.name = name;
        this.emails = new LinkedList<>();
        this.currentSortingMethod = "dateDescending";
    }

    /**
     * Gets the name of the folder.
     * @return
     * Returns the name of the folder.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the folder.
     * @param name
     * The name of the folder.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the emails of the folder.
     * @return
     * Returns the emails of the folder.
     */
    public LinkedList<Email> getEmails() {
        return emails;
    }

    /**
     * Sets the emails of the folder.
     * @param emails
     * The emails of the folder.
     */
    public void setEmails(LinkedList<Email> emails) {
        this.emails = emails;
    }

    /**
     * Gets the current sorting method of the folder.
     * @return
     * Returns the current sorting method of the folder.
     */
    public String getCurrentSortingMethod() {
        return currentSortingMethod;
    }

    /**
     * Sets the current sorting method of the folder.
     * @param currentSortingMethod
     * The current sorting method of the folder.
     */
    public void setCurrentSortingMethod(String currentSortingMethod) {
        this.currentSortingMethod = currentSortingMethod;
    }

    /**
     * A method to add an email to the folder according to the current
     * sorting method.
     * @param email
     * The email to be added to the folder.
     */
    public void addEmail(Email email) {
        emails.add(email);
        sortEmails();
    }

    /**
     * A method to remove an email from the folder by index.
     * @param index
     * The index of the email.
     * @return
     * Returns the email at the index to be removed.
     * @throws IndexOutOfBoundsException
     * Indicates that the index cannot be less than zero or greater than the size of the emails
     * in the folder.
     */
    public Email removeEmail(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= emails.size()) {
            throw new IndexOutOfBoundsException("Index cannot be less than zero or greater than the size of the list.");
        }
        return emails.remove(index);
    }

    /**
     * A method to sort the emails alphabetically by subject in ascending order.
     */
    public void sortBySubjectAscending() {
        Collections.sort(emails, new SubjectComparatorAscend());
    }

    /**
     * A method to sort the emails alphabetically by subject in descending order.
     */
    public void sortBySubjectDescending() {
        Collections.sort(emails, new SubjectComparatorDescend());
    }

    /**
     * A method to sort the emails by date in ascending order.
     */
    public void sortByDateAscending() {
        Collections.sort(emails, new DateComparatorAscend());
    }

    /**
     * A method to sort the emails by date in descending order.
     */
    public void sortByDateDescending() {
        Collections.sort(emails, new DateComparatorDescend());
    }

    /**
     * A method to sort emails based on the current sorting method.
     */
    public void sortEmails() {
        if (currentSortingMethod.equals("subjectAscending")) {
            sortBySubjectAscending();
        }
        else if (currentSortingMethod.equals("subjectDescending")) {
            sortBySubjectDescending();
        }
        else if (currentSortingMethod.equals("dateAscending")) {
            sortByDateAscending();
        }
        else if (currentSortingMethod.equals("dateDescending")) {
            sortByDateDescending();
        }
        else {
            throw new IllegalArgumentException("Unknown sorting method: " + currentSortingMethod);
        }
    }
}
