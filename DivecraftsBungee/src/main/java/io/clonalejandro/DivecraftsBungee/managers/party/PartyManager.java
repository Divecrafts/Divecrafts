package io.clonalejandro.DivecraftsBungee.managers.party;

import io.clonalejandro.DivecraftsBungee.Main;
import io.clonalejandro.DivecraftsBungee.managers.idiomas.Languaje;
import io.clonalejandro.DivecraftsBungee.managers.party.utils.Invitacion;
import io.clonalejandro.DivecraftsBungee.managers.party.utils.Party;
import io.clonalejandro.DivecraftsBungee.utils.TextUtils;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartyManager {
	
	@Getter private static String prefix = TextUtils.formatText("&d&lParty> &f");
	
	private final Main plugin;
    @Getter private final List<Party> parties;

    public PartyManager(Main plugin) {
        this.plugin = plugin;
        parties = new ArrayList<>();
    }

    public void createParty(ProxiedPlayer player) {
        parties.add(new Party(plugin, player));
    }

    public void invitePlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        if (getParty(sender) == null) {
            createParty(sender);
            try {
                sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Party.creado"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Party party = getParty(sender);
        if (party.getLeader().equals(sender)) {
            if (!party.getPlayers().contains(player)) {
                party.invitePlayer(player);
                try {
                    sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Party.hasinvitado") + player.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Party.entuparty"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                sender.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Party.nolider"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invitacion invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    party.acceptInvitation(invitation);
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.aceptar"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.invitacionno"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void denyInvitation(ProxiedPlayer player, ProxiedPlayer leader) {
        for (Party party : parties) {
            if (party.getLeader().equals(leader)) {
                Invitacion invitation = party.getInvitation(player);
                if (invitation != null) {
                    party.removeInvitation(invitation);
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.cancelar"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        try {
            player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.invitacionno"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void kickPlayer(ProxiedPlayer sender, ProxiedPlayer player) {
        Party party = getParty(sender);
        if (party != null) {
            if (party.getLeader().equals(sender)) {
                if (party.getPlayers().contains(player)) {
                    party.kickPlayer(player);
                } else {
                    try {
                        player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(sender), "Party.noentuparty"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.nolider"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                player.sendMessage(Languaje.getLangMsg(Languaje.getPlayerLang(player), "Party.NoPerteneces"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Party getParty(ProxiedPlayer player) {
        for (Party party : parties) {
            if (party.getPlayers().contains(player) || party.getLeader().equals(player)) {
                return party;
            }
        }
        return null;
    }
}
