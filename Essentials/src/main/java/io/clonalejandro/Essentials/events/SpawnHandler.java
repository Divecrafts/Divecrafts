package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.SpawnYml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * On 02/05/2020
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

public class SpawnHandler implements Listener {
    final ItemStack[] kit = new ItemStack[]{
            new ItemStack(Material.IRON_SWORD),
            new ItemStack(Material.IRON_AXE),
            new ItemStack(Material.IRON_PICKAXE),
            new ItemStack(Material.IRON_SHOVEL),
            new ItemStack(Material.IRON_HELMET),
            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_BOOTS),
            new ItemStack(Material.BREAD, 16)
    };

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        if (!event.getPlayer().hasPlayedBefore()){
            event.getPlayer().teleport(new Spawn().getLocation());
            event.getPlayer().getInventory().addItem(kit);
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event){
        event.setRespawnLocation(new Spawn().getLocation());
    }

    static class Spawn {
        private final SpawnYml spawnYml = Main.spawnYml;

        private final World world = Bukkit.getWorld(spawnYml.getString("Lobby.World"));
        private final double x = spawnYml.getDouble("Lobby.x");
        private final double y = spawnYml.getDouble("Lobby.y");
        private final double z = spawnYml.getDouble("Lobby.z");
        private final float yaw = spawnYml.getFloat("Lobby.yaw");
        private final float pitch = spawnYml.getFloat("Lobby.pitch");

        final Location location = new Location(world, x , y, z, yaw, pitch);

        public Location getLocation() {
            return location;
        }
    }
}
