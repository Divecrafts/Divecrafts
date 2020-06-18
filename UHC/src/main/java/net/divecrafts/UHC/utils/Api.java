package net.divecrafts.UHC.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.task.ScoreTask;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alejandrorioscalera
 * On 16/1/18
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2018
 */

public class Api {


    /** SMALL CONSTRUCTORS **/

    public static final String PREFIX = "UHC> ", SPACE = " ", NULL = ""; //Strings for personal help
    public static final ArrayList<Player> ALIVE_PLAYERS = new ArrayList<>(); //List to players alive
    public static final HashMap<Player, Integer> KILLS = new HashMap<>();//Kills of players
    public static final PluginManager PLUGIN_MANAGER = Main.instance.getServer().getPluginManager(); //Plugin Manager
    public static final ArrayList<String> DESCONECTED = new ArrayList<>(); //List to players offline

    private static Game game;
    private static Arena arena;

    private volatile static State state = State.NULL; //Set the game state per default null
    private volatile static String HOST;


    /** REST **/

    /**
     * This function send a message to a Console server
     * @param message
     */
    public static void sendConsole(String message){
        Bukkit.getConsoleSender().sendMessage(message);
    }


    /**
     * This function send a message to all Players online
     * @param message
     */
    public static void broadCast(String message){
        Bukkit.broadcastMessage(message);
    }


    /**
     * This function return an Arena instance
     * @return
     */
    public static Arena getArena() {
        return arena;
    }


    /**
     * This function set an Arena instance
     * @param arena
     */
    public static void setArena(Arena arena){
        Api.arena = arena;
    }


    /**
     * This function return a Game instance
     * @return
     */
    public static Game getGame(){
        return game;
    }


    /**
     * This function set new Game instance
     * @param game
     */
    public static void setGame(Game game){
        Api.game = game;
    }


    /**
     * This function playSound to all Players Online
     * @param world
     * @param sound
     * @param volume
     * @param pitch
     */
    public static void playSound(final World world, final Sound sound, final float volume, final float pitch){
        for (Player player : getOnlinePlayers())
            world.playSound(player.getLocation(), sound, volume, pitch);
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName){
        final ItemStack item = new ItemStack(Material.SKULL);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(playerName);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @param itemName
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName, final String itemName){
        final ItemStack item = new ItemStack(Material.SKULL);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(playerName);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @param itemName
     * @param itemLore
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName, final String itemName, final List<String> itemLore){
        final ItemStack item = new ItemStack(Material.SKULL);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (itemName != null) meta.setDisplayName(itemName);

        meta.setOwner(playerName);
        meta.setLore(itemLore);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @param count
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName, final int count){
        final ItemStack item = new ItemStack(Material.SKULL, count);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(playerName);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @param count
     * @param itemName
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName, final int count, final String itemName){
        final ItemStack item = new ItemStack(Material.SKULL, count);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(playerName);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function return a skull head from Player name
     * @param playerName
     * @param count
     * @param itemName
     * @param itemLore
     * @return
     */
    public static ItemStack getPlayerSkull(final String playerName, final int count, final String itemName, final List<String> itemLore){
        final ItemStack item = new ItemStack(Material.SKULL, count);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(playerName);
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        item.setItemMeta(meta);

        return item;
    }


    /**
     * This function create an explosion from Location
     * @param location
     */
    public static void createExplossion(Location location){
        location.getWorld().createExplosion(location, 4.0f);
    }


    /**
     * This function create an explosion from Location
     * @param location
     * @param power
     */
    public static void createExplossion(Location location, float power){
        location.getWorld().createExplosion(location, power);
    }


    /**
     * This function return a Random number
     * @param max
     * @param min
     * @return
     */
    public static int getRandom(int max, int min){
        return min + (int) (Math.random() * ((max - min) + 1));
    }


    /**
     * This function check if the inventory is full
     * @param inventory
     * @return
     */
    public static boolean isInventoryFull(Inventory inventory){
        return inventory.firstEmpty() == -1;
    }


    /**
     * This function set a Host
     * @param player
     */
    public static synchronized void setHost(String player){
        HOST = player;
    }


    /**
     * This function return a Host
     * @return
     */
    public static String getHost(){
        return HOST;
    }

    /**
     * This function return a text parsed by Minecraft colors
     * @param text
     * @return
     */
    public static String translator(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    /**
     * This function remove or delete the world folder that contains data
     * @param path
     */
    public static boolean worldRemover(File path){
        Api.sendConsole("&d&lUHC> &fCache refresh");

        if(path.exists()) {
            File[] files = path.listFiles();

            for (File file : files != null ? files : new File[0])
                if (file.isDirectory()) worldRemover(file);
                else file.delete();
        }

        return path.delete();
    }


    /**
     * This function return a count of Online players
     * @return
     */
    public static int getOnline(){
        return Bukkit.getOnlinePlayers().size();
    }


    /**
     * This function return a collection of players online
     * @return
     */
    public static Collection<? extends Player> getOnlinePlayers(){
        return Bukkit.getOnlinePlayers();
    }


    /**
     * @deprecated
     * This function throw Sync TaskLater
     * @param runnable
     * @param ticks
     * @return
     */
    @Deprecated public static BukkitTask runTaskLater(BukkitRunnable runnable, long ticks){
        return Bukkit.getServer().getScheduler().runTaskLater(
                Main.instance, runnable, ticks
        );
    }


    /**
     * This function set state to this plugin
     * @param state
     */
    public static synchronized void setState(State state){
        Api.state = state;
    }


    /**
     * This function return a state to this plugin
     * @return
     */
    public static State getState(){
        return state;
    }


    /**
     * This function return a game time lapsed in seconds
     * @return
     */
    public static int getTimeInSecondsLapsed(){
        final String result = ScoreTask.getTime().replace(":", "");
        return Integer.parseInt(result.substring(2));
    }


    /**
     * This function return a game time lapsed in minutes
     * @return
     */
    public static int getTimeInMinutesLapsed(){
        final String result = ScoreTask.getTime().replace(":", "");
        return Integer.parseInt(result.substring(0, 1));
    }


    /**
     * This function return a config manager
     * @return
     */
    public static ConfigManager getConfigManager(){
        return ConfigManager.instance;
    }


    /**
     * This function hide a player for other player
     * @param player
     * @param playerUNotSee
     */
    public static void hidePlayer(Player player, Player playerUNotSee){
        player.hidePlayer(playerUNotSee);
    }


    /**
     * This function unhide a player for other player
     * @param player
     * @param playerUSee
     */
    public static void unhidePlayer(Player player, Player playerUSee){
        player.showPlayer(playerUSee);
    }


    /**
     * This function return a Data instance
     * @return
     */
    public static Data getDataManager(){
        return Data.instance;
    }


    /** OTHERS **/

    /**
     * This function rescue a json from url
     * @param url
     * @return
     */
    private static JsonObject getJson(String url){
        try {
            URL cUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();

            request.connect();

            JsonParser jsonParser = new JsonParser();
            InputStream stream = (InputStream) request.getContent();
            JsonElement response = jsonParser.parse(new InputStreamReader(stream));

            return response.getAsJsonObject();
        }
        catch (Exception ex){
            if (getConfigManager().getDebugMode()) ex.printStackTrace();
            return null;
        }
    }


}
