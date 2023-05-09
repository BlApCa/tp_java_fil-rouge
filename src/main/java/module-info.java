module com.example.devdesktopfxtp {
    requires javafx.fxml;
    requires javafx.web;
    requires java.mail;
    requires org.jsoup;

    opens com.example.devdesktopfxtp to javafx.fxml;
    exports com.example.devdesktopfxtp;
}