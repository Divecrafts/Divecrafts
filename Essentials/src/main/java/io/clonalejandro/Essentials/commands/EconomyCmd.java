package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.utils.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.HashMap;

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
        if (checkPermissions(sender, SCmd.Rank.SMOD)) return true;
        else if (cmd.getName().equalsIgnoreCase("balancetop")) balanceTop(sender);
        else if (cmd.getName().equalsIgnoreCase("deposit")) deposit(sender, args);
        else if (cmd.getName().equalsIgnoreCase("withdraw")) withdraw(sender, args);
        else if (cmd.getName().equalsIgnoreCase("balance")) balance(sender, args);
        return true;
    }


    private void deposit(CommandSender sender, String[] args) {
        if (args.length > 1){
            Main.instance.economyProvider.depositPlayer(args[0], Double.parseDouble(args[1]));
            sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fAñadidos &b%s$ a la cuenta de &e%s", args[1], args[0])));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/deposit &e<player> <cantidad>"));
    }

    private void withdraw(CommandSender sender, String[] args) {
        if (args.length > 1){
            Main.instance.economyProvider.withdrawPlayer(args[0], Double.parseDouble(args[1]));
            sender.sendMessage(Utils.colorize(String.format("&a&lServer> &fEliminados &b%s$ de la cuenta de &e%s", args[1], args[0])));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/withdraw &e<player> <cantidad>"));
    }

    private void balance(CommandSender sender, String[] args){
        if (args.length > 0){
            double money = Main.instance.economyProvider.getBalance(args[0]);
            sender.sendMessage(Utils.colorize(String.format("&a&lServer> La cuenta de &e%s es de &b%s$", args[0], money)));
        }
        else sender.sendMessage(Utils.colorize("&c&lServer> &fformato incorrecto usa &b/balance &e<player>"));
    }

    private void balanceTop(CommandSender sender) {
        try {
            final HashMap<String, Economy> top = Economy.balanceTop(10);
            top.forEach((playerName, economy) -> {
                try {
                    sender.sendMessage(Utils.colorize(String.format("&e%s &fhas &b%s$", playerName, economy.balance())));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
