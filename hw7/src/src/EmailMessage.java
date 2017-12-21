package src;


import org.apache.log4j.Logger;

import javax.mail.Address;
import java.util.Date;

public class EmailMessage {
    String subject;
    Address [] from;
    Date SentDate;
    String message;
    private Logger logger = Logger.getLogger(EmailMessage.class.getName());

    public EmailMessage(String subject, Address[] from, String message, Date sentDate) {
        this.subject = subject;
        this.from = from;
        this. message = message;
        this.SentDate = sentDate;
    }

    public String getMessage() {
        logger.trace("Entering getMessage");
        return message;
    }

    public Date getSentDate() {
        logger.trace("entering getSentDate");
        return SentDate;
    }

    public String getSubject() {
        logger.trace("entering getSubject");
        return subject;
    }

    public Address[] getFrom() {
        logger.trace("entering getFrom()");
        return from;
    }
}
