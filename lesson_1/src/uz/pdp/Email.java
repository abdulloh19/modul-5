package uz.pdp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        String username = "parij4320@gmail.com";
        String password = "dnbunrysnhxpoksy";
        Session session = getSession(properties, username, password);

        Message message = new MimeMessage(session);
        message.setSubject("My subject");
//        message.setText("oxshadimi ustoz");

        message.setContent("<h1 style=\"background-color: black; color: white\">white</h1>", "text/html");
        message.setFrom(new InternetAddress(username));
        String recevier = "398gxsofat@ozsaip.com";
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(recevier)});
        Transport.send(message);
        System.out.println("Message send successfully");
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
