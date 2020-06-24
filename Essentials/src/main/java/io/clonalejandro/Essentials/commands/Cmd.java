package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 28/05/2020
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

public class Cmd {

    public boolean checkPermissions(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());

        if (user.getUserData().getRank().getRank() < rank.getRank())
            return sendErrMessage(player);

        return false;
    }

    public boolean checkPermissionsWithoutMessage(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());
        return user.getUserData().getRank().getRank() < rank.getRank();
    }

    public boolean checkPermissionsWithoutMessage(CommandSender sender, SCmd.Rank rank){
        if  (sender instanceof Player) return checkPermissionsWithoutMessage((Player) sender, rank);
        return false;
    }

    public boolean checkPermissions(CommandSender sender, SCmd.Rank rank){
        if  (sender instanceof Player) return checkPermissions((Player) sender, rank);
        return false;
    }

    public boolean sendErrMessage(CommandSender sender){
        sender.sendMessage(Utils.colorize("&c&lServer> &fNo tienes permisos para hacer eso!"));
        return true;
    }

    public boolean sendErrMessage(Player player){
        player.sendMessage(Utils.colorize("&c&lServer> &fNo tienes permisos para hacer eso!"));
        return true;
    }
}
