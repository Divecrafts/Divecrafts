package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
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

public class TpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tp")) return tp(sender, args);
        else if (cmd.getName().equalsIgnoreCase("tphere")) return tphere(sender, args);
        else if (cmd.getName().equalsIgnoreCase("tpall")) return tpall(sender);
        return true;
    }


    private boolean tp(CommandSender sender, String[] args){
        if (args.length > 0) {
            final Player player = Bukkit.getPlayer(sender.getName());
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            new TeleportWithDelay(player, target.getLocation(), 0, "&9&lServer> &fTeletransportando...", false);
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/tp &e<jugador>"));
        return true;
    }

    private boolean tphere(CommandSender sender, String[] args){
        if (args.length > 0) {
            final Player player = Bukkit.getPlayer(sender.getName());
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            new TeleportWithDelay(target, player.getLocation(), 0);
            player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando el jugador &e%s &fa ti...", target.getName())));
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/tphere &e<jugador>"));
        return true;
    }

    private boolean tpall(CommandSender sender){
        final Player player = Bukkit.getPlayer(sender.getName());
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> new TeleportWithDelay(onlinePlayer, player.getLocation(), 0, "&9&lServer> &fTeletransportando...", false));

        return true;
    }
}
