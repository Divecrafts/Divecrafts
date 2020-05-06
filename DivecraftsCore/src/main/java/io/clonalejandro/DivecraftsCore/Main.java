package io.clonalejandro.DivecraftsCore;

import io.clonalejandro.DivecraftsCore.utils.*;
import lombok.Getter;
import lombok.Setter;
import io.clonalejandro.DivecraftsCore.events.PlayerEvents;
import io.clonalejandro.DivecraftsCore.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

public class Main extends JavaPlugin {

    @Getter @Setter private static String PREFIX = "";

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
          mySQL = new MySQL("localhost", "divecrafts", "root", "patata123");
          mySQL.openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            Log.log(Log.ERROR, "Imposible conectar con la MySQL, desactivando el plugin");
            getServer().getPluginManager().disablePlugin(this);
        }

        io.clonalejandro.DivecraftsCore.SCommands.load();

        registerClasses();
        registerEvents();
    }

    @Override
    public void onDisable() {
        awd.disable();
    }

    private void registerClasses() {
        inventoryManager = new InventoryManager();
        registerBungee();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerEvents(instance), instance);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            io.clonalejandro.DivecraftsCore.SCommands.onCmd(sender, cmd, label, args);
        } catch (Exception ex) {
            Log.log(Log.ERROR, "Error al ejecutar el comando '" + label + Arrays.toString(args) + "'");
            ex.printStackTrace();
        }
        return true;
    }

    private void registerBungee() {
        getServer().getMessenger().registerIncomingPluginChannel(this, "DivecraftsBungee", new BungeeMensager());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "DivecraftsBungee");
    }
}
