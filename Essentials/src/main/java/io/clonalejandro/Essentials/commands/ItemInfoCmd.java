package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * On 05/05/2020
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

public class ItemInfoCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR){
            final StringBuilder itemId = new StringBuilder();
            final String data = player.getItemInHand().getData().toString();

            itemId.append(player.getItemInHand().getTypeId());
            itemId.append(
                    String.format(":%s", data.charAt(
                            data.indexOf("(") + 1
                    ))
            );

            player.sendMessage(Main.translate(String.format("&9&lServer> &fLa id del item de tu mano es &e%s", itemId.toString())));
        }
        else player.sendMessage(Main.translate("&c&lServer> &fNo tienes ningún item en tu mano"));
        return true;
    }
}
