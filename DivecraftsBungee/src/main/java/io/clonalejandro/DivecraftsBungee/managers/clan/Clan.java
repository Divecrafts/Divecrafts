package io.clonalejandro.DivecraftsBungee.managers.clan;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clan {

    @Getter String name;
    @Getter String lider;
    @Getter @Setter List<ProxiedPlayer> miembros;
    @Getter private final Map<ProxiedPlayer, Invitacion> invitations;

    public Clan(String name) {
        this.name = name;
        miembros = new ArrayList<>();
        invitations = new HashMap<>();
    }

    public void setLider (ProxiedPlayer player) {
        lider = player.getName();
        miembros.add(player);
        Main.getMySQL().actualizarQuery("UPDATE `data` SET `clan`='" + getName() + "' WHERE `uuid` = '" + player.getUniqueId() + "'");
        save();

    }

    public void invitePlayer(ProxiedPlayer player) {
        if (!invitations.containsKey(player) || !miembros.contains(player)) {
            invitations.put(player, new Invitacion(Main.getMB(), player, this, 3));
        }
    }

    public void kickPlayer(ProxiedPlayer player) {
        removePlayer(player);
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.hassidoexpulsado"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(ProxiedPlayer player) {
        miembros.remove(player);
        if (player.getName().equalsIgnoreCase(getLider())) {
            removeClan();
        }
    }

    public void removeClan() {
        for (ProxiedPlayer p : miembros) {
            Main.getMySQL().actualizarQuery("UPDATE `data` SET `clan`='' WHERE `name` = '" + p.getName() +"'");
        }
        Main.getMB().getClanManager().getClanes().remove(this);
        Main.getMB().getClanManager().getClanesNames().remove(getName());
        try {
            broadcastMessage("Clanes.eliminado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        delete();
    }

    public void removeInvitation(Invitacion invitation) {
        invitations.remove(invitation.getPlayer());
    }

    public void acceptInvitation(Invitacion invitation) {
        miembros.add(invitation.getPlayer());
        Main.getMySQL().actualizarQuery("UPDATE `data` SET `clan`='" + getName() + "' WHERE `name` = '" + invitation.getPlayer().getName() + "'");
        save();
    }

    public Invitacion getInvitation(ProxiedPlayer player) {
        return invitations.get(player);
    }

    public void broadcastMessage(String message) throws SQLException {
        for (ProxiedPlayer player : miembros) {
            player.sendMessage(new TextComponent(TextUtils.formatText(message)));
        }
    }

    public void save() {
        try {
            if (!Main.getConfigManager().getClanFile(getName()).exists()) {
                Main.getConfigManager().getClanFile(getName()).createNewFile();
                Configuration configuration = YamlConfiguration.getProvider(YamlConfiguration.class).load(Main.getConfigManager().getClanFile(getName()));

                List<String> names = new ArrayList<>();
                for (ProxiedPlayer player : getMiembros()) {
                    names.add(player.getName());
                }

                configuration.set("name", getName());
                configuration.set("lider", getLider());
                configuration.set("miembros", names);

                YamlConfiguration.getProvider(YamlConfiguration.class).save(configuration, Main.getConfigManager().getClanFile(getName()));
            } else {
                Configuration configuration = YamlConfiguration.getProvider(YamlConfiguration.class).load(Main.getConfigManager().getClanFile(getName()));

                List<String> names = new ArrayList<>();
                for (ProxiedPlayer player : getMiembros()) {
                    names.add(player.getName());
                }

                configuration.set("name", getName());
                configuration.set("lider", getLider());
                configuration.set("miembros", names);

                YamlConfiguration.getProvider(YamlConfiguration.class).save(configuration, Main.getConfigManager().getClanFile(getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (Main.getConfigManager().getClanFile(getName()).exists()) {
                Main.getConfigManager().getClanFile(getName()).delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
