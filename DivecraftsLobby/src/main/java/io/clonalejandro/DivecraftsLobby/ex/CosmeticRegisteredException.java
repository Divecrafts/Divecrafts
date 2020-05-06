package io.clonalejandro.DivecraftsLobby.ex;

import io.clonalejandro.DivecraftsLobby.cosmetics.Cosmetic;

public class CosmeticRegisteredException extends RuntimeException {

    public CosmeticRegisteredException(Cosmetic cosmetic) {
        super("The cosmetic " + cosmetic.getName() + " with the ID " + cosmetic.getID() + " is already registered");
    }
}
