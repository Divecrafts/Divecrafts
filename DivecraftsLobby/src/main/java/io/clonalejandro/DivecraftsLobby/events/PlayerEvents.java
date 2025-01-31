package io.clonalejandro.DivecraftsLobby.events;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.BungeeMensager;
import io.clonalejandro.DivecraftsCore.utils.Hologramas;
import io.clonalejandro.DivecraftsCore.utils.ItemMaker;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.DivecraftsLobby.Main;
import io.clonalejandro.DivecraftsLobby.api.LobbyUser;
import io.clonalejandro.DivecraftsLobby.managers.InvManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;

public class PlayerEvents implements Listener {

    public static ArrayList<Player> sela = new ArrayList<>();
    public static ArrayList<Player> click = new ArrayList<>();
    public static ArrayList<Player> aunNo = new ArrayList<>();

    private final Main plugin = Main.getInstance();

    private boolean primera = true;
    private int runId = 0;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        LobbyUser user = Main.getUser(event.getPlayer());

        if (primera){
            primera = false;
            correrRun();
        }

        user.onJoin();
        event.setJoinMessage(null);
    }

    public void correrRun(){
        //runId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::registerHologramas, 0, 400L);
    }

    private void registerHologramas() {
        for (Entity e : Hologramas.hologramas) {
            e.remove();
        }
        Hologramas.hologramas.clear();
        //Hologramas.crearHolo((BungeeMensager.getLobbies().get("lew") + BungeeMensager.getJuegos().get("ew")) + " &6In Game", new Location(Bukkit.getWorld("spawn"), -30.5, 63.6, 12.5));
        //Hologramas.crearHolo((BungeeMensager.getLobbies().get("lsw") + BungeeMensager.getJuegos().get("sw")) + " &6In Game", new Location(Bukkit.getWorld("spawn"), -35.5, 64.1, 11.5));
        //Hologramas.crearHolo(BungeeMensager.getJuegos().get("mum") + " &6In Game", new Location(Bukkit.getWorld("spawn"), -39.5, 64.6, 8.5));
        //Hologramas.crearHolo(BungeeMensager.getJuegos().get("ffa") + " &6In Game", new Location(Bukkit.getWorld("spawn"), -42.5, 65.1, 3.5));
        //Hologramas.crearHolo(BungeeMensager.getJuegos().get("uhc") + " &6In Game", new Location(Bukkit.getWorld("spawn"), -42.5, 65.1, -2.5));
        //Hologramas.crearHolo((BungeeMensager.getLobbies().get("lfc") + BungeeMensager.getJuegos().get("fc")) + " &6In Game", new Location(Bukkit.getWorld("spawn"), -39.5, 64.6, -7.5));
        //Hologramas.crearHolo(BungeeMensager.getJuegos().get("mb") + " &6In Game", new Location(Bukkit.getWorld("spawn"), -35.5, 64.1, -10.5));

        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo1()), new Location(Bukkit.getWorld("world"),-9.5,66,0.5), "world");
        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo1p2()), new Location(Bukkit.getWorld("world"),-9.5,65.7,0.5), "world");

        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo2()), new Location(Bukkit.getWorld("world"),-9.5,65,0.5), "world");
        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo2p2()), new Location(Bukkit.getWorld("world"),-9.5,64.7,0.5), "world");

        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo3()), new Location(Bukkit.getWorld("world"),-31.5,63,0.5), "world");
        Hologramas.crearHolo(Utils.colorize(Main.getInstance().getHolo3p2()), new Location(Bukkit.getWorld("world"),-31.5,62.7,0.5), "world");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        LobbyUser user = Main.getUser(event.getPlayer());

        user.onQuit();
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = (Player) event.getWhoClicked();
            String n = event.getInventory().getName();
            SUser user = SServer.getUser(p);

            if (event.getCurrentItem() == null) return;
            if (event.getClickedInventory().getTitle() == null) return;

            switch (event.getClickedInventory().getTitle()) {
                case "Lobbies":
                    final String itemName = event.getCurrentItem().getItemMeta().getDisplayName();

                    BungeeMensager.getLobbies().forEach((lobby, onlinePlayers) -> {
                        if (itemName.equalsIgnoreCase(Utils.colorize(String.format("&a%s", lobby)))){
                            BungeeMensager.conectarExacto(p, lobby);
                        }
                    });

                    break;
                case "Games":
                    switch (event.getSlot()) {
                        case 4:
                            p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.portalyaenlobby"));
                            break;
                        case 10:
                            BungeeMensager.conectarExacto(p, "survival");
                            break;
                        case 11:
                            BungeeMensager.conectarExacto(p, "ffa");
                            break;
                        case 12:
                            BungeeMensager.conectarA(p, "uhc");
                            break;
                        case 13:
                            BungeeMensager.conectarExacto(p, "skyblock");
                            break;
                            /*
                        case 24:
                            BungeeMensager.conectarA(p, "sw");
                            break;
                        case 28:
                            BungeeMensager.conectarA(p, "fc");
                            break;
                        case 30:
                            BungeeMensager.conectarExacto(p, "mum");
                            break;
                        case 32:
                            BungeeMensager.conectarExacto(p, "mb");
                            break;
                        case 34:
                            BungeeMensager.conectarA(p, "ew");
                            break;*/
                    }
                    break;
                case "Settings":
                    switch (event.getSlot()) {
                        case 1:
                        case 10:
                            if (user.getUserData().getRank().getRank() >= SCmd.Rank.MEGALODON.getRank()){
                                user.getUserData().setFly(!user.getUserData().getFly());
                                user.toggleFly();
                                InvManager.openInventory(p, InvManager.InvType.SETTINGS);
                                user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                                user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                                user.save();
                            }
                            else user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.cmdnopuedes"));
                            break;
                        case 3:
                        case 12:
                            user.getUserData().setChat(!user.getUserData().getChat());
                            InvManager.openInventory(p, InvManager.InvType.SETTINGS);
                            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                            user.save();
                            break;
                        case 5:
                        case 14:
                            user.getUserData().setPartys(!user.getUserData().getPartys());
                            InvManager.openInventory(p, InvManager.InvType.SETTINGS);
                            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                            user.save();
                            break;
                        case 7:
                        case 16:
                            if (user.getUserData().getVisible() == 2) {
                                user.getUserData().setVisible(0);
                            } else {
                                user.getUserData().setVisible(user.getUserData().getVisible() + 1);
                            }

                            switch (user.getUserData().getVisible()) {
                                case 0:
                                    user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 8).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad")).build());
                                    break;
                                case 1:
                                    user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 5).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad")).build());
                                    break;
                                case 2:
                                    user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 10).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad")).build());
                                    break;
                            }
                            user.tryHidePlayers();
                            InvManager.openInventory(p, InvManager.InvType.SETTINGS);
                            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                            user.save();
                            break;
                    }
                    user.save();
                    break;
                case "Perfil":
                    switch (event.getSlot()) {
                        case 11:
                            user.getPlayer().sendMessage(Utils.colorize("&eRanks, Crates, Coins and more:"));
                            user.getPlayer().sendMessage(Utils.colorize("&ahttps://shop.divecrafts.net"));
                            user.getPlayer().closeInventory();
                            break;
                        case 13:
                            InvManager.openInventory(user.getPlayer(), InvManager.InvType.SETTINGS);
                            break;
                        case 15:
                            InvManager.openInventory(p, InvManager.InvType.STATS);
                            break;
                        case 30:
                            InvManager.openInventory(p, InvManager.InvType.SOCIAL);
                            break;
                        case 32:
                            InvManager.openInventory(p, InvManager.InvType.IDIOMAS);
                            break;
                    }
                    break;
                case "Social":
                    switch (event.getSlot()) {
                        case 20:
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Bienvenida.discord"));
                            user.getPlayer().closeInventory();
                            break;
                        case 22:
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Varios.proximamente"));
                            user.getPlayer().closeInventory();
                            break;
                        case 24:
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Bienvenida.twitter"));
                            user.getPlayer().closeInventory();
                            break;
                    }
                    break;
                case "Language":
                    switch (event.getSlot()) {
                        case 20:
                            user.getUserData().setLang(Languaje.Lang.ES.getId());
                            InvManager.openInventory(p, InvManager.InvType.IDIOMAS);
                            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                            user.save();
                            break;
                        case 24:
                            user.getUserData().setLang(Languaje.Lang.EN.getId());
                            InvManager.openInventory(p, InvManager.InvType.IDIOMAS);
                            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                            user.save();
                            break;
                    }
                    break;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getMaterial() == Material.AIR) return;
        if (event.getItem() == null) return;
        SUser u = SServer.getUser(event.getPlayer());

        /*if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            final LobbyUser u = StylusLobby.getUser(event.getPlayer());
            final Cosmetic c = plugin.getCosmeticManager().getCosmeticByMaterial(event.getMaterial());
            if (c == null) return;
            switch (c.getType()) {
                case GADGET:
                    c.setUser(u);
                    ((Gadget)c).onClick();
                    break;
                default:
                    break;
            }
        }*/

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            switch (event.getMaterial()) {
                case COMPASS:
                    InvManager.openInventory(event.getPlayer(), InvManager.InvType.GAMES);
                    break;
                case SKULL_ITEM:
                    InvManager.openInventory(event.getPlayer(), InvManager.InvType.PROFILE);
                    break;
                case NETHER_STAR:
                    InvManager.openInventory(event.getPlayer(), InvManager.InvType.LOBBIES);
                    break;
                case INK_SACK:
                    SUser user = SServer.getUser(event.getPlayer());
                    if (user.getUserData().getVisible() == 2) {
                        user.getUserData().setVisible(0);
                    } else {
                        user.getUserData().setVisible(user.getUserData().getVisible() + 1);
                    }
                    switch (user.getUserData().getVisible()) {
                        case 0:
                            user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 8).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad").split("\n")).build());
                            break;
                        case 1:
                            user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 5).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad").split("\n")).build());
                            break;
                        case 2:
                            user.getPlayer().getInventory().setItem(7, new ItemMaker(Material.INK_SACK, 1, (byte) 10).setName(Languaje.getLangMsg(user.getUserData().getLang(), "Itemnames.visibilidad")).setLore(Languaje.getLangMsg(user.getUserData().getLang(), "Itemlores.visibilidad").split("\n")).build());
                            break;
                    }
                    user.tryHidePlayers();
                    user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ORB_PICKUP, 1F, 1F);
                    user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
                    user.save();
                    break;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getY() < 10) {
            event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
        }
    }

    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.setKeepInventory(true);
        event.setKeepLevel(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamageByItem(EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickItem(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onNpcRightClick(NPCRightClickEvent e){
        Player p = e.getClicker();
        NPC npc = e.getNPC();
        switch (npc.getId()){
            case 1:
                BungeeMensager.conectarExacto(p, "ffa");
                break;
            case 2:
                BungeeMensager.conectarExacto(p, "uhc");
                break;
            case 3:
                BungeeMensager.conectarA(p, "sw");
                break;
            case 4:
                BungeeMensager.conectarA(p, "fc");
                break;
            case 5:
                BungeeMensager.conectarExacto(p, "mum");
                break;
            case 6:
                BungeeMensager.conectarExacto(p, "mb");
                break;
            case 7:
                BungeeMensager.conectarA(p, "ew");
                break;
        }
    }

}
