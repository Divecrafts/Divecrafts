package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;

import java.util.Arrays;

public class AfkCMD extends SCmd {

    public AfkCMD() {
        super("afk", Rank.USUARIO, Arrays.asList("away"));
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (SServer.afkMode.contains(user)) {
            SServer.afkMode.remove(user);
        } else {
            SServer.afkMode.add(user);

        }
    }
}
