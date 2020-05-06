package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Alex
 * On 30/04/2020
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
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class ChatHandler implements Listener {

    @EventHandler
    public void onWrite(AsyncPlayerChatEvent event){
        event.setFormat(Main.translate(String.format("%s&f: %s", event.getPlayer().getName(), event.getMessage())));
    }
}
