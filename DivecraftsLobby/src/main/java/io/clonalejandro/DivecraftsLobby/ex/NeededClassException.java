package io.clonalejandro.DivecraftsLobby.ex;

public class NeededClassException extends RuntimeException {

    public NeededClassException(String className) {
        super("This class needs " + className + " class to work");
    }
}
