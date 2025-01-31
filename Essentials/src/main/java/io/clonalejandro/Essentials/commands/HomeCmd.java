package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.objects.Home;
import io.clonalejandro.Essentials.utils.MysqlManager;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

public class HomeCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sethome")) return setHome(sender, args);
        else if (cmd.getName().equalsIgnoreCase("home")) return home(sender, arg, args);
        else if (cmd.getName().equalsIgnoreCase("delhome")) return delHome(sender, args);
        return true;
    }

    private boolean home(CommandSender sender, String arg, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());
        final SUser user = SServer.getUser(player.getUniqueId());
        List<Home> homes = getHomes(player);

        if (arg.equalsIgnoreCase("homes") && args.length > 0)
            return homes(sender, arg, args);

        if (args.length == 0 || arg.equalsIgnoreCase("homes")) {
            List<String> names = new ArrayList<>();
            if (homes != null)
                homes.forEach(home -> names.add(home.getName()));
            player.sendMessage(Main.translate(String.format("&9&lServer> &fTus homes: &e%s", String.join(", ", names))));
        }
        else {
            homes = homes != null ?
                    homes.stream()
                            .filter(h -> h.getName().equalsIgnoreCase(args[0]))
                            .collect(Collectors.toList()) :
                    new ArrayList<>();

            if (homes.size() == 0) {
                player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
            } else if (homes.get(0) != null) {
                final Home home = homes.get(0);
                new TeleportWithDelay(player, home.getLocation());

                player.sendMessage(Main.translate(String.format(
                        "&9&lServer> &fTeletransportando al home: &e%s%s",
                        home.getName(),
                        user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : " &fespere &e5seg"
                )));
            } else player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
        }
        return true;
    }

    private boolean setHome(CommandSender sender, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            final Home home = new Home(args[0], player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());

            final String query = String.format(
                    "INSERT INTO Homes VALUES (NULL, \"%s\", \"%s\", \"%s\", %s, %s, %s, %s, %s);",
                    player.getUniqueId().toString(),
                    home.getName(),
                    home.getWorld().getName(),
                    home.getX(),
                    home.getY(),
                    home.getZ(),
                    home.getYaw(),
                    home.getPitch()
            );

            final List<Home> homes = getHomes(player);

            if (homes != null && homes.contains(args[0])){
                player.sendMessage(Utils.colorize("&c&lServer> &fYa tienes un home registrado con ese nombre"));
                return true;
            }

            final Runnable set = () -> {
                try {
                    MysqlManager.getConnection().createStatement().executeUpdate(query);
                    player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha creado un home llamado: &e%s", args[0])));

                    if (Home.homes.get(player) != null)
                        Home.homes.get(player).add(home);
                    else if (homes != null) {
                        homes.add(home);
                        Home.homes.put(player, homes);
                    }
                    else {
                        List<Home> list = new ArrayList<>();
                        list.add(home);
                        Home.homes.put(player, list);
                    }
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                    player.sendMessage(Main.translate("&c&lServer> &falgo salió mal"));
                }
            };

            if (homes == null) {
                runTask(set);
                return true;
            }
            else if (homes.size() >= 1 && homes.size() < 4) {
                if (checkPermissionsWithoutMessage(player, SCmd.Rank.NEMO) && !player.hasPermission("essentials.homes.nemo"))
                    return sendErrMessage(player);
            }
            else if (homes.size() > 3 && homes.size() <= 10) {
                if (checkPermissionsWithoutMessage(player, SCmd.Rank.KRAKEN) && !player.hasPermission("essentials.homes.nemo") && !player.hasPermission("essentials.homes.kraken"))
                    return sendErrMessage(player);
            }
            else if (homes.size() > 10 && homes.size() <= 20) {
                if (checkPermissionsWithoutMessage(player, SCmd.Rank.POSEIDON))
                    return sendErrMessage(player);
            }
            runTask(set);
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/sethome &e<nombre>"));

        return true;
    }

    private boolean delHome(CommandSender sender, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0) {
            args[0] = MysqlManager.secureQuery(args[0]);

            final String query = String.format(
                    "DELETE FROM Homes WHERE uuid=\"%s\" AND name=\"%s\";",
                    player.getUniqueId().toString(),
                    args[0]
            );

            final List<Home> homes = getHomes(player);
            final Runnable delete = () -> {
                try {
                    MysqlManager.getConnection().createStatement().executeUpdate(query);
                    player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha borrado el home &e%s", args[0])));

                    if (Home.homes.get(player) != null && homes != null && homes.size() != 0) {
                        final Home home = homes.stream()
                                .filter(h -> h.getName().equalsIgnoreCase(args[0]))
                                .collect(Collectors.toList())
                                .get(0);

                        if (home != null) Home.homes.get(player).remove(home);
                    }
                }
                catch (SQLException throwables) {
                    player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
                }
            };

            runTask(delete);
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/delhome &e<nombre>"));

        return true;
    }

    private boolean homes(CommandSender sender, String arg, String[] args){
        try {
            if (checkPermissions(sender, SCmd.Rank.TMOD)) return true;

            if (args.length > 1){
                final Player player = Bukkit.getPlayer(sender.getName());
                final SUser user = SServer.getUser(player);
                final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("SELECT * FROM Homes WHERE uuid=?");
                statement.setString(1, Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());

                final ResultSet rs = statement.executeQuery();

                rs.next();

                final Home home = new Home(
                        rs.getString("name"),
                        rs.getString("world"),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")
                );

                new TeleportWithDelay(player, home.getLocation());

                player.sendMessage(Main.translate(String.format(
                        "&9&lServer> &fTeletransportando al home: &e%s%s",
                        home.getName(),
                        user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : " &fespere &e5seg"
                )));
            }
            else {
                final PreparedStatement statement = MysqlManager.getConnection().prepareStatement("SELECT `name` FROM Homes WHERE uuid=?");
                statement.setString(1, Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());

                final ResultSet rs = statement.executeQuery();
                final List<String> homes = new ArrayList<>();

                while (rs.next()) homes.add(rs.getString("name"));

                sender.sendMessage(Utils.colorize(String.format("&9&lServer> &fLa lista de homes del usuario &e%s &fes: &e%s", args[0], String.join(", ", homes))));
            }
        }
        catch (Exception ex){
            sender.sendMessage(Utils.colorize("&c&lServer> &fEse usuario no tiene homes registradas o la home especificada no existe"));
        }
        return true;
    }

    private List<Home> getHomes(Player player) {
        if (Home.homes.get(player) != null) return Home.homes.get(player);
        else return null;
    }

    private void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.instance, runnable);
    }
}
