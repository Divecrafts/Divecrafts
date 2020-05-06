package io.clonalejandro.DivecraftsBungee.managers.clan;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Invitacion {
    @Getter
    private final ProxiedPlayer player;
    private final Clan clan;
    private final Main plugin;
    private boolean valid;

    private ScheduledTask task;

    public Invitacion(Main plugin, ProxiedPlayer player, Clan clan, int minutes) {
        this.player = player;
        this.clan = clan;
        this.plugin = plugin;
        valid = true;
        startInvitation(minutes);
    }

    @SuppressWarnings("unused")
    private void acceptInvitation() {
        if (valid) {
            task.cancel();
            clan.removeInvitation(this);
            clan.acceptInvitation(this);
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.aceptar"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
    private void denyInvitation() {
        if (valid) {
            task.cancel();
            clan.removeInvitation(this);
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.cancelar"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void startInvitation(int minutes) {
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Clanes.hassidoinvitadopor").replaceAll("%lider%", clan.getLider()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent accept = null;
        try {
            accept = new TextComponent("§a§l[" + Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Aceptar") + "]");
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept " + clan.getLider()));
            accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Aceptar")).create()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent deny = null;
        try {
            deny = new TextComponent("§4§l[" + Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Cancelar") + "]");
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan deny " + clan.getLider()));
            deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Cancelar")).create()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent full = new TextComponent(ClanManager.getPrefix());
        full.addExtra(accept);
        full.addExtra(new TextComponent(" §r§7o "));
        full.addExtra(deny);
        player.sendMessage(full);
        task = plugin.getProxy().getScheduler().schedule(plugin, () -> {
            clan.removeInvitation(this);
            valid = false;
        }, minutes, TimeUnit.MINUTES);
    }

}
