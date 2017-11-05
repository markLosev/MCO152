import java.util.Date;

public class EmailMessage {
    String subject;
    String from;
    Date SentDate;
    String message;

    public EmailMessage(String subject, String from, String message, Date sentDate) {
        this.subject = subject;
        this.from = from;
        this. message = message;
        this.SentDate = sentDate;
    }
}
