package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 08/06/2020
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

public class EnderchestCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (checkPermissions(sender, SCmd.Rank.MEGALODON)) return true;

        if (args.length > 0){
            if (checkPermissions(sender, SCmd.Rank.MOD)) return true;
            final Player target = Bukkit.getPlayer(args[0]);

            if (target != null && target.isOnline())
                player.openInventory(target.getEnderChest());
            else player.sendMessage(Utils.colorize("&c&lServer> &fEl jugador ha de estar online"));
        }
        else player.openInventory(player.getEnderChest());

        return true;
    }


}
