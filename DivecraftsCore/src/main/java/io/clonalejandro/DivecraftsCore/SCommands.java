package io.clonalejandro.DivecraftsCore;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.*;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class SCommands implements TabCompleter {

    public static List<SCmd> cmds = new ArrayList<>();
    public static SCommands ucmds;
    private static Main plugin = Main.getInstance();
    private static String name = "server:";

    public static void load() {
        //cmds.add(new AfkCMD());
        cmds.add(new AyudaCMD());
        cmds.add(new DecirCMD());
        cmds.add(new ResponderCMD());
        cmds.add(new HelpOPCMD());
        cmds.add(new KillAllCMD());
        cmds.add(new KillCMD());
        cmds.add(new SetGroupCMD());
        cmds.add(new WeatherCMD());
        cmds.add(new KittyCMD());
        cmds.add(new ColorCMD());
        cmds.add(new GiveBoosterCMD());
        cmds.add(new DisguiseCMD());
        cmds.add(new UnDisguiseCMD());
        cmds.add(new BroadcastCMD());
        cmds.add(new HologramCMD());

        ucmds = new SCommands();
        cmds.forEach(SCommands::register);
    }

    public static void register(SCmd... cmdList) {
        for (SCmd cmd : cmdList) register(cmd);
    }

    public static void register(SCmd cmd) {
        CommandMap commandMap = getCommandMap();
        PluginCommand command = getCmd(cmd.getName());

        if (command.isRegistered()) command.unregister(commandMap);

        command.setAliases(cmd.getAliases());
        command.setTabCompleter(ucmds);

        if (commandMap == null) return;

        commandMap.register(Main.getInstance().getDescription().getName(), command);

        if (!cmds.contains(cmd)) cmds.add(cmd);

        if (plugin.getServer().getPluginCommand(name + cmd.getName()) == null) {
        }
    }

    private static PluginCommand getCmd(String name) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, Main.getInstance());
        } catch (Exception e) {
        }
        return command;
    }

    public static void onCmd(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (label.startsWith(name)) {
            label = label.replaceFirst(name, "");
        }
        for (SCmd cmdr : cmds) {
            if (label.equals(cmdr.getName()) || cmdr.getAliases().contains(label)) {
                if (sender instanceof ConsoleCommandSender) {
                    ConsoleCommandSender cs = (ConsoleCommandSender) sender;
                    cmdr.run(cs, label, args);
                    break;
                }
                if (sender instanceof Player) {
                    SUser p = SServer.getUser((Player) sender);
                    if (p.isOnRank(cmdr.getGroup())) {
                        cmdr.run(p, label, args);
                        return;
                    }

                    p.getPlayer().sendMessage(Languaje.getLangMsg(p.getUserData().getLang(), "Global.cmdnopuedes"));
                    return;
                }
                cmdr.run(sender, label, args);
            }
        }
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return commandMap;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> rtrn = null;
        if (label.startsWith(name)) {
            label = label.replaceFirst(name, "");
        }
        /*
        * Auto Complete normal para cada comando si está declarado
         */
        for (SCmd cmdr : cmds) {
            if (cmdr.getName().equals(label) || cmdr.getAliases().contains(label)) {
                try {
                    if ((sender instanceof Player) && (!SServer.getUser((Player) sender).isOnRank(cmdr.getGroup()))) {
                        return new ArrayList<>();
                    }
                    rtrn = cmdr.onTabComplete(sender, cmd, label, args, args[args.length - 1], args.length - 1);
                } catch (Exception ex) {

                }
                break;
            }
        }
        /*
        * Si el autocomplete es null, que devuelva jugadores
         */
        if (rtrn == null) {
            rtrn = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                rtrn.add(p.getName());
            }
        }
        /*
        * Autocomplete para cada argumento
         */
        ArrayList<String> rtrn2 = new ArrayList<>();
        rtrn2.addAll(rtrn);
        rtrn = rtrn2;
        if (!(args[args.length - 1].isEmpty() || args[args.length - 1] == null)) {
            List<String> remv = new ArrayList<>();
            for (String s : rtrn) {
                if (!StringUtils.startsWithIgnoreCase(s, args[args.length - 1])) {
                    remv.add(s);
                }
            }
            rtrn.removeAll(remv);
        }
        return rtrn;
    }
}
