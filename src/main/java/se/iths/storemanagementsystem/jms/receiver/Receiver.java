package se.iths.storemanagementsystem.jms.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.iths.storemanagementsystem.jms.config.JmsConfig;
import se.iths.storemanagementsystem.jms.model.MessageObject;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class Receiver {

    @JmsListener(destination = JmsConfig.JU20DEC_QUEUE)
    public void listen(@Payload MessageObject messageObject) {
        System.out.println("I got a message");
        System.out.println(messageObject);

        sendEmail(messageObject.getEmail());
    }

    private void sendEmail(String email) {

        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "growlinggremlins47@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";


        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "greger123");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Hej från Team 10!");

            // Now set the actual message
            message.setText("Hej Ava. Vi skickar det här meddelandet från vår JMS-reciever...");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }


}
