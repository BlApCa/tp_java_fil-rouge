package com.example.devdesktopfxtp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class HelloController {
    private ChangeListener listener;
    @FXML
    private WebView view;
    @FXML
    private TreeView<Object> listingEmails;
    @FXML
    private Menu refresh;
    @FXML
    private Label refresherLabel;


    @FXML
    public void initialize() throws MessagingException, IOException {
        TreeItem<Object> root = new TreeItem<>("Boite de reception");

        EmailManager emailManager = new EmailManager();
        List<Email> emailList = emailManager.readEmails();

        for (Email email : emailList) {
            root.getChildren().add(new TreeItem<>(email));
        }
        listingEmails.setRoot(root);
        root.setExpanded(true);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = dateFormat.format(cal.getTime()).toString();
        refresherLabel.setText("Dernière synchronisation : " + today);
        WebEngine webView = view.getEngine();
        String webContent = "<h1>HELLO</h1>";
        webView.loadContent(webContent);

        listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                TreeItem<Email> selectedItem = (TreeItem<Email>) newValue;
                if (selectedItem.getValue() instanceof Email) {
                    Email selectedEmail = (Email) selectedItem.getValue();
                    WebEngine webEngine = view.getEngine();
                    String sender = "Expéditeur : " + selectedEmail.getSender();
                    String recipient = "Destinataire : " + selectedEmail.getRecipient();
                    String date = "Envoyé le : " + selectedEmail.getDate();
                    String subject = "<h3>" + selectedEmail.getSubject() + "</h3>";
                    String body = selectedEmail.getBody();
                    String webContent = sender + "</br>" + recipient + "</br>" + date + subject + body;
                    webEngine.loadContent(webContent);
                }
            }
        };
        SelectionModel<TreeItem<Object>> sm = listingEmails.getSelectionModel();
        sm.selectedItemProperty().addListener(listener);
    }

    @FXML
    public void refresherAction() throws MessagingException, IOException {
        listingEmails.getSelectionModel().selectedItemProperty().removeListener(listener);
        initialize();
    }

    public void aboutAction() {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setTitle("À propos");
        dialog.setContentText("Vous utilisez actuellement le gestionnaire de mail du binôme Hugo & Fleming. Merci de votre soutiens, puisses votre utilisation être des plus agréable.");
        dialog.show();
    }

    public void sendMailAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("send-mail-view.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Sender Mail");
        stage.show();
    }
}
