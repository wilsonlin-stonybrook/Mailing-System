/**
 * Wilson Lin
 * 115091711
 * wilson.lin.2@stonybrook.edu
 * Hw 5
 * CSE214.R04 Summer 2024
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The <code>Mailbox</code> class is used for the creation of a mailbox object
 * and it will be used to hold the folders, and it allows user input.
 */
public class Mailbox implements Serializable  {
    private static final long serialVersionUID = 1L;
    private Folder inbox;
    private Folder trash;
    private LinkedList<Folder> folders;
    private static Mailbox mailbox;

    /**
     * A default constructor that sets inbox to a new folder named inbox,
     * trash to a new folder named trash, and folders to an empty linked list.
     */
    public Mailbox() {
        inbox = new Folder("Inbox");
        trash = new Folder("Trash");
        folders = new LinkedList<>();
    }

    /**
     * A method to add folders to the mailbox.
     * @param folder
     * The folder to add to the mailbox.
     * @throws IllegalArgumentException
     * Indicates that the folder already exists.
     */
    public void addFolder(Folder folder) throws IllegalArgumentException {
        if (mailbox.folders.contains(folder)) {
            throw new IllegalArgumentException("Folder already exists!");
        }
        this.folders.add(folder);
    }

    /**
     * A method to delete a folder of a given name.
     * @param name
     * The name of the folder.
     * @throws IllegalArgumentException
     * Indicates that you cannot delete the inbox or trash folders.
     */
    public void deleteFolder(String name) throws IllegalArgumentException {
        if (name.equals("Inbox") || name.equals("Trash")) {
            throw new IllegalArgumentException("Cannot delete the inbox or trash folders.");
        }
        for (int i = 0; i < folders.size(); i++) {
            if (folders.get(i).getName().equals(name)) {
                folders.remove(i);
                System.out.println("The folder " + name + " has been deleted!");
            }
            else {
                System.out.println("No folder with that name!");
            }
        }
    }

    /**
     * A method to compose an email.
     */
    public void composeEmail() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter recipient (To): ");
        String to = input.nextLine();
        System.out.println("Enter carbon copy recipients (CC): ");
        String cc = input.nextLine();
        System.out.println("Enter blind carbon copy recipients (BCC): ");
        String bcc = input.nextLine();
        System.out.println("Enter subject line: ");
        String subject = input.nextLine();
        System.out.println("Enter body: ");
        String body = input.nextLine();
        GregorianCalendar timestamp = new GregorianCalendar();
        mailbox.inbox.addEmail(new Email(to, cc, bcc, subject, body, timestamp));
        System.out.println("Email successfully added to Inbox.");
    }

    /**
     * A method to delete a email.
     * @param email
     * The email to be deleted.
     */
    public void deleteEmail(Email email) {
        if (mailbox.inbox.getEmails().contains(email)) {
            mailbox.trash.addEmail(mailbox.inbox.removeEmail(mailbox.inbox.getEmails().indexOf(email)));
            System.out.println(email.getSubject() + " has been successfully been moved to the trash.");
        }
        for (Folder folder : folders) {
            if (folder.getEmails().contains(email)) {
                mailbox.trash.addEmail(folder.removeEmail(folder.getEmails().indexOf(email)));
            }
        }
        System.out.println("Email does not exist.");
    }

    /**
     * A method to clear out the trash.
     */
    public void clearTrash() {
        mailbox.trash.getEmails().clear();
        System.out.println(trash.getEmails().size() + " item(s) successfully deleted.");
    }

    /**
     * A method to move an email from one folder to another.
     * @param email
     * The email to be moved from one folder to another.
     * @param target
     * The target folder.
     */
    public void moveEmail(Email email, Folder target) {
        Folder targetFolder = getFolder(target.getName());
        if (targetFolder == null) {
            targetFolder = inbox;
        }
        for(Folder folder : folders) {
            if(folder.getEmails().contains(email)) {
                folder.removeEmail(folder.getEmails().indexOf(email));
            }
        }
        targetFolder.addEmail(email);
    }

    /**
     * A method to get the folder.
     * @param name
     * The name of the folder.
     * @return
     * Returns the folder.
     */
    public Folder getFolder(String name) {
        if (name.equals("Inbox")) {
            return mailbox.inbox;
        }
        if (name.equals("Trash")) {
            return mailbox.trash;
        }
        for (int i = 0; i < mailbox.folders.size(); i++) {
            Folder folder = mailbox.folders.get(i);
            if (folder.getName().equals(name)) {
                return folder;
            }
            else {
                throw new IllegalArgumentException("No folder with that name!");
            }
        }
        return null;
    }

    /**
     * The main method to run the simulation.
     * @param args
     */
    public static void main(String[] args) {
        File file = new File("mailbox.obj");
        if (file.exists()) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                mailbox = (Mailbox) objectInputStream.readObject();
                System.out.println("Mailbox loaded from previous saved file.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading the file, starting with an new empty mailbox.");
                mailbox = new Mailbox();
            }
        } else {
            System.out.println("Previous saved file not found, starting with an empty mailbox.");
            mailbox = new Mailbox();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
        Scanner input = new Scanner(System.in);
        String option;
        boolean subMenu = true;
        while (true) {
            System.out.println("Mailbox: ");
            System.out.println("--------");
            System.out.println("Inbox");
            System.out.println("Trash");
            for (int i = 0; i < mailbox.folders.size(); i++) {
                System.out.println(mailbox.folders.get(i).getName());
            }
            printOptions();
            System.out.println("Enter a user option: ");
            option = input.next().toUpperCase();
            input.nextLine();
            try {
                switch (option) {
                    case "A":
                        System.out.println("Enter folder name: ");
                        String folderName = input.nextLine();
                        mailbox.addFolder(new Folder(folderName));
                        break;
                    case "R":
                        System.out.println("Enter the name of the folder: ");
                        folderName = input.nextLine();
                        mailbox.deleteFolder(folderName);
                        break;
                    case "C":
                        mailbox.composeEmail();
                        break;
                    case "F":
                        System.out.println("Enter folder name: ");
                        folderName = input.nextLine();
                        subMenu();
                        option = input.nextLine().toUpperCase();
                        switch (option) {
                            case "M":
                                System.out.println("Enter the index of the email to move: ");
                                int index = input.nextInt();
                                System.out.println();
                                System.out.println("Folders: ");
                                for (int i = 0; i < mailbox.folders.size(); i++) {
                                    System.out.println(mailbox.folders.get(i).getName());
                                }
                                System.out.println("Select a folder to move " + mailbox.inbox.getEmails().get(index).getSubject() + " to: ");
                                String targetFolder = input.nextLine();
                                Email email = mailbox.inbox.getEmails().get(index);
                                mailbox.moveEmail(email, mailbox.getFolder(targetFolder));
                                System.out.println();
                                break;
                            case "D":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                mailbox.deleteEmail(mailbox.inbox.getEmails().get(index));
                                break;
                            case "V":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                System.out.println("To: " + mailbox.getFolder(folderName).getEmails().get(index).getTo());
                                System.out.println("CC: " + mailbox.getFolder(folderName).getEmails().get(index).getCc());
                                System.out.println("BCC: " + mailbox.getFolder(folderName).getEmails().get(index).getBcc());
                                System.out.println("Subject: " + mailbox.getFolder(folderName).getEmails().get(index).getSubject());
                                System.out.println(mailbox.getFolder(folderName).getEmails().get(index).getBody());
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.getFolder(folderName).getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.getFolder(folderName).getEmails().size(); i++) {
                                    email = mailbox.getFolder(folderName).getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                break;
                            case "SA":
                                mailbox.getFolder(folderName).sortBySubjectAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.getFolder(folderName).getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.getFolder(folderName).getEmails().size(); i++) {
                                    email = mailbox.getFolder(folderName).getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in ascending order.");
                                break;
                            case "SD":
                                mailbox.getFolder(folderName).sortBySubjectDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.getFolder(folderName).getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.getFolder(folderName).getEmails().size(); i++) {
                                    email = mailbox.getFolder(folderName).getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in descending order.");
                                break;
                            case "DA":
                                mailbox.getFolder(folderName).sortByDateAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.getFolder(folderName).getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.getFolder(folderName).getEmails().size(); i++) {
                                    email = mailbox.getFolder(folderName).getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by date in ascending order.");
                                break;
                            case "DD":
                                mailbox.getFolder(folderName).sortByDateDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.getFolder(folderName).getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.getFolder(folderName).getEmails().size(); i++) {
                                    email = mailbox.getFolder(folderName).getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by date in descending order.");
                                break;
                            case "R":
                                break;
                            default:
                                System.out.println("Invalid input!");
                                if (input.equals("R")) {
                                    break;
                                }
                        }
                        break;
                    case "I":
                        System.out.println(mailbox.inbox.getName());
                        System.out.println();
                        System.out.println("Index |          Time        | Subject");
                        System.out.println("-------------------------------------");
                        for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                            Email email = mailbox.inbox.getEmails().get(i);
                            String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                            System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                        }
                        subMenu();
                        option = input.nextLine().toUpperCase();
                        switch (option) {
                            case "M":
                                System.out.println("Enter the index of the email to move: ");
                                int index = input.nextInt();
                                System.out.println();
                                System.out.println("Folders: ");
                                for (int i = 0; i < mailbox.folders.size(); i++) {
                                    System.out.println(mailbox.folders.get(i).getName());
                                }
                                System.out.println("Select a folder to move " + mailbox.inbox.getEmails().get(index - 1).getSubject() + " to: ");
                                String targetFolder = input.nextLine();
                                mailbox.moveEmail(mailbox.inbox.getEmails().get(index - 1), mailbox.getFolder(targetFolder));
                                System.out.println();
                                break;
                            case "D":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                mailbox.deleteEmail(mailbox.inbox.getEmails().get(index));
                                break;
                            case "V":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                System.out.println("To: " + mailbox.inbox.getEmails().get(index).getTo());
                                System.out.println("CC: " + mailbox.inbox.getEmails().get(index).getCc());
                                System.out.println("BCC: " + mailbox.inbox.getEmails().get(index).getBcc());
                                System.out.println("Subject: " + mailbox.inbox.getEmails().get(index).getSubject());
                                System.out.println(mailbox.inbox.getEmails().get(index).getBody());
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.inbox.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                                    Email email = mailbox.inbox.getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                break;
                            case "SA":
                                mailbox.inbox.sortBySubjectAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.inbox.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                                    Email email = mailbox.inbox.getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in ascending order.");
                                break;
                            case "SD":
                                mailbox.inbox.sortBySubjectDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.inbox.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                                    Email email = mailbox.inbox.getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in descending order.");
                                break;
                            case "DA":
                                mailbox.inbox.sortByDateAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.inbox.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                                    Email email = mailbox.inbox.getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by date in ascending order.");
                                break;
                            case "DD":
                                mailbox.inbox.sortByDateDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.inbox.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.inbox.getEmails().size(); i++) {
                                    Email email = mailbox.inbox.getEmails().get(i);
                                    String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                                }
                                System.out.println("Emails sorted by date in descending order.");
                                break;
                            case "R":
                                break;
                            default:
                                System.out.println("Invalid input!");
                                if (input.equals("R")) {
                                    break;
                                }
                        }
                        break;
                    case "T":
                        subMenu();
                        option = input.nextLine().toUpperCase();
                        System.out.println("Enter folder name: ");
                        folderName = input.nextLine();
                        subMenu();
                        option = input.nextLine().toUpperCase();
                        System.out.println();
                        System.out.println(mailbox.trash.getName());
                        System.out.println("Index |          Time        | Subject");
                        System.out.println("-------------------------------------");
                        for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                            Email email = mailbox.trash.getEmails().get(i);
                            String formattedDate = dateFormat.format(email.getTimestamp().getTime());
                            System.out.printf("  %d   |  %s | %s\n", (i + 1), formattedDate, email.getSubject());
                        }
                        switch (option) {
                            case "M":
                                System.out.println("Enter the index of the email to move: ");
                                int index = input.nextInt();
                                System.out.println();
                                System.out.println("Folders: ");
                                for (int i = 0; i < mailbox.folders.size(); i++) {
                                    System.out.println(mailbox.folders.get(i).getName());
                                }
                                System.out.println("Select a folder to move " + mailbox.trash.getEmails().get(index).getSubject() + " to: ");
                                String targetFolder = input.nextLine();
                                Email email = mailbox.trash.getEmails().get(index);
                                mailbox.moveEmail(email, mailbox.getFolder(targetFolder));
                                System.out.println();
                                break;
                            case "D":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                mailbox.trash.removeEmail(index);
                                break;
                            case "V":
                                System.out.println("Enter email index: ");
                                index = input.nextInt();
                                System.out.println("To: " + mailbox.trash.getEmails().get(index).getTo());
                                System.out.println("CC: " + mailbox.trash.getEmails().get(index).getCc());
                                System.out.println("BCC: " + mailbox.trash.getEmails().get(index).getBcc());
                                System.out.println("Subject: " + mailbox.trash.getEmails().get(index).getSubject());
                                System.out.println(mailbox.trash.getEmails().get(index).getBody());
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.trash.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                                    email = mailbox.trash.getEmails().get(i);
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), mailbox.trash.getEmails().get(i).getTimestamp(), email.getSubject());
                                }
                                break;
                            case "SA":
                                mailbox.trash.sortBySubjectAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.trash.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                                    email = mailbox.trash.getEmails().get(i);
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), mailbox.trash.getEmails().get(i).getTimestamp(), email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in ascending order.");
                                break;
                            case "SD":
                                mailbox.trash.sortBySubjectDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.trash.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                                    email = mailbox.trash.getEmails().get(i);
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), mailbox.trash.getEmails().get(i).getTimestamp(), email.getSubject());
                                }
                                System.out.println("Emails sorted by subject in descending order.");
                                break;
                            case "DA":
                                mailbox.trash.sortByDateAscending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.trash.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                                    email = mailbox.trash.getEmails().get(i);
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), mailbox.trash.getEmails().get(i).getTimestamp(), email.getSubject());
                                }
                                System.out.println("Emails sorted by date in ascending order.");
                                break;
                            case "DD":
                                mailbox.trash.sortByDateDescending();
                                System.out.println();
                                System.out.println();
                                System.out.println(mailbox.trash.getName());
                                System.out.println();
                                System.out.println();
                                System.out.println("Index |          Time        | Subject");
                                System.out.println("-------------------------------------");
                                for (int i = 0; i < mailbox.trash.getEmails().size(); i++) {
                                    email = mailbox.trash.getEmails().get(i);
                                    System.out.printf("  %d   |  %s | %s\n", (i + 1), mailbox.trash.getEmails().get(i).getTimestamp(), email.getSubject());
                                }
                                System.out.println("Emails sorted by date in descending order.");
                                break;
                            case "R":
                                break;
                            default:
                                System.out.println("Invalid input!");
                                if (input.equals("R")) {
                                    break;
                                }
                        }
                        break;
                    case "E":
                        mailbox.clearTrash();
                        break;
                    case "Q":
                        System.out.println("Program successfully exited and mailbox saved.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input!");
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * A method to print out the menu options.
     */
    private static void printOptions() {
        System.out.println("Menu: ");
        System.out.println("A - Add folder");
        System.out.println("R - Remove folder");
        System.out.println("C - Compose email");
        System.out.println("F - Open folder");
        System.out.println("I - Open Inbox");
        System.out.println("T - Open Trash");
        System.out.println("E - Empty Trash");
        System.out.println("Q - Quit");
    }

    /**
     * A method to print out the sub menu for the folders.
     */
    private static void subMenu() {
        System.out.println("M - Move email");
        System.out.println("D - Delete email");
        System.out.println("V - View email contents");
        System.out.println("SA - Sort by subject line in ascending order");
        System.out.println("SD - Sort by subject line in descending order");
        System.out.println("DA - Sort by date in ascending order");
        System.out.println("DD - Sort by date in descending order");
        System.out.println("R - Return to mailbox");
        System.out.println("Enter a user option: ");
    }
}
