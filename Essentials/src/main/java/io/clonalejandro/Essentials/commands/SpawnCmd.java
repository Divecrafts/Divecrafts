package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.SpawnYml;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

public class SpawnCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        final SpawnYml spawnYml = Main.spawnYml;
        final Player player = Bukkit.getPlayer(sender.getName());

        if (cmd.getName().equalsIgnoreCase("setspawn")) return setSpawn(spawnYml, player);
        else if (cmd.getName().equalsIgnoreCase("spawn")) return spawn(spawnYml, player);

        return true;
    }

    private boolean spawn(SpawnYml spawnYml, Player player){
        final World world = Bukkit.getWorld(spawnYml.getString("Lobby.World"));
        final double x = spawnYml.getDouble("Lobby.x");
        final double y = spawnYml.getDouble("Lobby.y");
        final double z = spawnYml.getDouble("Lobby.z");
        final float yaw = spawnYml.getFloat("Lobby.yaw");
        final float pitch = spawnYml.getFloat("Lobby.pitch");

        final Location location = new Location(world, x , y, z, yaw, pitch);
        final SUser user = SServer.getUser(player.getUniqueId());

        player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al spawn%s", user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : ", espere &e5seg")));
        new TeleportWithDelay(player, location);

        return true;
    }

    private boolean setSpawn(SpawnYml spawnYml, Player player){
        final String world = player.getWorld().getName();
        final double x = player.getLocation().getX();
        final double y = player.getLocation().getY();
        final double z = player.getLocation().getZ();
        final float yaw = player.getLocation().getYaw();
        final float pitch = player.getLocation().getPitch();

        spawnYml.set("Lobby.World", world);
        spawnYml.set("Lobby.x", x);
        spawnYml.set("Lobby.y", y);
        spawnYml.set("Lobby.z", z);
        spawnYml.set("Lobby.yaw", yaw);
        spawnYml.set("Lobby.pitch", pitch);

        player.sendMessage(Main.translate("&a&lServer> &fSpawn establecida correctamente!"));
        return true;
    }
}
