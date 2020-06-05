package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class AyudaCMD extends SCmd {

    public AyudaCMD() {
        super("ayuda", Rank.USUARIO, Arrays.asList("help"));
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length > 0 && Integer.parseInt(args[0]) > 10)
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), String.format("Ayuda.pagina%s", args[0])).split("\n"));
        else user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ayuda.pagina1"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
