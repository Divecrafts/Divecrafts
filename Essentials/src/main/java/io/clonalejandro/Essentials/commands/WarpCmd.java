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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        final List<Warp> warps = getWarps();
        final List<String> warpNames = new ArrayList<>();

        warps.forEach(warp -> warpNames.add(warp.getName()));
        player.sendMessage(Main.translate(String.format("&9&lServer> &fLa lista de warps es: &e%s", String.join(", ", warpNames))));
        return true;
    }

    private boolean setWarp(CommandSender sender, String[] args){
        if (checkPermissions(sender, SCmd.Rank.SMOD) && !sender.hasPermission("essentials.setwarp")) return true;

        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            final Runnable task = () -> {
                args[0] = MysqlManager.secureQuery(args[0]);
                final Warp warp = new Warp(args[0], player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                final String query = String.format(
                        "INSERT INTO Warps VALUES (\"%s\", \"%s\", %s, %s, %s, %s, %s);",
                        warp.getName(),
                        warp.getWorld().getName(),
                        warp.getX(),
                        warp.getY(),
                        warp.getZ(),
                        warp.getYaw(),
                        warp.getPitch()
                );

                try {
                    MysqlManager.getConnection().createStatement().executeUpdate(query);
                    player.sendMessage(Main.translate(String.format("&9&lServer> &fHas creado el warp &e%s", args[0])));
                    Warp.warpList.add(warp);
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                    player.sendMessage(Main.translate("&c&lServer> &falgo salió mal"));
                }
            };

            runTask(task);
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/setwarp &e<nombre>"));

        return true;
    }

    private boolean warp(CommandSender sender, String[] args){
        final Player player = Bukkit.getPlayer(sender.getName());
        final SUser user = SServer.getUser(player.getUniqueId());

        if (args.length > 0){
            args[0] = MysqlManager.secureQuery(args[0]);
            final List<Warp> warps = getWarps().stream()
                    .filter(w -> w.getName().equalsIgnoreCase(args[0]))
                    .collect(Collectors.toList());
            if (warps.size() != 0){
                final Warp warp = warps.get(0);
                final Location location = warp.getLocation();
                player.sendMessage(Main.translate(String.format("&9&lServer> &fTeletransportando al warp &e%s%s", args[0], user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : "  &fespere &e5seg")));
                new TeleportWithDelay(player, location);
            }
            else player.sendMessage(Main.translate("&c&lServer> &fEse warp no existe"));
        }
        else new WarpGui(getWarps(), player);
        return true;
    }

    private boolean delWarp(CommandSender sender, String[] args){
        if (checkPermissions(sender, SCmd.Rank.SMOD) && !sender.hasPermission("essentials.delwarp")) return true;

        final Player player = Bukkit.getPlayer(sender.getName());

        if (args.length > 0){
            final Runnable task = () -> {
                args[0] = MysqlManager.secureQuery(args[0]);
                final String query = String.format("DELETE FROM Warps WHERE name=\"%s\";", args[0]);

                try {
                    MysqlManager.getConnection().createStatement().executeUpdate(query);
                    player.sendMessage(Main.translate(String.format("&9&lServer> &fSe ha borrado el warp &e%s", args[0])));

                    if (Warp.warpList.size() != 0){
                        final List<Warp> warps = getWarps().stream()
                                .filter(w -> w.getName().equalsIgnoreCase(args[0]))
                                .collect(Collectors.toList());

                        if (warps.size() != 0)
                            Warp.warpList.remove(warps.get(0));
                    }
                }
                catch (SQLException throwables) {
                    player.sendMessage(Main.translate("&c&lServer> &fEse warp no existe"));
                }
            };

            runTask(task);
        }
        else player.sendMessage(Main.translate("&c&lServer> &fformato incorrecto usa &b/delwarp &e<nombre>"));

        return true;
    }

    private List<Warp> getWarps(){
        return Warp.warpList;
    }

    private void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.instance, runnable);
    }
}
