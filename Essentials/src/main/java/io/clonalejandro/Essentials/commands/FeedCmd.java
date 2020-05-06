package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

public class FeedCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (args.length > 0){
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            target.setFoodLevel(20);
            sender.sendMessage(Main.translate(String.format("&9&lServer> &fHas quitado el hambre a &e%s", target.getName())));
        }
        else {
            final Player player = Bukkit.getPlayer(sender.getName());
            player.setFoodLevel(20);
            sender.sendMessage(Main.translate("&9&lServer> &fTe has quitado el hambre"));
        }
        return true;
    }
}
