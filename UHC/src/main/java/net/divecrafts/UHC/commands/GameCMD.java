package net.divecrafts.UHC.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.Lobby;
import net.divecrafts.UHC.task.GameCountDown;
import net.divecrafts.UHC.utils.Api;

/**
 * Created by alejandrorioscalera
 * On 17/1/18
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
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2018
 */

public class GameCMD implements CommandExecutor {


    /** SMALL CONSTRUCTORS **/

    //none...


    /** REST **/

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){

        if (cmd.getName().equalsIgnoreCase("uhc")){
            if (sender.hasPermission("game.admin")){
                if (args.length < 1) listCommands(sender);

                else if (args[0].equalsIgnoreCase("forcestart"))
                    forceStart(sender);

                else if (args[0].equalsIgnoreCase("sethost"))
                    setHost(args, sender);

                else if (args[0].equalsIgnoreCase("setlobby"))
                    setLobby(sender);

                else if (args[0].equalsIgnoreCase("help"))
                    listCommands(sender);

                else notExists(sender);
            }
            else notPermissions(sender);
        }
        return true;
    }


    /** OTHERS **/

    /**
     * This function force start to arena uhc
     */
    private void forceStart(CommandSender sender){
        if (GameCountDown.isRunning){
            sender.sendMessage(Api.translator("&c&lUHC> &fThe game countdown is running"));
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p -> {
            final SUser user = SServer.getUser(p);
            p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.force"));
        });

        new GameCountDown(Main.instance, true).runTaskTimer(Main.instance, 1L, 20L);
    }


    /**
     * This function set host
     * @param args
     * @param sender
     */
    private void setHost(String[] args, CommandSender sender){
        if (args[1] == null)
            sender.sendMessage(Api.translator("&c&lUHC> &fIncorrect the format is: &b/uhc setHost &e<username>"));
        else {
            Api.setHost(args[1]);

            for (Player player : Api.getOnlinePlayers())
                player.getScoreboard().getTeam("host").setSuffix(
                        Api.translator("&f" + Api.getHost())
                );

            sender.sendMessage(Api.translator(
                    "&a&lUHC> &fThe &eHost &fhas been setted as &e" + Api.getHost()
            ));
        }//When argument isn't null
    }


    /**
     * This function send help text to player while command or argument return error
     * @param sender
     */
    private void notExists(CommandSender sender){
        sender.sendMessage(Api.translator(
                "&c&lUHC> &fThis command not exists please use &b/uhc help"
        ));//When not exists
    }


    /**
     * This function send message while player dont have permissions
     * @param sender
     */
    private void notPermissions(CommandSender sender){
        sender.sendMessage(Api.translator(
                "&c&lUHC> &fYou dont have permissions for this"
        ));//When dont have permissions
    }


    /**
     * This function set a game Lobby and save this
     * @param sender
     */
    private void setLobby(CommandSender sender){
        final Player player = Bukkit.getPlayer(sender.getName());
        new Lobby().setLobby(player.getLocation());
        sender.sendMessage(Api.translator("&a&lUHC> &fLobby setted and saved the new location"));
    }


    /**
     * This function send a list of all commands to this plugin
     * @param sender
     */
    private void listCommands(CommandSender sender){
        sender.sendMessage(Api.translator("&f** &d&lUHC &f**"));
        sender.sendMessage(Api.translator("&aPlugin developed by: &eclonalejandro"));
        sender.sendMessage(Api.translator("&aPlugin version: &e1.0"));
        sender.sendMessage(Api.translator("&b/uhc forcestart &f: Force start the game uhc"));
        sender.sendMessage(Api.translator("&b/uhc sethost &e<username> &f: Update or set the game host"));
        sender.sendMessage(Api.translator("&b/uhc setlobby &f: Set the game lobby in your current location"));
    }


}
