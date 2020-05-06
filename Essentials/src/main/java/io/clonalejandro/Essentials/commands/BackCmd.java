package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

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

public class BackCmd implements CommandExecutor {

    public final static HashMap<Player, Location> lastLocation = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (lastLocation.containsKey(player)){
            final Location location = lastLocation.get(player);

            player.sendMessage(Main.translate("&9&lServer> &fTeletransportando a la última localización, espere &e5seg"));
            new TeleportWithDelay(player, location);
        }
        else player.sendMessage(Main.translate("&c&lServer> &fNo tienes localizaciones anteriores"));
        return true;
    }
}
