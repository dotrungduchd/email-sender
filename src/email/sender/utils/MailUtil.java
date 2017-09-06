package email.sender.utils;

import email.sender.entity.MailConfigEntity;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    public static void sendEmail(MailConfigEntity config, String content) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", config.mailHost);
        properties.setProperty("mail.smtp.port", config.mailPort);
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.mailFrom));
        message.addRecipients(Message.RecipientType.TO, config.mailTo);
        message.setSubject(config.mailSubject);
        message.setContent(content, config.mailContentType);
        Transport.send(message);
    }

    public static void sendEmail(MailConfigEntity config, Address[] mailTo, String content) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", config.mailHost);
        properties.setProperty("mail.smtp.port", config.mailPort);
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.mailFrom));
        message.addRecipients(Message.RecipientType.TO, mailTo);
        message.setSubject(config.mailSubject);
        message.setContent(content, config.mailContentType);
        Transport.send(message);
    }
}
