package it.meucci.textinterface;

import java.util.ArrayList;

public class TextInterface {
    public static final int MIN_LINES = 10;
    private static Page current;
    private static ArrayList<ArrayList<String>> screens = new ArrayList<ArrayList<String>>();
    private static int screen_index;
    private static String error;
    // chat with the user; null if not serving anyone
    private static String serving;
    public static void start(){
        screen_index = 0;
        current = new WelcomePage();
        clearScreen();
        printCurrent();
    }

    public static void switchTo(Page newpage){
        error = "";
        current = newpage;
        refresh();
    }

    public static void refresh(){
        clearScreen();
        printCurrent();
    }

    public static void clearScreen() {
        for(int i = 0; i < 20; ++i){
            System.out.println();
        }
    }

    public static void printCurrent(){
        clearScreen();
        // Render screens
        renderScreens(current);
        // Render page
        print();

        // Enable input if necessary
        if(current.isTextInputEnabled()){
            System.out.print(" > ");
            TextInput.stop();
            TextInput.inputCycle();
        }
    }

    private static void print(){
        System.out.println(" + " + current.getHeader() + " (" + (screen_index+1) + "/" + screens.size() + ")");
        for(String s : screens.get(screen_index)){
            System.out.println(s);
        }
        if(error == null){
            System.out.println();
        } else {
            System.out.println(error);
        }

    }

    public static void nextScreen(){
        if (screen_index+1 >= screens.size()){
            error = "No more pages!";
            refresh();
        } else {
            screen_index++;
            refresh();
        }
    }

    public static void previousScreen(){
        if (screen_index-1 < 0){
            error = "No more pages!";
            refresh();
        } else {
            screen_index--;
            refresh();
        }
    }

    public static void renderScreens(Page p){
        ArrayList<String> content = p.getContent();
        screens = new ArrayList<>();
        int k = 0; int screen = -1;
        for(String i : content){
            if(k % 10 == 0){
                screen++;
            }
            try{
                screens.get(screen).add(i);
            }catch(Exception e){
                screens.add(screen, new ArrayList<>());
                screens.get(screen).add(i);
            }
            k = k + 1;
        }
    }
}
