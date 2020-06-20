package io.clonalejandro.Essentials.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.SpawnYml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

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
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        final SUser user = SServer.getUser(event.getPlayer());

        if (user.getUserData().getRank().getRank() < SCmd.Rank.NEMO.getRank())
            event.setJoinMessage(null);
        if (!event.getPlayer().hasPlayedBefore())
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> event.getPlayer().teleport(new Spawn().getLocation()), 5L);
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event){
        event.setLeaveMessage(null);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event){
        event.setQuitMessage(null);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event){
        final Player player = event.getPlayer();
        event.setRespawnLocation(new Spawn().getLocation());

        //If is fly
        if (SServer.getUser(player).getUserData().getFly()){
            player.setAllowFlight(true);
            player.setFlying(true);
        }
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
