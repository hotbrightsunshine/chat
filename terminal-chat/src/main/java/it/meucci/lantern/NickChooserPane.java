package it.meucci.lantern;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import it.meucci.App;

public class NickChooserPane {
    static private TextBox nick;
    static private Button button;
    static private Panel root;
    static private boolean isUsernameOkay;
    static private boolean hasToWait;
    static private Label error;
    static private Runnable action = new Runnable() {
        @Override
        public void run() {
            // If user is valid
            error.setVisible(false);

            button.setEnabled(false);
            App.client.changeUsername(nick.getText());
            long now = System.currentTimeMillis();
            while(hasToWait==true){
                if((System.currentTimeMillis() - now) > 5000){
                    timeout();
                    break;
                }
            }
            if(isUsernameOkay){
                GraphicalInterface.chatSwitch();
            } else {

            }
        }
    };

    static public void timeout(){
        error.setForegroundColor(TextColor.ANSI.RED);
        error.setText("Timeout");
        error.setVisible(true);
    }

    static public void initialize(){
        isUsernameOkay = false;
        hasToWait = true;

        root = new Panel();
        root.setLayoutManager(new GridLayout(1));

        new Label("Please insert a new username.").addTo(root);

        error = new Label("");
        error.setVisible(false);
        error.addTo(root);

        nick = new TextBox(App.client.getUsername()).addTo(root);
        nick.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.CENTER
        ));

        button = new Button("Submit", action).addTo(root);
    }

    static public Panel getRoot(){
        return root;
    }

    static public void setIfUsernameOkay(boolean value){
        hasToWait = false;
        isUsernameOkay = value;
    }
}
