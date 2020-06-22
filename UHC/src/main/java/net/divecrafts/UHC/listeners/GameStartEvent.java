package net.divecrafts.UHC.listeners;

import net.divecrafts.UHC.minigame.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Alex
 * On 22/06/2020
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©StylusUHC 2017/2020
 */

public class GameStartEvent extends Event {

    private final Game game;
    private static final HandlerList HANDLERS = new HandlerList();

    public GameStartEvent(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
