package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Alex
 * On 02/05/2020
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

public class SignHandler implements Listener {

    @EventHandler
    public void onSign(SignChangeEvent event){
        for(int i = 0; i<4; i++){
            event.setLine(i, Main.translate(event.getLine(i)));
        }
    }
}
