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

public class HomeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) return setHome(sender, args);
        else if (cmd.getName().equalsIgnoreCase("home")) return home(sender, args);
        else if (cmd.getName().equalsIgnoreCase("delhome")) return delHome(sender, args);
        return true;
    }

    private boolean home(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            final String query = String.format("SELECT * FROM Homes WHERE uuid=\"%s\" AND name=\"%s\"", player.getUniqueId().toString(), args[0]);

            try {
                final ResultSet result = MysqlManager.getStatement().executeQuery(query);

                result.next();

                final World world = Bukkit.getWorld(result.getString("world"));
                final double x = result.getDouble("x");
                final double y = result.getDouble("y");
                final double z = result.getDouble("z");
                final float yaw = result.getFloat("yaw");
                final float pitch = result.getFloat("pitch");

                final Location location = new Location(world, x, y, z, yaw, pitch);

                player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al home: &e%s &fespere &e5seg", args[0])));
                new TeleportWithDelay(player, location);
            }
            catch (SQLException throwables) {
                player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
            }
        }
        else {
            final String query = String.format("SELECT * FROM Homes WHERE uuid=\"%s\"", player.getUniqueId().toString());

            try {
                final ResultSet result = MysqlManager.getStatement().executeQuery(query);
                final List<String> homes = new ArrayList<>();

                while (result.next())
                    homes.add(result.getString("name"));

                player.sendMessage(Main.translate(String.format("&9&lServer> &fTus homes: &e%s", String.join(", ", homes))));
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                player.sendMessage(Main.translate("&c&lServer> &falgo salió mal puede que no tengas homes guardados"));
            }
        }

        return true;
    }

    private boolean setHome(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format(
                    "INSERT INTO Homes VALUES (NULL, \"%s\", \"%s\", \"%s\", %s, %s, %s, %s, %s);",
                    player.getUniqueId().toString(),
                    args[0],
                    player.getWorld().getName(),
                    player.getLocation().getX(),
                    player.getLocation().getY(),
                    player.getLocation().getZ(),
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch()
            );

            try {
                MysqlManager.getStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha creado un home llamado: &e%s", args[0])));
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                player.sendMessage(Main.translate("&c&lServer> &falgo salió mal"));
            }
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/sethome &e<jugador>"));

        return true;
    }

    private boolean delHome(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format(
                    "DELETE FROM Homes WHERE uuid=\"%s\" AND name=\"%s\";",
                    player.getUniqueId().toString(),
                    args[0]
            );

            try {
                MysqlManager.getStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha borrado el home &e%s", args[0])));
            }
            catch (SQLException throwables) {
                player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
            }
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/delhome &e<nombre>"));

        return true;
    }
}
