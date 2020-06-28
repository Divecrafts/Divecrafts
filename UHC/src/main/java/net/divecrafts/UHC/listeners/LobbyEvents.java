package net.divecrafts.UHC.listeners;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.BungeeMensager;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.Lobby;
import net.divecrafts.UHC.minigame.State;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.minigame.modes.ModeType;
import net.divecrafts.UHC.task.GameCountDown;
import net.divecrafts.UHC.utils.Api;
import net.divecrafts.UHC.utils.Scoreboard;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by alejandrorioscalera
 * On 17/1/18
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

public class LobbyEvents implements Listener {


    /**
     * SMALL CONSTRUCTORS
     **/

    private final Main plugin;
    private Lobby lobby;

    public LobbyEvents(Main instance) {
        plugin = instance;
    }


    /**
     * REST
     **/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Api.getState() == State.LOBBY) {
            whilePlayerCanJoin(e);
            if (Game.playerSpawn.get(e.getPlayer()) == null) {
                final Location spawn = Arena.genRandomSpawn(63);
                Game.playerSpawn.put(e.getPlayer(), spawn);
                spawn.getChunk().load();
            }
        }
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        event.setCancelled(Api.getState() == State.LOBBY && !event.getInventory().getTitle().equalsIgnoreCase("Mode"));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getMaterial() == Material.AIR) return;
            if (event.getItem() == null) return;

            final SUser user = SServer.getUser(event.getPlayer());

            switch (event.getMaterial()){
                case WATCH:
                    if (user.getUserData().getRank().getRank() >= SCmd.Rank.KRAKEN.getRank())
                        if (!Api.PLAYERS_VOTED.contains(user.getPlayer()))
                            user.getPlayer().openInventory(getVoteInventory(user.getPlayer()));
                        else user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.yavotaste"));
                    else user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.cmdnopuedes"));
                    break;
                case BED:
                    final int time = 3;
                    event.getPlayer().sendMessage(
                            Languaje.getLangMsg(
                                    user.getUserData().getLang(), "Global.bedlobbymsg").replace("%tiempo%", String.valueOf(time)
                            )
                    );
                    Api.PLAYERS_WAITING_TELEPORT.put(user.getPlayer(), Bukkit.getScheduler().runTaskLater(Main.instance, () -> BungeeMensager.conectarA(event.getPlayer(), "lobby"), 20L * time));
                    break;
            }
        }

        event.setCancelled(Api.getState() == State.LOBBY);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (Api.getState() == State.LOBBY && event.getClickedInventory() != null && event.getClickedInventory().getTitle().equalsIgnoreCase("Mode")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta() == null) return;

            final String modeStr = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            final ModeType mode = ModeType.valueOf(modeStr.toUpperCase());
            final SUser user = SServer.getUser((Player) event.getWhoClicked());
            final String name = String.valueOf(mode.toString().charAt(0)).toUpperCase() + mode.toString().substring(1).toLowerCase();

            if (Api.SELECTED_MODES.contains(mode))
                event.getWhoClicked().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.currentselected"));
            else {
                Api.SELECTED_MODES.add(mode);
                Api.PLAYERS_VOTED.add(user.getPlayer());

                addModeToScoreboard(mode);

                event.getWhoClicked().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.modeselected").replace("%modo%", name));

                Bukkit.getOnlinePlayers().forEach(p -> {
                    final SUser tempUser = SServer.getUser(p);
                    p.sendMessage(Languaje.getLangMsg(tempUser.getUserData().getLang(), "UHC.brmode")
                            .replace("%mode%", name)
                            .replace("%player%", user.getPlayer().getDisplayName())
                    );
                });
            }

            Bukkit.getScheduler().runTask(Main.instance, () -> {
                resetPlayer(user.getPlayer());
                user.getPlayer().closeInventory();
            });
        }

        event.setCancelled(Api.getState() == State.LOBBY);
    }

    @EventHandler
    public void onPlayerMoves(PlayerMoveEvent event) {
        if (Api.getState() == State.LOBBY && Api.PLAYERS_WAITING_TELEPORT.get(event.getPlayer()) != null)
            Api.PLAYERS_WAITING_TELEPORT.get(event.getPlayer()).cancel();
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent e) {
        if (Api.getState() == State.LOBBY)
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent e) {
        if (Api.getState() == State.LOBBY)
            e.getPlayer().teleport(lobby.getLocation());
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(Api.getState() == State.LOBBY);
    }

    @EventHandler
    public void onPickItem(PlayerPickupItemEvent event) {
        event.setCancelled(Api.getState() == State.LOBBY);
    }

    @EventHandler
    public void onVoid(PlayerMoveEvent event) {
        if (Api.getState() == State.LOBBY && event.getFrom().getY() < 5)
            event.getPlayer().teleport(new Lobby().getLocation());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (Api.getState() == State.LOBBY) onQuit();
        e.setQuitMessage(Api.NULL);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (Api.getState() == State.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onDamageByItem(EntityDamageByEntityEvent event) {
        if (Api.getState() == State.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        if (Api.getState() == State.LOBBY) onQuit();
        e.setLeaveMessage(Api.NULL);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        final boolean isDefaultWorld = e.getPlayer().getWorld().getName().equalsIgnoreCase("world");

        if (Api.getState() == State.LOBBY && isDefaultWorld)
            e.setCancelled(true);
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if ((Api.getState() == State.LOBBY || Api.getState() == State.ENDING) &&
                e.getEntityType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }


    /** OTHERS **/

    /**
     * This function execute the onPlayerJoin order while state is "Lobby"
     *
     * @param event
     */
    private void whilePlayerCanJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final SUser user = SServer.getUser(player);

        lobby = new Lobby();

        event.setJoinMessage(null);

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.join").replace("%player%", player.getDisplayName())));

        Scoreboard.lobbyScoreboard(player); // Set scoreboard to a player join
        Scoreboard.updateScoreboard("onlineplayers", Api.translator("&f" + (Api.getOnline())));

        resetPlayer(player);

        if (lobby.getLocation() != null)
            Bukkit.getScheduler().runTask(Main.instance, () -> player.teleport(lobby.getLocation()));

        if (Api.getOnline() == Api.getConfigManager().getPlayersForStart())
            startCountDown();
    }


    /**
     * This function execute the onPlayerKickEvent order while state is "Lobby"
     */
    private void onQuit() {
        Scoreboard.updateScoreboard("onlineplayers", Api.translator("&f" + (Api.getOnline() - 1)));
    }


    /**
     * This function start a game count down
     */
    private void startCountDown() {
        new GameCountDown(plugin, false).runTaskTimer(plugin, 1L, 20L);
    }


    private ItemStack getVoteSelector(SUser user) {
        final ItemStack item = new ItemStack(Material.WATCH);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.voteselector"));
        meta.setLore(new ArrayList<>());

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack getBedLobby(SUser user) {
        final ItemStack item = new ItemStack(Material.BED);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Languaje.getLangMsg(user.getUserData().getLang(), "Global.bedlobby"));
        meta.setLore(new ArrayList<>());

        item.setItemMeta(meta);

        return item;
    }

    private Material resolverVoteMaterial(ModeType mode) {
        switch (mode) {
            default:
                return Material.STONE;
            case SOUP:
                return Material.MUSHROOM_SOUP;
            case OPUHC:
                return Material.GOLDEN_APPLE;
            case BOWLESS:
                return Material.BOW;
            case NOCLEAN:
                return Material.BARRIER;
            case COALLESS:
                return Material.COAL;
            case CUTCLEAN:
                return Material.SHEARS;
            case FIRELESS:
                return Material.BLAZE_POWDER;
            case IRONLESS:
                return Material.IRON_INGOT;
            case GOLDLESS:
                return Material.GOLD_INGOT;
            case VANILLAP:
                return Material.LEATHER_CHESTPLATE;
            case HORSELESS:
                return Material.SADDLE;
            case TIMEBOMB:
                return Material.TNT;
            case BOUNTYHUNTER:
                return Material.MOB_SPAWNER;
            case AIRDROPS:
                return Material.CHEST;
            case DIAMONDLESS:
                return Material.DIAMOND;
            case BAREBONES:
                return Material.BONE;
            case COLDWEAPONS:
                return Material.IRON_AXE;
            case BLOODENCHANTS:
                return Material.ENCHANTMENT_TABLE;
            case NINESLOT:
                return Material.STAINED_GLASS;
            case RODLESS:
                return Material.BLAZE_ROD;
            case OREFRENZY:
                return Material.DIAMOND_ORE;
            case LIMITATIONS:
                return Material.REDSTONE;
            case BLOODDIAMONDS:
                return Material.DIAMOND_PICKAXE;
        }
    }

    private Inventory getVoteInventory(Player player) {
        final Inventory inventory = Bukkit.createInventory(player, 27, "Mode");

        for (ModeType mode : ModeType.values()) {
            final String name = String.valueOf(mode.toString().charAt(0)).toUpperCase() + mode.toString().substring(1).toLowerCase();
            final Material material = resolverVoteMaterial(mode);

            final ItemStack item = new ItemStack(material, 1);
            final ItemMeta meta = item.getItemMeta();

            String color = "&b";

            if (Api.SELECTED_MODES.contains(mode)) {
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                color = "&c";
            }

            meta.setDisplayName(Utils.colorize(color + name));
            meta.setLore(new ArrayList<>());

            item.setItemMeta(meta);

            if (Api.SELECTED_MODES.contains(mode)) {
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            }

            inventory.addItem(item);
        }

        return inventory;
    }

    private void resetPlayer(Player player) {
        final SUser user = SServer.getUser(player);

        Bukkit.getScheduler().runTask(Main.instance, () -> {
            player.getInventory().clear();
            player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0.0F);

            player.getInventory().setItem(2, getVoteSelector(user));
            player.getInventory().setItem(8, getBedLobby(user));

            player.setGameMode(GameMode.ADVENTURE);
        });
    }

    private void addModeToScoreboard(ModeType mode) {
        final String name = String.valueOf(mode.toString().charAt(0)).toUpperCase() + mode.toString().substring(1).toLowerCase();

        switch (Api.SELECTED_MODES.size()) {
            case 1:
                Scoreboard.updateScoreboard("mode1", name);
                break;
            case 2:
                Scoreboard.updateScoreboard("mode2", name);
                break;
            case 3:
                Scoreboard.updateScoreboard("mode3", name);
                break;
            case 4:
                Scoreboard.updateScoreboard("mode4", name);
                break;
        }
    }
}
