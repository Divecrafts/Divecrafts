package io.clonalejandro.Essentials.events;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.SpawnYml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

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

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerRespawnEvent event){
        final SpawnYml spawnYml = Main.spawnYml;

        final World world = Bukkit.getWorld(spawnYml.getString("Lobby.World"));
        final double x = spawnYml.getDouble("Lobby.x");
        final double y = spawnYml.getDouble("Lobby.y");
        final double z = spawnYml.getDouble("Lobby.z");
        final float yaw = spawnYml.getFloat("Lobby.yaw");
        final float pitch = spawnYml.getFloat("Lobby.pitch");

        final Location location = new Location(world, x , y, z, yaw, pitch);

        event.setRespawnLocation(location);
    }
}
