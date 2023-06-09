package com.example.devdesktopfxtp;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

public class ReadEmails {

    /**************************************************************
     * Méthode statique à utiliser pour récupérer le corps d'un email passé en paramètre
     * @param message
     * @return le corps du mail, dans une String
     * @throws MessagingException
     * @throws IOException
     **************************************************************/
    public static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    /*****************************************************
     * Méthode private, utilisée par getTextFromMessage
     * @param mimeMultipart
     * @return
     * @throws MessagingException
     * @throws IOException
     *****************************************************/
    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break; // without break same text appears twice in tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(org.jsoup.Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}
