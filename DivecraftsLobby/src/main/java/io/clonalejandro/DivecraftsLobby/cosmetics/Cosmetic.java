package io.clonalejandro.DivecraftsLobby.cosmetics;

import lombok.Getter;
import lombok.Setter;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsLobby.Main;
import io.clonalejandro.DivecraftsLobby.api.LobbyUser;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Cosmetic extends BukkitRunnable implements Listener {

    protected Main plugin = Main.getInstance();

    @Getter @Setter private LobbyUser user;
    @Getter private SCmd.Rank rank;

    public Cosmetic(SCmd.Rank rank) {
        this.rank = rank;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    protected abstract void start();
    protected abstract void stop();
    public abstract void run();

    public int getID() {
        return getCosmeticInfo().id();
    }
    public String getName() {
        return getCosmeticInfo().name();
    }
    public Material getMaterial() {
        return getCosmeticInfo().mat();
    }
    public CosmeticType getType() {
        return getCosmeticInfo().type();
    }
    private CosmeticInfo getCosmeticInfo() {
        return getClass().getAnnotationsByType(CosmeticInfo.class)[0];
    }

    public enum CosmeticType {
        GADGET, MOUNTS, UKNOWN
    }
}
