package com.example.devdesktopfxtp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, MessagingException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client-mail-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 340);
        stage.setTitle("Client Mail");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}