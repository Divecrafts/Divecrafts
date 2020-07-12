package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 12/07/2020
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

public class RankupCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)){
            if (args.length > 1){
                final SUser user = SServer.getUser(Bukkit.getOfflinePlayer(args[0]));
                final SCmd.Rank rank = SCmd.Rank.values()[Integer.parseInt(args[1])];

                if (user.getUserData().getRank().getRank() < rank.getRank()){
                    final String prefix = rank.getRank() > 0 ? "&" + SCmd.Rank.groupColor(rank) + rank.getPrefix() + " " : "";

                    user.getUserData().setRank(rank);
                    user.save();

                    Utils.loadPermissions(user.getPlayer());
                    Utils.updateUserColor(user);

                    user.getPlayer().sendMessage(Utils.colorize("&9&lServer> &fTu rango se ha cambiado a " + prefix));
                }
            }
            else sender.sendMessage(Utils.colorize("&c&lServer> &fFormato incorrecto usa &b/rankup &e<usuario> <rango>"));
        }
        return true;
    }

}
