package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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

        if (args.length == 0 || arg.equalsIgnoreCase("homes")) {
            List<String> names = new ArrayList<>();
            Objects.requireNonNull(getHomes(player)).forEach(home -> names.add(home.name));
            player.sendMessage(Main.translate(String.format("&9&lServer> &fTus homes: &e%s", String.join(", ", names))));
        }
        else {
            final List<Home> homes = getHomes(player);
            if (homes != null) {
                final AtomicReference<Home> home = new AtomicReference<>();
                homes.forEach(lHome -> {
                    if (lHome.getName().equalsIgnoreCase(args[0]))
                        home.set(lHome);
                });

                if (home.get() != null) {
                    final Location location = new Location(
                            home.get().getWorld(),
                            home.get().getX(),
                            home.get().getY(),
                            home.get().getZ(),
                            home.get().getYaw(),
                            home.get().getPitch()
                    );

                    player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al home: &e%s%s", args[0], user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : " &fespere &e5seg")));
                    new TeleportWithDelay(player, location);
                } else player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
            } else player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
        }
        return true;
    }

    private boolean setHome(CommandSender sender, String[] args) {
        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0) {
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
                final List<Home> homes = getHomes(player);

                if (homes == null) {
                    MysqlManager.getConnection().createStatement().executeUpdate(query);
                    player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha creado un home llamado: &e%s", args[0])));
                    return true;
                } else if (homes.size() >= 1 && homes.size() < 4) {
                    if (checkPermissions(player, SCmd.Rank.NEMO)) return true;
                } else if (homes.size() > 3 && homes.size() <= 10) {
                    if (checkPermissions(player, SCmd.Rank.KRAKEN)) return true;
                } else if (homes.size() > 10) {
                    if (checkPermissions(player, SCmd.Rank.POSEIDON)) return true;
                }
                MysqlManager.getConnection().createStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha creado un home llamado: &e%s", args[0])));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                player.sendMessage(Main.translate("&c&lServer> &falgo salió mal"));
            }
        } else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/sethome &e<jugador>"));

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

            try {
                MysqlManager.getConnection().createStatement().executeUpdate(query);
                player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha borrado el home &e%s", args[0])));
            } catch (SQLException throwables) {
                player.sendMessage(Main.translate("&c&lServer> &fEse home no existe"));
            }
        } else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/delhome &e<nombre>"));

        return true;
    }


    private List<Home> getHomes(Player player) {
        final String query = String.format("SELECT * FROM Homes WHERE uuid=\"%s\"", player.getUniqueId().toString());

        try {
            final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);
            final List<Home> homes = new ArrayList<>();

            while (result.next())
                homes.add(new Home(
                        result.getString("name"),
                        result.getString("world"),
                        result.getDouble("x"),
                        result.getDouble("y"),
                        result.getDouble("z"),
                        result.getFloat("yaw"),
                        result.getFloat("pitch")
                ));
            return homes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            player.sendMessage(Main.translate("&c&lServer> &falgo salió mal puede que no tengas homes guardados"));
        }

        return null;
    }


    static class Home {
        private final String name, world;
        private final double x, y, z;
        private final float yaw, pitch;

        public Home(String name, String world, double x, double y, double z, float yaw, float pitch) {
            this.name = name;
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public String getName() {
            return name;
        }

        public World getWorld() {
            return Bukkit.getWorld(this.world);
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }
    }
}
