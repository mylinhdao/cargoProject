package ui;

import api.Beobachter;

public class ConsoleView {
    public synchronized void showOutput(String output){
        System.out.println(output);
    }


}
