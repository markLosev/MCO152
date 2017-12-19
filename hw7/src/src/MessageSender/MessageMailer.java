package src.MessageSender;

import src.EmailMessage;
import src.ReadMail;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


interface IMailer
{
    void sendMail(String to, String subject, String message);
}

class Emailer implements IMailer {
    final static Logger logger = Logger.getLogger(IMailer.class.getName());

    public void sendMail(String to, String subject, String message) {
        logger.trace("entering sendMail()");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        logger.trace("creating session object");
                        return new PasswordAuthentication("dependencyinjectionhw","Moshe123");
                    }
                });

        try {

            Message communication = new MimeMessage(session);
            communication.setFrom(new InternetAddress("dependencyinjectionhw@gmail.com"));
            communication.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            communication.setSubject(subject);
            communication.setText(message);
            Transport.send(communication );

            System.out.println("Done");

        } catch (MessagingException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        logger.trace("Leaving sendMail method");
    }
}

public class MessageMailer {
    private IMailer email;
    private ReadMail mailReader;
    private EmailMessage [] messages;
    private Logger logger = Logger.getLogger(MessageMailer.class.getName());

    public MessageMailer(IMailer email, ReadMail mailReader) {
        this.email = email;
        this.mailReader = mailReader;
    }

    public void forwardEmails () {
        logger.trace("Entering forwardEmails()");
        retrieveInboxMessages();
        for (int i = 0; i < messages.length; i++) {
            email.sendMail(messages[i].getSubject(), "Forwarding messages to " + messages[i].getSubject(), messages[i].getMessage());
        };
        logger.trace("Leaving forwardEmails()");
    }

    private void retrieveInboxMessages() {
        logger.trace("Entering retrieveInboxMessages()");
        try {
            messages = mailReader.readMail();
        } catch (MessagingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.trace("Leaving retrieveInboxMessages()");
    }

    public static void main(String [] args) {
        PropertyConfigurator.configure("/Users/moshelosev/IdeaProjects/MCO 152/MCO152/hw7/resources/log4j.properties");
        IMailer mail = new Emailer();
        ReadMail reader = new ReadMail();
        MessageMailer sender = new MessageMailer(mail, reader);
        sender.forwardEmails();
    }
}
