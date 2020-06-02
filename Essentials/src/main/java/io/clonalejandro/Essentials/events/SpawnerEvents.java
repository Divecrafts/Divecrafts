package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.commands.Cmd;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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

public class SpawnerEvents implements Listener {

    @EventHandler
    public void onSpawnerBreakEvent(BlockBreakEvent event){
        if (event.getBlock().getType() == Material.SPAWNER){
            final Location location = event.getBlock().getLocation();
            final ItemStack item = new ItemStack(Material.SPAWNER, 1);
            final CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
            final BlockStateMeta stateMeta = (BlockStateMeta) item.getItemMeta();
            final EntityType entityType = spawner.getSpawnedType();

            spawner.setSpawnedType(entityType);
            stateMeta.setBlockState(spawner);
            item.setItemMeta(stateMeta);

            //if (checkPermissions(event.getPlayer(), SCmd.Rank.POSEIDON))
                location.getWorld().dropItemNaturally(location, item);
        }
    }

    public boolean checkPermissions(Player player, SCmd.Rank rank){
        final SUser user = SServer.getUser(player.getUniqueId());
        return user.getUserData().getRank().getRank() < rank.getRank();
    }
}
