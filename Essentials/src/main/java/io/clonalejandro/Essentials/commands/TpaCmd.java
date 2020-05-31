package io.clonalejandro.Essentials.commands;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.Essentials.Main;
import io.clonalejandro.Essentials.tasks.TpaTask;
import io.clonalejandro.Essentials.utils.TeleportWithDelay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex
 * On 01/05/2020
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

public class TpaCmd implements CommandExecutor {

    public final static HashMap<Player, Player> tpaUsers = new HashMap<>();
    public final static HashMap<Player, Type> tpaType = new HashMap<>();

    private final Main instance;

    enum Type {TPA, TPAHERE}

    public TpaCmd(Main main){
        instance = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tpa")) return tpa(sender, args);
        else if (cmd.getName().equalsIgnoreCase("tpaall")) return tpaAll(sender);
        else if (cmd.getName().equalsIgnoreCase("tpaccept")) return tpaAccept(sender);
        else if (cmd.getName().equalsIgnoreCase("tpdeny")) return tpaDeny(sender);
        else if (cmd.getName().equalsIgnoreCase("tpahere")) return tpaHere(sender, args);
        return true;
    }


    private boolean tpa(CommandSender sender, String[] args){
        if (args.length > 0){
            final Player target = Bukkit.getPlayer(args[0]);
            final Player player = Bukkit.getPlayer(sender.getName());

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            player.sendMessage(Main.translate(String.format("&9&lServer> &fSolicitud de teletransporte enviada al jugador &e%s", target.getName())));
            target.sendMessage(Main.translate(String.format("&9&lServer> &fEl jugador &e%s &fte pide teletranportarse contigo, tienes &e60 seg para aceptar", player.getName())));

            tpaUsers.put(target, player);
            tpaType.put(target, Type.TPA);

            new TpaTask(60, target).runTaskTimer(instance, 1, 20);
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fFormato incorrecto usa &b/tpa &e<usuario>"));
        return true;
    }

    private boolean tpaDeny(CommandSender sender){
        final Player denier = Bukkit.getPlayer(sender.getName());
        final Player teleporter = tpaUsers.get(denier);

        if (teleporter == null){
            denier.sendMessage(Main.translate("&c&lServer> &fNo dispones de solicitudes de teletransporte"));
            return true;
        }

        if (teleporter.isOnline())
            teleporter.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha rechazado tu peticion", denier.getName())));
        if (denier.isOnline())
            denier.sendMessage(Main.translate("&c&lServer> &fPetición de teletransporte cancelada"));

        tpaUsers.remove(denier);
        tpaType.remove(denier);
        return true;
    }

    private boolean tpaAccept(CommandSender sender){
        final Player accepter = Bukkit.getPlayer(sender.getName());
        final Player teleporter = tpaUsers.get(accepter);

        if (teleporter == null){
            accepter.sendMessage(Main.translate("&c&lServer> &fNo dispones de solicitudes de teletransporte"));
            return true;
        }

        final SUser user = SServer.getUser(teleporter.getUniqueId());

        switch (tpaType.get(accepter)){
            case TPA:
                accepter.sendMessage(Main.translate("&9&lServer> &fSolicitud de teletransporte aceptada!"));
                teleporter.sendMessage(Main.translate(String.format("&9&lServer> &fTu solicitud de teletransporte ha sido aceptada, serás teletransportado%s", user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : " en &e5seg")));

                AtomicInteger timeA = new AtomicInteger(user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? 0 : 5);
                Main.awaitingPlayersToTeleport.put(teleporter, new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (timeA.get() != 0) timeA.getAndDecrement();
                        else {
                            new TeleportWithDelay(teleporter, accepter.getLocation(), 0, "&9&lServer> &fTeletransportando...", true);
                            tpaType.remove(accepter);
                            tpaUsers.remove(accepter);
                            cancel();
                        }
                    }
                }.runTaskTimer(Main.instance, 1, 20L));
                break;
            case TPAHERE:
                accepter.sendMessage(Main.translate(String.format("&9&lServer> &fSolicitud de teletransporte aceptada, serás teletransportado%s", user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? "" : " en &e5seg")));
                teleporter.sendMessage(Main.translate("&9&lServer> &fTu solicitud de teletransporte a ti ha sido aceptada"));

                AtomicInteger timeB = new AtomicInteger(user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank() ? 0 : 5);
                Main.awaitingPlayersToTeleport.put(accepter, new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!tpaUsers.containsKey(accepter)) cancel();
                        if (timeB.get() != 0) timeB.getAndDecrement();
                        else {
                            new TeleportWithDelay(accepter, teleporter.getLocation(), 0, "&9&lServer> &fTeletransportando...", true);
                            tpaType.remove(accepter);
                            tpaUsers.remove(accepter);
                            cancel();
                        }
                    }
                }.runTaskTimer(Main.instance, 1, 20L));
                break;
        }

        return true;
    }

    private boolean tpaHere(CommandSender sender, String[] args){
        if (args.length > 0){
            final Player player = Bukkit.getPlayer(sender.getName());
            final Player target = Bukkit.getPlayer(args[0]);

            if (target == null){
                sender.sendMessage(Main.translate(String.format("&c&lServer> &fEl jugador &e%s &fha de estar conectado", args[0])));
                return true;
            }

            player.sendMessage(Main.translate(String.format("&9&lServer> &fSolicitud de teletransporte contigo enviada al jugador &e%s", target.getName())));
            target.sendMessage(Main.translate(String.format("&9&lServer> &fEl jugador &e%s &fte pide que te teletranportes con él, tienes &e60 seg para aceptar", player.getName())));

            tpaUsers.put(target, player);
            tpaType.put(target, Type.TPAHERE);

            new TpaTask(60, target).runTaskTimer(instance, 1, 20);
        }
        else sender.sendMessage(Main.translate("&c&lServer> &fFormato incorrecto usa &b/tpahere &e<usuario>"));
        return true;
    }

    private boolean tpaAll(CommandSender sender){
        final Player player = Bukkit.getPlayer(sender.getName());
        player.sendMessage(Main.translate("&9&lServer> &fSolicitud de teletransporte contigo enviada a todos los jugadores online"));

        Bukkit.getOnlinePlayers().forEach(target -> {
            if (target != player){
                target.sendMessage(Main.translate(String.format("&9&lServer> &fEl jugador &e%s &fte pide que te teletranportes con él, tienes &e60 seg para aceptar", player.getName())));

                tpaUsers.put(target, player);
                tpaType.put(target, Type.TPAHERE);

                new TpaTask(60, target).runTaskTimer(instance, 1, 20);
            }
        });

        return true;
    }
}
