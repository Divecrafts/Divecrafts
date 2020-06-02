package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

/**
 * Created by Alex
 * On 02/06/2020
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

public class SpawnerCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.SMOD)) return true;
        if (args.length > 2){
            final Player player = Bukkit.getPlayer(args[0]);

            if (player != null){
                final String mob = args[1];
                final int amount = Integer.parseInt(args[2]);
                final ItemStack spawner = new ItemStack(Material.SPAWNER, amount);
                final BlockStateMeta stateMeta = (BlockStateMeta) spawner.getItemMeta();
                final CreatureSpawner creatureSpawner = (CreatureSpawner) stateMeta.getBlockState();

                creatureSpawner.setSpawnedType(EntityType.valueOf(mob.toUpperCase()));
                stateMeta.setBlockState(creatureSpawner);
                spawner.setItemMeta(stateMeta);
                player.getInventory().addItem(spawner);

                sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fHas dado &e%s &fspawner de tipo &e%s", amount, mob)));
            }
            else sender.sendMessage(Utils.colorize("&c&lServer> &fEl jugador ha de estar online"));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto &b/givespawner &e<usuario> <mob> <cantidad>"));
        return true;
    }
}
