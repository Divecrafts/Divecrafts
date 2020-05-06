package io.clonalejandro.DivecraftsBungee.managers.clan;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.MessageType;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClanManager {

    @Getter List<Clan> clanes;
    @Getter List<String> clanesNames;

    @Getter private static String prefix = TextUtils.formatText("&3&lClan> &e");

    public ClanManager() {
        this.clanes = new ArrayList<>();
        this.clanesNames = new ArrayList<>();
    }

    public void loadClanes() throws IOException {
        int i = 0;
        for (File clanFile : Main.getConfigManager().clanesFolder.listFiles()) {
            Configuration config = YamlConfiguration.getProvider(YamlConfiguration.class).load(clanFile);
            clanesNames.add(config.getString("name"));
            Clan clan = new Clan(config.getString("name"));
            List<ProxiedPlayer> miembros = new ArrayList<>();
            for (String name : config.getStringList("miembros")) {
                ProxiedPlayer miembro = Main.getMB().getProxy().getPlayer(name);
                if (miembro != null) {
                    miembros.add(miembro);
                }
            }
            clan.lider = config.getString("lider");
            clan.setMiembros(miembros);
            clanes.add(clan);
            i++;
        }
        TextUtils.consoleMSG("&eTotal Clanes cargados: &a" + i, MessageType.SUCCESS);
    }

    public void createClan(String name, ProxiedPlayer lider) {
        Clan clan = new Clan(name);
        if (!clanesNames.contains(name)) {
            clanes.add(clan);
            clanesNames.add(name);
            clan.setLider(lider);
            try {
                lider.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(lider), "Clanes.creado"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clan.save();
        } else {
            try {
                lider.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(lider), "Clanes.existe"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void invitePlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        Clan clan = getClan(sender);
        if (clan.getLider().equals(sender.getName())) {
            if (!clan.getMiembros().contains(player)) {
                clan.invitePlayer(player);
                try {
                    sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Clanes.hasinvitado") + player.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Clanes.entuclan"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Clanes.nolider"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Clan clan : clanes) {
            if (clan.getLider().equals(leader.getName())) {
                Invitacion invitation = clan.getInvitation(player);
                if (invitation != null) {
                    clan.removeInvitation(invitation);
                    clan.acceptInvitation(invitation);
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.aceptar"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.invitacionno"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void denyInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Clan clan : clanes) {
            if (clan.getLider().equals(leader.getName())) {
                Invitacion invitation = clan.getInvitation(player);
                if (invitation != null) {
                    clan.removeInvitation(invitation);
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.cancelar"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.invitacionno"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kickPlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        Clan clan = getClan(sender);
        if (clan != null) {
            if (clan.getLider().equals(sender.getName())) {
                if (clan.getMiembros().contains(player)) {
                    clan.kickPlayer(player);
                } else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Clanes.noentuclan"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.nolider"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.NoPerteneces"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Clan getClan(ProxiedPlayer player) {
        for (Clan clan : clanes) {
            if (clan.getMiembros().contains(player)) {
                return clan;
            }
        }
        return null;
    }

    public Clan getClanByName(String name) {
        for (Clan clan : clanes) {
            if (clan.getName().equalsIgnoreCase(name)) {
                return clan;
            }
        }
        return null;
    }


}
