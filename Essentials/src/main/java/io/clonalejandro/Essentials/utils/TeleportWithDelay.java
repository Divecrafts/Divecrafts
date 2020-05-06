package io.clonalejandro.Essentials.utils;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.commands.BackCmd;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

public class TeleportWithDelay {

    public TeleportWithDelay(final Player player, final Location location, final String msg){
        addToLastLocation(player);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            player.sendMessage(Main.translate(msg));
            player.teleport(location);
        }, 20L * 5);
    }

    public TeleportWithDelay(final Player player, final Location location){
        addToLastLocation(player);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(location), 20L * 5);
    }

    public TeleportWithDelay(final Player player, final Location location, final int time){
        addToLastLocation(player);
        if (time > 0) Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(location), 20L * time);
        else player.teleport(location);
    }

    public TeleportWithDelay(final Player player, final Location location, final int time, final String msg, final boolean byPassTime){
        addToLastLocation(player);
        if (time > 0 && !byPassTime)
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                player.sendMessage(Main.translate(msg));
                player.teleport(location);
            }, 20L * time);
        else player.teleport(location);
    }

    private void addToLastLocation(final Player player){
        if (!BackCmd.lastLocation.containsKey(player))
            BackCmd.lastLocation.put(player, player.getLocation());
        else BackCmd.lastLocation.replace(player, player.getLocation());
    }
}
