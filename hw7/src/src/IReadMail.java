package src;

import javax.mail.MessagingException;

interface IReadMail {
    public EmailMessage[] readMail() throws MessagingException;
}
