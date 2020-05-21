package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;

public class FlyCMD extends SCmd {

    public FlyCMD() {
        super("fly", Rank.TMOD, "volar");
    }

    public void run(SUser u, String label, String... args) {
        if (args.length > 1) return;
        u.toggleFly();
    }
}
