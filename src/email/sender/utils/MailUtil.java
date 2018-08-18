package email.sender.utils;

import email.sender.entity.MailConfigEntity;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

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

    public static void sendEmailImage(MailConfigEntity config, Address[] mailTo, String content) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", config.mailHost);
        properties.setProperty("mail.smtp.port", config.mailPort);

        Session session = Session.getDefaultInstance(properties);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(config.mailFrom));
        message.setRecipients(Message.RecipientType.TO, mailTo);
        message.setSubject(config.mailSubject);
        
        MimeMultipart multipart = new MimeMultipart("related");
        
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(content, config.mailContentType);
        multipart.addBodyPart(messageBodyPart);
        
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(config.mailImage);
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }
}
