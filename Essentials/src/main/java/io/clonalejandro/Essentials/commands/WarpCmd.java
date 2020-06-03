package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.guis.WarpGui;
import io.clonalejandro.Essentials.objects.Warp;
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

public class WarpCmd extends Cmd implements CommandExecutor {

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
        final List<Warp> warps = getWarps(player);
        final List<String> warpNames = new ArrayList<>();

        warps.forEach(warp -> warpNames.add(warp.getName()));
        player.sendMessage(Main.translate(String.format("&9&lServer> &fLa lista de warps es: &e%s", String.join(", ", warpNames))));
        return true;
    }

    private boolean setWarp(CommandSender sender, String[] args){
        if (checkPermissions(sender, SCmd.Rank.SMOD) && !sender.hasPermission("essentials.setwarp")) return true;

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
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/setwarp &e<nombre>"));

        return true;
    }

    private boolean warp(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());
        final SUser user = SServer.getUser(player.getUniqueId());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final String query = String.format("SELECT * FROM Warps WHERE name=\"%s\";", args[0]);

            try {
                final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);

                result.next();

                final Location location = new Warp(result).getLocation();

                player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al warp &e%s%s", args[0], user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : "  &fespere &e5seg")));
                new TeleportWithDelay(player, location);
            }
            catch (SQLException throwables){
                player.sendMessage(Main.translate("&c&lServer> &fEse warp no existe"));
            }
        }
        else new WarpGui(getWarps(player), player);

        return true;
    }

    private boolean delWarp(CommandSender sender, String[] args){
        if (checkPermissions(sender, SCmd.Rank.SMOD) && !sender.hasPermission("essentials.delwarp")) return true;

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

    private List<Warp> getWarps(Player player){
        final String query = "SELECT * FROM Warps";
        final List<Warp> warps = new ArrayList<>();

        try {
            final ResultSet result = MysqlManager.getConnection().createStatement().executeQuery(query);

            while (result.next())
                warps.add(new Warp(result));
        }
        catch (SQLException throwables){
            player.sendMessage(Main.translate("&c&lServer> &fNo hay warps guardados"));
        }

        return warps;
    }
}
