package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SBooster;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alex
 * On 22/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class GiveBoosterCMD extends SCmd {

    public GiveBoosterCMD() {
        super("givebooster", Rank.ADMIN, "giveboost");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
       if (args.length > 4){
           SUser target;

           if (plugin.getServer().getPlayer(args[0]) == null) {
               try {
                   final PreparedStatement statement =  Main.getInstance().getMySQL().openConnection().prepareStatement("SELECT `uuid` FROM data WHERE `name` = ?");

                   statement.setString(1, args[0]);
                   final ResultSet rs = statement.executeQuery();

                   if (rs.next()) {
                       final String uuidStr = rs.getString("uuid");
                       target = SServer.getUser(UUID.fromString(uuidStr));
                   }
                   else throw new SQLException();
               }
               catch (SQLException ex) {
                   user.getPlayer().sendMessage(Utils.colorize(Main.getPREFIX() + "§cEl jugador no existe"));
                   return;
               }
           }
           else target = SServer.getUser(plugin.getServer().getPlayer(args[0]));
           try {
               plugin.getMySQL().addBooster(target, new SBooster(0, Integer.parseInt(args[1]), SServer.GameID.values()[Integer.parseInt(args[2])], Timestamp.valueOf(args[3] + " " + args[4]), target.getUuid()));
               user.getPlayer().sendMessage(Utils.colorize(Main.getPREFIX() + "&fBooster añadido"));
           }
           catch (SQLException ex) {
               ex.printStackTrace();
           }
       }
       else user.getPlayer().sendMessage(Utils.colorize(Main.getPREFIX() + "&cUso incorrecto usa: &b/givebooster &e<jugador> <multiplier> <gameId> <Y-M-d> <h:m:s>"));
    }

    @Override
    public void run(ConsoleCommandSender sender, String label, String[] args) {
        if (args.length > 4){
            SUser target;

            if (plugin.getServer().getPlayer(args[0]) == null) {
                try {
                    final PreparedStatement statement =  Main.getInstance().getMySQL().openConnection().prepareStatement("SELECT `uuid` FROM data WHERE `name` = ?");

                    statement.setString(1, args[0]);
                    final ResultSet rs = statement.executeQuery();

                    if (rs.next()) {
                        final String uuidStr = rs.getString("uuid");
                        target = SServer.getUser(UUID.fromString(uuidStr));
                    }
                    else throw new SQLException();
                }
                catch (SQLException ex) {
                   sender.sendMessage(Utils.colorize(Main.getPREFIX() + "§cEl jugador no existe"));
                    return;
                }
            }
            else target = SServer.getUser(plugin.getServer().getPlayer(args[0]));
            try {
                plugin.getMySQL().addBooster(target, new SBooster(0, Integer.parseInt(args[1]), SServer.GameID.values()[Integer.parseInt(args[2])], Timestamp.valueOf(args[3] + " " + args[4]), target.getUuid()));
                sender.sendMessage(Utils.colorize(Main.getPREFIX() + "&fBooster añadido"));
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else sender.sendMessage(Utils.colorize(Main.getPREFIX() + "&cUso incorrecto usa: &b/givebooster &e<jugador> <multiplier> <gameId> <Y-M-d> <h:m:s>"));
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}