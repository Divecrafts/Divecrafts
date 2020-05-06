package io.clonalejandro.DivecraftsBungee.managers.party.utils;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.clan.ClanManager;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.managers.party.PartyManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Invitacion {

    @Getter private final ProxiedPlayer player;
    private final Party party;
    private final Main plugin;
    private boolean valid;

    private ScheduledTask task;

    public Invitacion(Main plugin, ProxiedPlayer player, Party party, int minutes) {
        this.player = player;
        this.party = party;
        this.plugin = plugin;
        valid = true;
        startInvitation(minutes);
    }

    @SuppressWarnings("unused")
	private void acceptInvitation() {
        if (valid) {
            task.cancel();
            party.removeInvitation(this);
            party.acceptInvitation(this);
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.aceptar"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
	private void denyInvitation() {
        if (valid) {
            task.cancel();
            party.removeInvitation(this);
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.cancelar"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void startInvitation(int minutes) {
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.hassidoinvitadopor").replaceAll("%lider%", party.getLeader().getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent accept = null;
        try {
            final String msg = Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Aceptar");

            accept = new TextComponent(msg);
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + party.getLeader().getName()));
            accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Aceptar")).create()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent deny = null;
        try {
            final String msg = Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Cancelar");

            deny = new TextComponent(msg);
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan deny " + party.getLeader().getName()));
            deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Global.Cancelar")).create()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextComponent full = new TextComponent(PartyManager.getPrefix());
        full.addExtra(accept);
        full.addExtra(new TextComponent(ChatColor.translateAlternateColorCodes('&', " &ro ")));
        full.addExtra(deny);
        player.sendMessage(full);
        task = plugin.getProxy().getScheduler().schedule(plugin, () -> {
            party.removeInvitation(this);
            valid = false;
        }, minutes, TimeUnit.MINUTES);
    }
}
