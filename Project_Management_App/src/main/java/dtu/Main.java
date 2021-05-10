/*
	Made by Jakob Jacobsen s204502 
	This class runs the program
*/
package dtu;

import dtu.mvc.CommandController;

public class Main {
    
    public static void main(String[] args) {
    	CommandController commandController = new CommandController();
        commandController.start();
    }
}