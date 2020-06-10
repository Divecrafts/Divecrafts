package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.commands.HeadCmd;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alex
 * On 09/06/2020
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

public class PlayerHandlers implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Entity entity = player.getKiller();

        String msg = "%player ha sido asesinado por %killer";

        msg = msg.replace("%player", player.getDisplayName());

        if (entity != null && entity instanceof Player)
            msg = msg.replace("%killer", ((Player) entity).getDisplayName());
        else if (entity != null) {
            msg = msg.replace("%killer", entity.getCustomName());
        }

        if (hasLucky(1, 1000) && entity instanceof Player){
            final ItemStack head = HeadCmd.createSkull(
                    Utils.colorize("&eHead of " + player.getDisplayName()),
                    player.getName(),
                    new ArrayList<>()
            );

            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), head);
        }

        event.setDeathMessage(Utils.colorize("&7" + msg));
    }

    public boolean hasLucky(int min, int max){
        final int secretNumber = 1;
        final int number = new Random().nextInt((max - min) + 1) - min;
        return number == secretNumber;
    }
}
