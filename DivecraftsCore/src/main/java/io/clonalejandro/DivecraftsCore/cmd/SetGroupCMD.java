package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class SetGroupCMD extends SCmd {

    public SetGroupCMD() {
        super("setgroup", Rank.ADMIN, "darRank", "setrank", "rango");
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 0) {
            user.getPlayer().sendMessage(Main.getPREFIX() + "§6Tu Rango: §" + Rank.groupColor(user.getUserData().getRank()) + user.getUserData().getRank().toString());
            return;
        }

        if (args.length == 1) {
            int i;
            try {
                i = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                SUser target = SServer.getUser(plugin.getServer().getPlayer(args[0]));
                Rank g = target.getUserData().getRank();
                user.getPlayer().sendMessage(Main.getPREFIX() + "*§6El rango de §c" + target.getName() + " §6es §" + Rank.groupColor(g) + g.toString());
                return;
            }

            if (i > Rank.values().length - 1) {
                user.getPlayer().sendMessage(Main.getPREFIX() + "§cEste número es más grande de los rangos que hay");
                return;
            }

            user.getUserData().setRank(Rank.values()[i]);
            user.save();
            user.getPlayer().sendMessage(Main.getPREFIX() + "§3Rango cambiado: §" + Rank.groupColor(Rank.values()[i]) + Rank.values()[i].toString());
            return;
        }

        if (args.length == 2) {
            int i;
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                user.getPlayer().sendMessage(Main.getPREFIX() + "§cEl rango no es un número");
                return;
            }

            if (i > Rank.values().length - 1) {
                user.getPlayer().sendMessage(Main.getPREFIX() + "§cEste número es más grande de los rangos que hay");
                return;
            }
            SUser target = SServer.getUser(plugin.getServer().getPlayer(args[0]));

            if (target == null || !target.isOnline()) {
                userNotOnline(user);
                return;
            }

            target.getUserData().setRank(Rank.values()[i]);
            target.save();
            user.getPlayer().sendMessage(Main.getPREFIX() + "*§3Rango cambiado a §c" + target.getName() + " §3: §" + Rank.groupColor(Rank.values()[i]) + Rank.values()[i].toString());
        }
    }

    @Override
    public void run(ConsoleCommandSender sender, String label, String[] args) {
        if (args.length == 2) {
            int i;
            try {
                i = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(Main.getPREFIX() + "*§cEl rango no es un número");
                return;
            }

            if (i > Rank.values().length - 1) {
                sender.sendMessage(Main.getPREFIX() + "*§cEste número es más grande de los rangos que hay");
                return;
            }
            SUser target = SServer.getUser(plugin.getServer().getPlayer(args[0]));

            if (target == null || !target.isOnline()) {
                sender.sendMessage(Utils.colorize(Main.getPREFIX() + "*§cEl jugador no está conectado"));
                return;
            }

            target.getUserData().setRank(Rank.values()[i]);
            target.save();
            sender.sendMessage(Utils.colorize(Main.getPREFIX() + "*§3Rango cambiado a §c" + target.getName() + " §3: §" + Rank.groupColor(Rank.values()[i]) + target.getUserData().getRank().toString()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
