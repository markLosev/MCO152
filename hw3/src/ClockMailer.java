import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import sun.plugin2.message.transport.Transport;

import javax.jms.Message;
import javax.jms.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

interface IClock
{
    LocalDateTime now();
}

interface IMailer
{
    void sendMail(String to, String subject, String message);
}

class Clock implements IClock{
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

class FakeClock implements IClock{
    public LocalDateTime now() {
        String now = "2016-11-09 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(now, formatter);
    }
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

public class ClockMailer {
    private IClock clock;
    private IMailer email;

    public ClockMailer(IClock clock, IMailer email) {
        this.clock = clock;
        this.email = email;
    }

    public void midnightEmail(String to, String subject, String message) {
        LocalDateTime currentTime = clock.now();
        if (currentTime.getHour() == 0 && currentTime.getMinute() == 0) {
            email.sendMail( to, subject, message);
        }
    }

    public static void main(String [] args) {
        IClock clock = new FakeClock();
        IMailer mail = new Emailer();
        ClockMailer emailer = new ClockMailer(clock, mail);
        emailer.midnightEmail("mark.losev@yahoo.com", "HW message", "Congratulations you have finished the HW");
    }
}
