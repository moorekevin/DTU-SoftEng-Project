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