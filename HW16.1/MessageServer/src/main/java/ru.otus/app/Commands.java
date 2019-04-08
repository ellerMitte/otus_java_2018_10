package ru.otus.app;

/**
 * @author Igor on 08.04.19.
 */
public enum Commands {
    SAVE("SaveUser"),
    READ("GetUsers"),
    DELETE("DeleteUser");

    private final String commandClass;

    Commands(String commandClass) {
        this.commandClass = commandClass;
    }

    public String getCommandClass() {
        return commandClass;
    }
}
