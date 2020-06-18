package io.clonalejandro.DivecraftsCore.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.user.Profile;
import io.clonalejandro.DivecraftsCore.utils.Disguise;
import io.clonalejandro.DivecraftsCore.utils.ReflectionAPI;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.*;

public class SUser {

    private final Main plugin = Main.getInstance();

    @Getter private final UUID uuid;
    @Getter @Setter private UserData userData;
    @Getter private final List<BukkitRunnable> tasks = new ArrayList<>();
    @Getter @Setter private Profile profile;

    /**
     * Default Constructor
     *
     * @param p the OfflinePlayer
     */
    public SUser(OfflinePlayer p) {
        this(p.getUniqueId());
    }

    /**
     * Default constructor
     *
     * @param uuid The user UUID
     */
    public SUser(UUID uuid) {
        this.uuid = uuid;
        setUserData(plugin.getMySQL().loadUserData(uuid));
        setProfile(new Profile(this));
    }

    /**
     * Saves the user in the MySQL
     */
    public void save() {
        plugin.getMySQL().saveUser(this);
        SServer.users.remove(this);
        plugin.getMySQL().loadUserData(uuid);
        SServer.users.add(this);
    }


    public OfflinePlayer getOfflinePlayer() {
        return plugin.getServer().getOfflinePlayer(uuid);
    }
    public Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
    }
    public String getName() {
        return getOfflinePlayer().getName();
    }
    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }
    public boolean isOnRank(SCmd.Rank rank) {
        return getUserData().getRank().getRank() >= rank.getRank();
    }
    public Location getLoc() {
        return getPlayer().getLocation();
    }
    public World getWorld() {
        return getPlayer().getWorld();
    }
    public String getDisplayName() {
        return getPlayer().getDisplayName();
    }
    public PlayerInventory getInventory() {
        return getPlayer().getInventory();
    }
    public double getHealth() {
        return getPlayer().getHealth();
    }


    public void sendDiv() {
        getPlayer().sendMessage(Utils.colorize("&e====================="));
    }
    public void sendSound(Sounds sound) {
        getPlayer().playSound(getPlayer().getLocation(), sound.toSound(), 4, 4);
    }
    public void teleport(Location location) {
        getPlayer().teleport(location);
    }
    public void teleport(Entity entity) {
        getPlayer().teleport(entity);
    }
    public void teleport(World world) {
        teleport(world.getSpawnLocation());
    }
    public void removeItemInHand() {
        getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
    }
    public void openInventory(Inventory inv) {
        getPlayer().openInventory(inv);
    }
    public void closeInventory() {
        getPlayer().closeInventory();
    }

    public void clearInventory() {
        getPlayer().getInventory().clear();
    }

    public void heal() {
        getPlayer().setHealth(getPlayer().getHealthScale());
        getPlayer().setFoodLevel(20);
        getPlayer().getActivePotionEffects().forEach(p -> getPlayer().removePotionEffect(p.getType()));
    }

    public void toggleFly() {
        if (getPlayer().getAllowFlight()) {
            getPlayer().setAllowFlight(false);
            getPlayer().setFlying(false);
            getUserData().setFly(false);
            return;
        }
        getUserData().setFly(true);
        getPlayer().setAllowFlight(true);
        getPlayer().setFlying(true);
    }

    public void sendToServer(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Conectar");
        out.writeUTF(server);
        getPlayer().sendPluginMessage(plugin, "DivecraftsBungee", out.toByteArray());
    }

    public void sendActionBar(String msg) {
        try {
            Constructor<?> constructor = ReflectionAPI.getNmsClass("PacketPlayOutChat").getConstructor(ReflectionAPI.getNmsClass("IChatBaseComponent"), byte.class);
            Object icbc = ReflectionAPI.getNmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + Utils.colorize(msg) + "\"}");
            Object packet = constructor.newInstance(icbc, (byte) 2);

            ReflectionAPI.sendPacket(getPlayer(), packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendHeaderAndFooter(String headerText, String footerText) {
        try {
            Class chatSerializer = ReflectionAPI.getNmsClass("IChatBaseComponent$ChatSerializer");

            Object tabHeader = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(headerText) + "'}");
            Object tabFooter = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{'text': '" + Utils.colorize(footerText) + "'}");
            Object tab = ReflectionAPI.getNmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[]{ReflectionAPI.getNmsClass("IChatBaseComponent")}).newInstance(new Object[]{tabHeader});

            Field f = tab.getClass().getDeclaredField("b");
            f.setAccessible(true);
            f.set(tab, tabFooter);

            ReflectionAPI.sendPacket(getPlayer(), tab);
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void tryHidePlayers() {
        switch (getUserData().getVisible()) {
            case 0:
                plugin.getServer().getOnlinePlayers().forEach(pl -> getPlayer().hidePlayer(pl));
                break;
            case 1:
                //AMIGOS SOLO
                break;
            case 2:
                plugin.getServer().getOnlinePlayers().forEach(pl -> getPlayer().showPlayer(pl));
                break;
        }
    }


    @Data
    public static class UserData {
        SCmd.Rank rank = SCmd.Rank.USUARIO;

        Location lastLocation = null;
        Boolean god = false;
        Long lastConnect = 0L;
        Long timeJoin = 0L;
        Long timePlayed = 0L;
        String nickname = null;
        InetSocketAddress ip = null;
        Integer coins = 200;
        List<SBooster> boosters = new ArrayList<>();
        Integer keys = 0;
        String clanName = null;

        String nickcolor = "7";

        // Settings
        Integer visible = 2;//0 nadie, 1 amigos, 2 todos
        Boolean clanes = false;
        Boolean chat = true;
        Boolean partys = true;
        Boolean fly = false;
        Integer lang = Languaje.Lang.ES.getId();
        String disguise = "";

        // Game Stats
        HashMap<Integer, Integer> kills = new HashMap<>(); // ID, amount
        HashMap<Integer, Integer> deaths = new HashMap<>();
        HashMap<Integer, Integer> wins = new HashMap<>();
        HashMap<Integer, Integer> plays = new HashMap<>();
        Integer mum_elo = 0;
        Integer mum_reroll = 0;


        public UserData() { //No Nulls
            Arrays.asList(SServer.GameID.values()).forEach(g -> {
                kills.put(g.getId(), 0);
                deaths.put(g.getId(), 0);
                wins.put(g.getId(), 0);
                plays.put(g.getId(), 0);
            });
        }


        public int getKills(SServer.GameID gameMode) {
            return kills.get(gameMode.getId());
        }
        public int getDeaths(SServer.GameID gameMode) {
            return deaths.get(gameMode.getId());
        }
        public int getWins(SServer.GameID gameMode) {
            return wins.get(gameMode.getId());
        }
        public int getPlays(SServer.GameID gameMode) {
            return plays.get(gameMode.getId());
        }
        public void addKill(SServer.GameID gameMode) {
            kills.replace(gameMode.getId(), getKills(gameMode) + 1);
        }
        public void addDeath(SServer.GameID gameMode) {
            deaths.replace(gameMode.getId(), getDeaths(gameMode) + 1);
        }
        public void addWin(SServer.GameID gameMode) {
            wins.replace(gameMode.getId(), getWins(gameMode) + 1);
        }
        public void addPlay(SServer.GameID gameMode) {
            plays.replace(gameMode.getId(), getPlays(gameMode) + 1);
        }
    }
}
