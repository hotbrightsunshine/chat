package it.fi.meucci;

import java.io.IOException;
import java.net.Inet4Address;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML Button connecButton;
    @FXML Button utilizzaUsername;
    @FXML TextField ipField;
    @FXML TextField usernameText;
    @FXML ListView partecipant;
    @FXML ListView messageView;
    
    
    private void switchToSecondary() throws IOException 
    {
        App.setRoot("secondary");
    }

    private void connetti() throws IOException
    {
        usernameText.setVisible(true);
        utilizzaUsername.setVisible(true);
        messageView.setVisible(true);
        App.c.
        
        
    }
}
