package io.clonalejandro.DivecraftsCore.ex;

import io.clonalejandro.DivecraftsCore.kits.Kit;

public class SlotAlreadyInUseException extends Exception {

    public SlotAlreadyInUseException(Kit.ItemSlot slot) {
        super("The slot " + slot.name() + " is already in use");
    }
}
