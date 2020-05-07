package io.clonalejandro.Essentials.commands;

import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.MysqlManager;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class WarpCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setwarp")) return setWarp(sender, args);
        else if (cmd.getName().equalsIgnoreCase("warps")) return warps(sender);
        else if (cmd.getName().equalsIgnoreCase("warp")) return warp(sender, args);
        else if (cmd.getName().equalsIgnoreCase("delwarp")) return delWarp(sender, args);
        return true;
    }

    private boolean warps(CommandSender sender){
        final Player player = Bukkit.getPlayer(sender.getName());
        final String query = "SELECT name FROM Warps;";

        try {
            final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);
            final List<String> warps = new ArrayList<>();

            while (result.next()){
                warps.add(result.getString("name"));
            }

            player.sendMessage(Main.translate(String.format("&9&lServer> &fLa lista de warps es: &e%s", String.join(", ", warps))));
        }
        catch (SQLException throwables){
            player.sendMessage(Main.translate("&c&lServer> &fNo hay warps guardados"));
        }

        return true;
    }

    private boolean setWarp(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format(
                    "INSERT INTO Warps VALUES (\"%s\", \"%s\", %s, %s, %s, %s, %s);",
                    args[0],
                    player.getWorld().getName(),
                    player.getLocation().getX(),
                    player.getLocation().getY(),
                    player.getLocation().getZ(),
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch()
            );

            try {
                MysqlManager.getConnection().createStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fHas creado el warp &e%s", args[0])));
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                player.sendMessage(Main.translate("&c&lServer> &falgo salió mal"));
            }
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/warp &e<nombre>"));

        return true;
    }

    private boolean warp(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format("SELECT * FROM Warps WHERE name=\"%s\";", args[0]);

            try {
                final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);

                result.next();

                final World world = Bukkit.getWorld(result.getString("world"));
                final double x = result.getDouble("x");
                final double y = result.getDouble("y");
                final double z = result.getDouble("z");
                final float yaw = result.getFloat("yaw");
                final float pitch = result.getFloat("pitch");

                final Location location = new Location(world, x, y, z, yaw, pitch);

                player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al warp &e%s &fespere &e5seg", args[0])));
                new TeleportWithDelay(player, location);
            }
            catch (SQLException throwables){
                player.sendMessage(Main.translate("&c&lServer> &fEse warp no existe"));
            }
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/warp &e<nombre>"));

        return true;
    }

    private boolean delWarp(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format("DELETE FROM Warps WHERE name=\"%s\";", args[0]);

            try {
                MysqlManager.getConnection().createStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha borrado el warp &e%s", args[0])));
            }
            catch (SQLException throwables) {
                player.sendMessage(Main.translate("&c&lServer> &fEse warp no existe"));
            }
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/delwarp &e<nombre>"));

        return true;
    }
}
