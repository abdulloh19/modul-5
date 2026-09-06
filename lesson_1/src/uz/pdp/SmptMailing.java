package uz.pdp;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SmptMailing {
    static void main(String[] args) throws MessagingException {
        Properties properties = getProperties();

        String username = "51deea4326154d";
        String password = "39f2b554daf32f";
        Session session = getSession(properties, username, password);

        Message message = new MimeMessage(session);
        message.setSubject("My subject");

        Multipart multipart = new MimeMultipart();
        BodyPart attachment = new MimeBodyPart();
        message.setContent("<h1 style=\"background-color: black; color: white\">white</h1>", "text/html");
        attachment.setFileName("cv.txt");
        attachment.setDataHandler(new DataHandler(new FileDataSource("cv.txt")));
        multipart.addBodyPart(attachment);
        message.setContent(multipart);

        message.setFrom(new InternetAddress(username));
        String recevier = "398gxsofat@ozsaip.com";
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(recevier)});
        Transport.send(message);
        System.out.println("Message send successfully");
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.starttls .enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    private static Session getSession(Properties properties, String username, String password) {
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
