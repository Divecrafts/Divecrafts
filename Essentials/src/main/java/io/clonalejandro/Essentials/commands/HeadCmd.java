package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
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

public class HeadCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (args.length > 1){
            final Player player = Bukkit.getPlayer(sender.getName());
            final String name = args[0];
            final String owner = args[1];

            player.getInventory().addItem(createSkull(name, owner, new ArrayList<>()));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/givehead &e<nombre> <player-name>"));
        return true;
    }


    public static ItemStack createSkull(String displayName, String owner, List<String> lores){
        ItemStack i = new ItemStack(Material.LEGACY_SKULL_ITEM, 1 ,(byte)3);
        SkullMeta im = (SkullMeta) i.getItemMeta();
        im.setDisplayName(Utils.colorize(displayName));
        im.setOwner(owner);
        im.setLore(lores);
        i.setItemMeta(im);
        return i;
    }
}
