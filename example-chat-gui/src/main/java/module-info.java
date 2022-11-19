module com.example.examplechatgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.examplechatgui to javafx.fxml;
    exports com.example.examplechatgui;
}