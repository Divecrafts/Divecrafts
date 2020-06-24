package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.providers.EconomyProvider;
import io.clonalejandro.Essentials.utils.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alex
 * On 30/05/2020
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

public class EconomyCmd extends Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("balancetop")) balanceTop(sender);
        else if (cmd.getName().equalsIgnoreCase("deposit")) deposit(sender, args);
        else if (cmd.getName().equalsIgnoreCase("withdraw")) withdraw(sender, args);
        else if (cmd.getName().equalsIgnoreCase("balance")) balance(sender, args);
        else if (cmd.getName().equalsIgnoreCase("pay")) pay(sender, args);
        return true;
    }

    private void pay(CommandSender sender, String[] args){
        if (args.length > 1){
            final Player me = Bukkit.getPlayer(sender.getName());
            final Player player = Bukkit.getPlayer(args[0]);

            if (player != null){
                try {
                    final double amount = Double.parseDouble(args[1]);
                    EconomyProvider provider = Main.instance.economyProvider;

                    if (provider.has(me, amount)){
                        provider.withdrawPlayer(me, amount);
                        provider.depositPlayerWithoutBooster(player, amount);

                        player.sendMessage(Utils.colorize(String.format("&a&lServer> &fAñadidos &b%s$ &fa tu cuenta del jugador &e%s", amount, me.getName())));
                        me.sendMessage(Utils.colorize(String.format("&a&lServer> &fEliminados &b%s$ &fde tu cuenta", amount)));
                    }
                    else sender.sendMessage(Utils.colorize("&c&lServer> &fNo tienes suficiente dinero"));
                }
                catch (Exception ex) {
                    sender.sendMessage(Utils.colorize("&c&lServer> &fLa cantidad debe de ser decimal"));
                }
            }
            else sender.sendMessage(Utils.colorize("&c&lServer> &fEl jugador especificado ha de estar conectado"));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/pay &e<jugador> <cantidad>"));
    }

    private void deposit(CommandSender sender, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.SMOD)) return;
        if (args.length > 1){
            final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            Main.instance.economyProvider.depositPlayer(args[0], Double.parseDouble(args[1]));
            sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fAñadidos &b%s$ &fa la cuenta de &e%s", args[1], args[0])));

            if (player.getName() != null && player.isOnline()){
                ((Player) player).sendMessage(Utils.colorize(String.format("&a&lServer> &fAñadidos &b%s$ &fa tu cuenta", args[1])));
            }
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/deposit &e<jugador> <cantidad>"));
    }

    private void withdraw(CommandSender sender, String[] args) {
        if (checkPermissions(sender, SCmd.Rank.SMOD)) return;
        if (args.length > 1){
            final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            final double amount = Double.parseDouble(args[1]);
            final EconomyProvider provider = Main.instance.economyProvider;

            if (player.getName() != null){
                provider.withdrawPlayer(player.getName(), amount);
                sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fEliminados &b%s$ &fde la cuenta de &e%s", amount, player.getName())));
                if (player.isOnline())
                    ((Player) player).sendMessage(Utils.colorize(String.format("&a&lServer> &fEliminados &b%s$ &fde tu cuenta", amount)));
            }
            else {
                sender.sendMessage(Utils.colorize("&c&lServer> &fEl jugador especificado ha de estar conectado"));
            }
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/withdraw &e<jugador> <cantidad>"));
    }

    private void balance(CommandSender sender, String[] args){
        final EconomyProvider provider = Main.instance.economyProvider;
        final double money = provider.getBalance(args.length > 0 ? args[0] : sender.getName());
        sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fLa cuenta de &e%s &fes de &b%s$", args.length > 0 ? args[0] : sender.getName(), money)));
    }

    private void balanceTop(CommandSender sender) {
        try {
            final List<Economy> top = Economy.balanceTop(10);
            top.forEach((economy) -> sender.sendMessage(Utils.colorize(String.format("&e%s &fhas &b%s$", economy.getPlayer().getName(), economy.balance()))));
        }
        catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}
