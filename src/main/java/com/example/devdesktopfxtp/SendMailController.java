package com.example.devdesktopfxtp;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.mail.MessagingException;

public class SendMailController {
    @FXML
    private TextArea receiverEmail;
    @FXML
    private TextArea subjectEmail;
    @FXML
    private TextArea bodyEmail;

    @FXML
    public void sendMail() throws MessagingException {
        EmailManager emailManager = new EmailManager();
        emailManager.sendEmail(receiverEmail.getText(), subjectEmail.getText(), bodyEmail.getText());
        Stage stage = (Stage) receiverEmail.getScene().getWindow();
        stage.close();
    }
}
