package com.example.examplechatgui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HelloController {
    @FXML
    public Button sendButton;

    @FXML
    public TextField textField;

    @FXML
    public ListView<Button> chats;

    @FXML
    public ListView<Label> messages;

    public void send(){
        Label l = new Label(textField.getText());
        messages.getItems().add(l);
    }
}