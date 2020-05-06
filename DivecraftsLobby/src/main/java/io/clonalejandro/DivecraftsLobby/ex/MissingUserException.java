package io.clonalejandro.DivecraftsLobby.ex;

public class MissingUserException extends RuntimeException {

    public MissingUserException() {
        super("A cosmetic class has been loaded without an user");
    }
}
