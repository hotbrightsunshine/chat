package it.meucci.lantern;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

public class GraphicalInterface {
    static private DefaultTerminalFactory d = new DefaultTerminalFactory();
    static private Terminal terminal;
    static private Window window;

    static public void start() throws IOException {
        terminal = d.createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        window = new BasicWindow();



        chooseIp();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }
    public static void chooseIp(){
        IpChooserPane.initialize();
        window.setComponent(IpChooserPane.getRoot());
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
    }

    public static void chooseNick(){
        NickChooserPane.initialize();
        window.setComponent(NickChooserPane.getRoot());
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
    }

    public static void chatSwitch(){
        ChatSwitchPane.initialize();
        window.setComponent(ChatSwitchPane.getRoot());
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
    }
}
