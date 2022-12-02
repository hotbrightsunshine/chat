package it.fi.meucci;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML Button leave;
    @FXML Button sendMess;
    @FXML ListView listaPart;
    @FXML ListView chat;
    @FXML TextField ip;
    @FXML TextField doveScrivi;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}