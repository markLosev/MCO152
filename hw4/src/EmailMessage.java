import javax.mail.Address;
import java.util.Date;

public class EmailMessage {
    String subject;
    Address [] from;
    Date SentDate;
    String message;

    public EmailMessage(String subject, Address[] from, String message, Date sentDate) {
        this.subject = subject;
        this.from = from;
        this. message = message;
        this.SentDate = sentDate;
    }
}
