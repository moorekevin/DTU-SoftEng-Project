package dtu;

import dtu.mvc.CommandController;

public class main {
    
    public static void main(String[] args) {
        CommandController commandController = new CommandController();
        commandController.start();
    }
}