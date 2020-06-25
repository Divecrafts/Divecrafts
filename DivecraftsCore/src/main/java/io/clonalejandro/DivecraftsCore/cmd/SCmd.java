package io.clonalejandro.DivecraftsCore.cmd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class SCmd {

    protected static transient Main plugin = Main.getInstance();
    protected static transient Random r = new Random();
    @Getter private final transient String name;
    @Getter private final transient List<String> aliases;
    @Getter private transient Rank group = Rank.USUARIO;

    public SCmd(final String name, final Rank rank, final List<String> aliases) {
        this.name = name.toLowerCase();
        this.group = rank;
        this.aliases = aliases;
    }

    public SCmd(final String name, final Rank rank, final String... aliases) {
        this(name, rank, aliases.length > 0 ? Arrays.asList(aliases) : null);
    }

    public SCmd(final String name, final Rank rank) {
        this(name, rank, "");
    }

    public void run(ConsoleCommandSender sender, String label, String[] args) {
        run((CommandSender) sender, label, args);
    }

    public void run(SUser user, String label, String[] args) {
        run(user.getPlayer(), label, args);
    }

    public void run(CommandSender sender, String label, String[] args) {
        sender.sendMessage(Utils.colorize("*&cEste comando no está funcional para este sender"));
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return new ArrayList<>();
    }

    public void userNotOnline(SUser user) {
        user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.noconectado"));
    }

    @Getter
    @AllArgsConstructor
    public enum Rank {
        USUARIO(0, ""),
        NEMO(1, "NEMO"),
        MEGALODON(2, "MEGALODON"),
        KRAKEN(3, "KRAKEN"),
        MEDUSA(4, "MEDUSA"),
        POSEIDON(5, "POSEIDON"),
        YOUTUBER(6, "YT"),
        TMOD(7, "MOD"),
        BUILDER(8, "BUILDER"),
        MOD(9, "MOD"),
        SMOD(10, "SMOD"),
        DEV(11, "DEV"),
        ADMIN(12, "ADM"),
        CEO(13, "CEO");

        private final int rank;
        private final String prefix;

        public static String groupColor(Rank rank) {
            switch (rank) {
                case CEO:
                    return "4&l";
                case ADMIN:
                    return "c&l";
                case DEV:
                    return "d&l";
                case SMOD:
                case MOD:
                case TMOD:
                    return "6&l";
                case BUILDER:
                    return "3&l";
                case YOUTUBER:
                    return "c";
                case POSEIDON:
                    return "e&l";
                case MEDUSA:
                    return "5&l";
                case KRAKEN:
                    return "a&l";
                case MEGALODON:
                    return "b&l";
                case NEMO:
                    return "9&l";
                default:
                    return "8";
            }
        }
    }
}
