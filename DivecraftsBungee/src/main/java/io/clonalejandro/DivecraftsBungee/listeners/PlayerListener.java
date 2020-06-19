package io.clonalejandro.DivecraftsBungee.listeners;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.clan.Clan;
import io.clonalejandro.DivecraftsBungee.managers.party.utils.Party;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) throws SQLException, IOException {
        ProxiedPlayer player = e.getPlayer();
        ResultSet s = Main.getMySQL().query("SELECT * FROM data WHERE name='" + player.getName() + "'");
        while(s.next()) {
            loadPermissions(player, s);
            Clan clan = Main.getMB().getClanManager().getClanByName(s.getString("clan"));
            if (clan != null) {
                clan.getMiembros().add(player);
            }
        }
    }

    @EventHandler
    public void onPlayerKick(ServerKickEvent event){
        event.getPlayer().sendMessage(TextUtils.formatText(event.getKickReason()));
    }

	@EventHandler
    public void onPlayerQuit(PlayerDisconnectEvent e){
        ProxiedPlayer player = e.getPlayer();
        Party party = Main.getMB().getPartyManager().getParty(player);
        if(party != null){
            if(party.getLeader().equals(player)){
                party.removeLeader();
            }else{
                party.removePlayer(player);
            }
        }
        Clan clan = Main.getMB().getClanManager().getClan(player);
        if (clan != null) {
            clan.removePlayer(player);
        }
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.getMessage().startsWith("/bungee") || e.getMessage().startsWith("bungee:")) e.setCancelled(true);
    }

    private void loadPermissions(ProxiedPlayer player, ResultSet result){
        try {
            final int rankId = result.getInt("grupo");
            final List<String> permissions = new ArrayList<>();

            if (rankId > 0){
                for (int i = 0; i <= rankId; i++)
                    permissions.addAll(Main.getConfigManager().getConfig().getStringList(String.format("Permissions.%s.perms", i)));
            }
            else permissions.addAll(Main.getConfigManager().getConfig().getStringList(String.format("Permissions.%s.perms", rankId)));

            permissions.forEach(permission -> player.setPermission(permission, true));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
