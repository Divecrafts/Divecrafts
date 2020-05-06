package io.clonalejandro.DivecraftsBungee.managers.party.utils;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.party.PartyManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Party {

    @Getter private ProxiedPlayer leader;
    @Getter private final List<ProxiedPlayer> players;
    @Getter private final Map<ProxiedPlayer, Invitacion> invitations;

    private final Main plugin;

    public Party(Main plugin, ProxiedPlayer leader) {
        this.leader = leader;
        this.plugin = plugin;
        players = new ArrayList<>();
        invitations = new HashMap<>();
    }

    public void invitePlayer(ProxiedPlayer player) {
        if (!invitations.containsKey(player) || !players.contains(player)) {
            invitations.put(player, new Invitacion(plugin, player, this, 3));
        }
    }

    public void setLeader(ProxiedPlayer player) {
        if (players.contains(player)) {
            players.add(leader);
            leader = player;
            players.remove(player);
        }
    }

    public void kickPlayer(ProxiedPlayer player) {
        removePlayer(player);
        player.sendMessage(new TextComponent(PartyManager.getPrefix() + "Has sido expulsado de la Party."));
        broadcastMessage(ChatColor.translateAlternateColorCodes('&',"*&e" + player.getName() + " *&fha sido expulsado de la Party."));
    }

    public void removePlayer(ProxiedPlayer player) {
        players.remove(player);
        if (players.isEmpty()) {
            plugin.getPartyManager().getParties().remove(this);
            broadcastMessage(ChatColor.translateAlternateColorCodes('&',"La Party ha sido eliminada."));
        }
    }

    public void removeLeader() {
        plugin.getPartyManager().getParties().remove(this);
        broadcastMessage(ChatColor.translateAlternateColorCodes('&', "La party ha sido eliminada."));
    }

    public void removeInvitation(Invitacion invitation) {
        invitations.remove(invitation.getPlayer());
    }

    public void acceptInvitation(Invitacion invitation) {
        players.add(invitation.getPlayer());
        broadcastMessage(ChatColor.translateAlternateColorCodes('&', "*&e" + invitation.getPlayer().getName() + "&f* se ha unido a la Party."));
    }

    public Invitacion getInvitation(ProxiedPlayer player) {
        return invitations.get(player);
    }

    public void broadcastMessage(String message) {
        leader.sendMessage(new TextComponent(PartyManager.getPrefix() + message));
        for (ProxiedPlayer player : players) {
            player.sendMessage(new TextComponent(PartyManager.getPrefix() + message));
        }
    }
}
