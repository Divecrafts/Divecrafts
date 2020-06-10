package io.clonalejandro.Essentials.commands;

import com.mysql.jdbc.Util;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class ItemRenameCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (checkPermissions(sender, SCmd.Rank.MEDUSA)) return true;

        if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR){
            final ItemStack item = player.getItemInHand();

            if (args.length == 1){
                final ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(Utils.colorize(args[0]));
                item.setItemMeta(meta);
            }
            else if (args.length > 1){
                final ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(Utils.colorize(args[0]));

                //Build list
                args[0] = "";
                List<String> msgs = new ArrayList<>();
                for (String msg : args) msgs.add(Utils.colorize(msg));

                meta.setLore(msgs);
                item.setItemMeta(meta);
            }
            else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/rename &e<name> &e<lore>"));
        }
        else player.sendMessage(Main.translate("&c&lServer> &fNo tienes ningún item en tu mano"));
        return true;
    }

}
