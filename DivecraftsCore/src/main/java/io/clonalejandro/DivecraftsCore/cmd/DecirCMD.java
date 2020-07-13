package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DecirCMD extends SCmd {

    public DecirCMD() {
        super("decir", Rank.USUARIO, Arrays.asList("w", "m", "msg", "mensaje", "tell"));
    }

    @Getter private final static HashMap<Player, Player> lastPlayerMsg = new HashMap<>();

    private static void sendPrivateMessage(SUser target, SUser from, String mensaje) {
        target.getPlayer().sendMessage(Languaje.getLangMsg(target.getUserData().getLang(), "Msg.desde").replace("%player%", from.getName()) + mensaje);
        from.getPlayer().sendMessage(Languaje.getLangMsg(from.getUserData().getLang(), "Msg.para").replace("%player%", target.getName()) + mensaje);
        target.sendSound(Sounds.LEVEL_UP);
    }

    private static void sendPrivateMessage(SUser target, ConsoleCommandSender from, String mensaje) {
        target.getPlayer().sendMessage(Languaje.getLangMsg(target.getUserData().getLang(), "Msg.desde").replace("%player%", from.getName()) + mensaje);
        from.sendMessage(Languaje.getLangMsg(Languaje.Lang.ES.getId(), "Msg.para").replace("%player%", target.getName()) + mensaje);
        target.sendSound(Sounds.LEVEL_UP);
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (args.length <= 1){
            return;
        }

        SUser target = SServer.getUser(Main.getInstance().getServer().getPlayer(args[0]));

        if (user == target) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Msg.noatimismo"));
            return;
        }
        if (target == null || !target.isOnline()) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.noconectado"));
            return;
        }
        args[0] = "";

        if (lastPlayerMsg.get(target.getPlayer()) == null)
            lastPlayerMsg.remove(target.getPlayer());
        lastPlayerMsg.put(target.getPlayer(), user.getPlayer());

        final String message = user.getUserData().getRank().getRank() > 0 ? Utils.colorize(Utils.buildString(Arrays.copyOfRange(args, 1, args.length))) : Utils.buildString(Arrays.copyOfRange(args, 1, args.length));

        if (message.matches("\\d{1,3}(\\.\\d{1,3}){3}|.+\\.[a-zA-Z]{2,3}")){
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Chat.noips"));
            return;
        }

        sendPrivateMessage(target, user, message);
    }

    @Override
    public void run(ConsoleCommandSender sender, String lbl, String[] args) {
        if (args.length == 1){
            return;
        }

        SUser target = SServer.getUser(Main.getInstance().getServer().getPlayer(args[0]));

        if (target == null || !target.isOnline()) {
            sender.sendMessage(Languaje.getLangMsg(Languaje.Lang.ES.getId(), "Global.noconectado"));
            return;
        }
        args[0] = "";
        sendPrivateMessage(target, sender, Utils.colorize(Utils.buildString(args)));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return null;
    }
}
