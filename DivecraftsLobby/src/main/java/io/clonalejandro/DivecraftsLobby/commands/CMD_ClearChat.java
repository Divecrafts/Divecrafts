package io.clonalejandro.DivecraftsLobby.commands;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsLobby.Main;

public class CMD_ClearChat extends SCmd {

    public CMD_ClearChat() {
        super("clearchat", Rank.TMOD);
    }

    public void run(SUser u, String label, String... args) {
        Main.getUsers().forEach(us -> {
            for (int i = 0; i < 100; i++) us.getPlayer().sendMessage(" ");
        });
        u.getPlayer().sendMessage("§cChat eliminado para todos");
    }
}
