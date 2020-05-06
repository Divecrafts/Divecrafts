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

public class SpeedCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            if (Float.parseFloat(args[0]) > 10 || Float.parseFloat(args[0]) < 0){
                player.sendMessage(Main.translate("&c&lServer> &fLa velocidad ha de ser entre 0 y 1"));
                return true;
            }

            if (player.isFlying())
                player.setFlySpeed(Float.parseFloat(args[0]) / 10);
            else player.setWalkSpeed(Float.parseFloat(args[0]) / 10);

            player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha cambiado la velocidad a &e%s", args[0])));
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/speed &e<velocidad>"));

        return true;
    }
}
