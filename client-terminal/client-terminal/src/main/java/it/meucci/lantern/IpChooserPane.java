package it.meucci.lantern;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import it.meucci.App;
import it.meucci.Client;

import java.io.IOException;
import java.net.Inet4Address;

public class IpChooserPane {
    private static Panel root;
    private static TextBox ipText;
    private static Button button;
    private static Label ipNotCorrect;
    private static Runnable action = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println(ipText.getText());
                Inet4Address address = (Inet4Address) Inet4Address.getByName(ipText.getText());
                ipNotCorrect.setVisible(false);
                App.client = new Client(address, 7777);
                button.setEnabled(false);
            } catch (IOException e) {
                ipNotCorrect.setVisible(true);
            }

        }
    };

    public static void initialize(){


        root = new Panel();

        // Welcome Label
        Label welcomeLabel = new Label("Welcome to CazzoChat!");
        welcomeLabel.setPreferredSize(new TerminalSize(welcomeLabel.getText().length(), 2));
        welcomeLabel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                true,
                2,
                2
        ));
        welcomeLabel.addStyle(SGR.BOLD);
        welcomeLabel.setForegroundColor(TextColor.ANSI.BLACK);

        // Description Label
        Label description = new Label("This client allows you to communicate \nwith other users" +
                " connected to the same server as yours. \nPlease, insert a valid IP address here.");

        // Ip Not Correct Label
        ipNotCorrect = new Label("Unable to connect!");
        ipNotCorrect.setForegroundColor(TextColor.ANSI.RED);
        ipNotCorrect.setVisible(false);

        // Ip Text
        ipText = new TextBox("127.0.0.1");
        ipText.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.CENTER
        ));

        // Button
        button = new Button("Submit", action);
        button.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER
        ));

        // Setting the layour manager
        root.setLayoutManager(new GridLayout(1));

        // Adding items
        root.addComponent(welcomeLabel);
        root.addComponent(description);
        root.addComponent(ipNotCorrect);
        root.addComponent(ipText);
        root.addComponent(button);
    }

    public static Panel getRoot(){
        return root;
    }
}
