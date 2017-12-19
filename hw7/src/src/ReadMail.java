package src;

import org.apache.log4j.Logger;
import javax.mail.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class ReadMail implements IReadMail {

    private Logger logger = Logger.getLogger(ReadMail.class.getName());

    public EmailMessage[] readMail() throws MessagingException {
        logger.trace("entering readMail()");
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", "pop.gmail.com");
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        Store store = null;
        try {
            store = emailSession.getStore("pop3s");
        } catch (NoSuchProviderException e) {
            logger.error(e);
            e.printStackTrace();
        }

        try {
            store.connect("pop.gmail.com", "dependencyinjectionhw@gmail.com", "Moshe123");
        } catch (MessagingException e) {
            logger.error(e);
            e.printStackTrace();
        }

        Folder emailFolder = null;
        try {
            emailFolder = store.getFolder("INBOX");
        } catch (MessagingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        assert emailFolder != null;
        try {
            emailFolder.open(Folder.READ_ONLY);
        } catch (MessagingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        Message[] messages = new Message[0];
        try {
            messages = emailFolder.getMessages();
        } catch (MessagingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        EmailMessage[] contents = new EmailMessage[messages.length];

        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            Address[] from = message.getFrom();
            String subject = message.getSubject();
            Date dateRecieved = message.getReceivedDate();
            String text = "";
            try {
                text = message.getContent().toString();
            } catch (IOException e) {
                logger.error(e);
                e.printStackTrace();
            }
            contents[i] = new EmailMessage(subject,from,text,dateRecieved);
        }
        logger.trace("leabing readMail()");
        return contents;
    }
}
