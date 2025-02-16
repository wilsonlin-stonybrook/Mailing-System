
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * The <code>Email</code> class implements serializable and it is used to
 * create an email object that stores information about each email.
 */
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timestamp;

    /**
     * Parameterized constructor to create an email with specific
     * to, cc, bcc, subject, body, and timestamp.
     * @param to
     * The recipient's email address that the email is being sent to.
     * @param cc
     * The email addresses to cc.
     * @param bcc
     * The email addresses to bcc.
     * @param subject
     * The subject of the email.
     * @param body
     * The body of the email.
     * @param timestamp
     * The timestamp of the email when it was created.
     */
    public Email(String to, String cc, String bcc, String subject, String body, GregorianCalendar timestamp) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
    }

    /**
     * Gets the recipient's email address to send the email to.
     * @return
     * Returns the recipient's email address to send the email to.
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the recipient's email address to send the email to.
     * @param to
     * The recipient's email address to send the email to.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets the email addresses to cc for the email.
     * @return
     * Returns the email addresses to cc for the email.
     */
    public String getCc() {
        return cc;
    }

    /**
     * Sets the email addresses to cc for the email.
     * @param cc
     * The email addresses to cc for the email.
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * Gets the email addresses to bcc for the email.
     * @return
     * Returns the email addresses to bcc for the email.
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * Sets the email addresses to bcc for the email.
     * @param bcc
     * The email addresses to bcc for the email.
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    /**
     * Gets the subject of the email.
     * @return
     * Returns the subject of the email.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject of the email.
     * @param subject
     * The subject of the email address.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the body of the email.
     * @return
     * Returns the body of the email.
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body of the email.
     * @param body
     * The body of the email.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the timestamp of the email when the email was created.
     * @return
     * Returns the timestamp of the email when the email was created.
     */
    public GregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the email when the email was created.
     * @param timestamp
     * The timestamp of the email when the email was created.
     */
    public void setTimestamp(GregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }
}
