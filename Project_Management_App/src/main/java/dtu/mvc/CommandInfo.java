/*
	Made by Aryan Mirzazadeh - s204489
	This class is used by the command controller to hold information for each command.
*/
package dtu.mvc;

interface Command {
	void runCommand();
}

public class CommandInfo {
    String info;
    Command command;

    public CommandInfo(String info, Command command) {
        this.info = info;
        this.command = command;
    }

    public String getInfo() {
        return info;
    }

    public Command getCommand() {
        return command;
    }
}