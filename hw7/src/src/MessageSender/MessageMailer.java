package src.MessageSender;

import src.EmailMessage;
import src.ReadMail;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


interface IMailer
{
    void sendMail(String to, String subject, String message);
}

class Emailer implements IMailer {
    public void sendMail(String to, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
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
            throw new RuntimeException(e);
        }
    }
}

public class MessageMailer {
    private IMailer email;
    private ReadMail mailReader;
    private EmailMessage [] messages;

    public MessageMailer(IMailer email, ReadMail mailReader) {
        this.email = email;
        this.mailReader = mailReader;
    }

    public void forwardEmails () {
        retrieveInboxMessages();
        for (int i = 0; i < messages.length; i++) {
            email.sendMail(messages[i].getSubject(), "Forwarding messages to " + messages[i].getSubject(), messages[i].getMessage());
        };
    }

    private void retrieveInboxMessages() {
        try {
            messages = mailReader.readMail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        IMailer mail = new Emailer();
        ReadMail reader = new ReadMail();
        MessageMailer emailer = new MessageMailer(mail, reader);
        emailer.forwardEmails();
    }
}
