package io.clonalejandro.DivecraftsCore.events;

import io.clonalejandro.DivecraftsCore.api.SUser;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Alex
 * On 18/06/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class UpdateNameEvent extends Event {

    @Getter private final String newName;
    @Getter private final SUser user;
    private static final HandlerList HANDLERS = new HandlerList();

    public UpdateNameEvent(String newName, SUser user) {
        this.newName = newName;
        this.user = user;
    }


    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
