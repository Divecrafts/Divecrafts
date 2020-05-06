package io.clonalejandro.DivecraftsLobby.cosmetics.gadgets;

import lombok.Getter;
import lombok.Setter;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsLobby.cosmetics.Cosmetic;
import org.bukkit.scheduler.BukkitTask;

public abstract class Gadget extends Cosmetic {

    @Getter @Setter protected boolean enabled = false;

    @Getter protected BukkitTask bt;

    public Gadget(SCmd.Rank rank) {
        super(rank);
    }

    public abstract void onClick();

    protected boolean hasUser() {
        return getUser() != null;
    }
}
