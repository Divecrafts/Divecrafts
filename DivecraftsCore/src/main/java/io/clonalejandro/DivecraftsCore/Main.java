package io.clonalejandro.DivecraftsCore;

import io.clonalejandro.DivecraftsCore.events.PlayerEvents;
import io.clonalejandro.DivecraftsCore.inv.InventoryManager;
import io.clonalejandro.DivecraftsCore.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends JavaPlugin {

    @Getter @Setter private static String PREFIX = "&9&lServer> ";
    @Getter public static final HashMap<Player, PermissionAttachment> perms = new HashMap<>();
    @Getter private static Main instance;

    @Getter private InventoryManager inventoryManager;
    @Getter private MySQL mySQL;
    AntiWorldDownloader awd;

    public void onEnable() {
        instance = this;
        awd = new AntiWorldDownloader(instance);

        File fConf = new File(getDataFolder(), "config.yml");
        if (!fConf.exists()) {
            try {
                getConfig().options().copyDefaults(true);
                saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        awd.enable();

        try {
            final String host = getConfig().getString("MySQL.host") == null ? "localhost" : getConfig().getString("MySQL.host");

            mySQL = new MySQL(host, "divecrafts", "root", "patata123");
            mySQL.openConnection();
        } catch (SQLException e) {
            Log.log(Log.ERROR, "Imposible conectar con la MySQL, desactivando el plugin");
            getServer().getPluginManager().disablePlugin(this);
        }

        SCommands.load();
        registerClasses();
        registerEvents();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::parseHolograms, 0, 400L);
    }

    @Override
    public void onDisable() {
        awd.disable();
        try {
            mySQL.getConnection().close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void registerClasses() {
        inventoryManager = new InventoryManager();
        registerGadgetsMenuHook();
        registerBungee();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerEvents(instance), instance);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            SCommands.onCmd(sender, cmd, label, args);
        } catch (Exception ex) {
            Log.log(Log.ERROR, "Error al ejecutar el comando '" + label + Arrays.toString(args) + "'");
            ex.printStackTrace();
        }
        return true;
    }

    private void registerBungee() {
        String version = getServer().getVersion();

        getServer().getMessenger().registerIncomingPluginChannel(this, version.contains("1.8") ? "DivecraftsBungee" : "divecrafts:divecraftsbungee", new BungeeMensager());
        getServer().getMessenger().registerOutgoingPluginChannel(this, version.contains("1.8") ? "DivecraftsBungee" : "divecrafts:divecraftsbungee");
    }

    private void registerGadgetsMenuHook(){
        if (Bukkit.getPluginManager().isPluginEnabled("GadgetsMenu")){
            try {
                Class<?> provider = Class.forName("com.yapzhenyie.GadgetsMenu.economy.GEconomyProvider");
                provider.getMethod("setMysteryDustStorage", provider)
                        .invoke(null, new GadgetsMenuHook(this));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void parseHolograms(){
        for (Entity e : Hologramas.hologramas) {
            e.remove();
        }
        Hologramas.hologramas.clear();

        String pprompt = "Holograms.hol1.msg";
        String res = getConfig().getString(pprompt);

        while (res != null){
            pprompt = pprompt.replace(".msg", "");

            final Configuration con = getConfig();
            final String prompt = pprompt + ".", msg = Utils.colorize(con.getString(prompt + "msg"));
            final World world = Bukkit.getWorld(con.getString(prompt + "world"));
            final double x = con.getDouble(prompt + "x"), y = con.getDouble(prompt + "y"), z = con.getDouble("z");

            Hologramas.crearHolo(msg, new Location(world, x, y, z), world.getName());

            int val = Integer.parseInt(pprompt.replace("Holograms.hol", "").replace(".msg", "")) + 1;
            pprompt = "Holograms." + "hol" + val  + ".msg";
            res = getConfig().getString(pprompt);
        }
    }
}
