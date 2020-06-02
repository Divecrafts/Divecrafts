package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.events.FlyHandler;
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

public class FlyCmd extends Cmd implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.MEDUSA)) return true;

        if (checkPermissions(sender, SCmd.Rank.TMOD) && FlyHandler.disabledWorlds.contains(((Player) sender).getWorld().getName().toLowerCase()))
            sender.sendMessage(Utils.colorize("&c&lServer> &fNo puedes volar en este mundo"));
        else if (args.length > 0){
            final Player player = Bukkit.getPlayer(args[0]);

            if (player == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[1])));
                return true;
            }

            goFly(player);
        }
        else goFly(Bukkit.getPlayer(sender.getName()));
        return true;
    }


    private void goFly(Player player){
        SServer.getUser(player.getUniqueId()).toggleFly();
        player.sendMessage(Main.translate(String.format("&9&lServer> &fModo fly %s", player.getAllowFlight() ? "&aactivado" : "&cdesactivado")));
    }
}
