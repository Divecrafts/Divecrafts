package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Alex
 * On 12/06/2020
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

public class StackCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.MEDUSA) && !sender.hasPermission("essentials.stack")) return true;

        final Player player = Bukkit.getPlayer(sender.getName());
        final List<ItemStack> items = Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList());
        final List<Material> whiteList = Arrays.asList(Material.LAPIS_LAZULI, Material.DIAMOND, Material.REDSTONE,
                Material.GOLD_INGOT, Material.IRON_INGOT, Material.COAL,
                Material.EMERALD, Material.QUARTZ, Material.GLOWSTONE_DUST,
                Material.MELON_SLICE
        );
        
        for (ItemStack item : items){
            if (whiteList.contains(item.getType())){
                final int blocks = item.getType().equals(Material.GLOWSTONE_DUST) || item.getType().equals(Material.QUARTZ) ? item.getAmount() / 4 : item.getAmount() / 9;
                final int others = item.getType().equals(Material.GLOWSTONE_DUST) || item.getType().equals(Material.QUARTZ) ? item.getAmount() % 4 : item.getAmount() % 9;

                ItemStack block = null;

                switch (item.getType()){
                    case LAPIS_LAZULI:
                        block = new ItemStack(Material.LAPIS_BLOCK, blocks);
                        break;
                    case DIAMOND:
                        block = new ItemStack(Material.DIAMOND_BLOCK, blocks);
                        break;
                    case REDSTONE:
                        block = new ItemStack(Material.REDSTONE_BLOCK, blocks);
                        break;
                    case GOLD_INGOT:
                        block = new ItemStack(Material.GOLD_BLOCK, blocks);
                        break;
                    case IRON_INGOT:
                        block = new ItemStack(Material.IRON_BLOCK, blocks);
                        break;
                    case COAL:
                        block = new ItemStack(Material.COAL_BLOCK, blocks);
                        break;
                    case EMERALD:
                        block = new ItemStack(Material.EMERALD_BLOCK, blocks);
                        break;
                    case QUARTZ:
                        block = new ItemStack(Material.QUARTZ_BLOCK, blocks);
                        break;
                    case GLOWSTONE_DUST:
                        block = new ItemStack(Material.GLOWSTONE, blocks);
                        break;
                    case MELON_SLICE:
                        block = new ItemStack(Material.MELON, blocks);
                        break;
                }

                if (Arrays.stream(player.getInventory().getContents()).anyMatch(Objects::isNull))
                    return true;

                item.setAmount(others);
                player.getInventory().addItem(block);
            }
        }

        return true;
    }

}
