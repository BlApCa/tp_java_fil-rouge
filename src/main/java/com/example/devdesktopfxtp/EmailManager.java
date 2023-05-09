package com.example.devdesktopfxtp;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmailManager {
    private final String username;
    private final String password;

    public EmailManager() {
        this.username = "javamailynovlh@gmail.com";
        this.password = "ybgpbpczimhqfbhf";
    }

    protected void sendEmail(String destinataire, String sujet, String message) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(
                prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message mail = new MimeMessage(session);
        mail.setFrom(new InternetAddress(username));
        mail.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destinataire)
        );
        mail.setSubject(sujet);
        mail.setText(message);

        Transport.send(mail);
    }

    protected List<Email> readEmails() throws MessagingException, IOException {
        Properties prop = new Properties();
        prop.put("mail.imap.host", "imap.gmail.com");
        prop.put("mail.imap.port", "993");
        prop.put("mail.imap.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(prop);

        Store store = emailSession.getStore("imaps");
        store.connect("imap.gmail.com", username, password);
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        List<Message> messages = new ArrayList<>(Arrays.asList(emailFolder.getMessages()));
        List<Email> emails = new ArrayList<>();

        for (Message message : messages) {
            Email email = new Email(((InternetAddress)(message.getFrom()[0])).getAddress(), message.getSubject(), ReadEmails.getTextFromMessage(message), message.getSentDate());
            emails.add(email);
        }

        return emails;
    }

    public static void main(String[] args) throws MessagingException, IOException {
        EmailManager emailManager = new EmailManager();

        emailManager.sendEmail(
                "hugo.lanzafame@ynov.com",
                "Test JavaMail",
                "Bonjour,\n\nVoila un petit test pour le JavaMailer.\n\nCordialement,\nJava Mail Ynov LH"
        );

        List<Email> emails = emailManager.readEmails();

        System.out.println(emails.get(0).getBody());
    }
}
