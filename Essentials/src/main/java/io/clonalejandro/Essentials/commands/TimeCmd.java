package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 01/05/2020
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

public class TimeCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.MOD)) return true;

        if (args.length > 0){
            final Player player = Bukkit.getPlayer(sender.getName());
            final World world = player.getWorld();

            switch (args[0].toLowerCase()){
                case "day":
                case "dia":
                    world.setTime(1000);
                    break;
                case "night":
                case "noche":
                    world.setTime(13000);
                    break;
                case "afternoon":
                case "tarde":
                    world.setTime(6000);
                    break;
            }

            sender.sendMessage(Main.translate(String.format("&9&lServer> &fel tiempo se ha cambiado a &e%s", args[0])));
        }
        else sender.sendMessage(Main.translate(String.format("&c&lServer> &fformato incorrecto usa &b/time &e<day:night:afternoon>")));
        return true;
    }
}
