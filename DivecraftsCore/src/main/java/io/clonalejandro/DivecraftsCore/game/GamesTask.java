package io.clonalejandro.DivecraftsCore.game;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class GamesTask extends BukkitRunnable {

    public abstract void run();

    /**
     * Abstract Method. Called it when a Runnable must finish
     */
    protected abstract void end();
}
