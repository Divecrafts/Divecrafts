package net.divecrafts.skywars.game;

import lombok.Getter;
import lombok.Setter;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.game.Arena;
import io.clonalejandro.DivecraftsCore.game.Game;
import io.clonalejandro.DivecraftsCore.game.GameState;
import io.clonalejandro.DivecraftsCore.kits.Kit;
import io.clonalejandro.DivecraftsCore.utils.ItemMaker;

import net.divecrafts.skywars.SkyWars;
import net.divecrafts.skywars.api.SkyIsland;
import net.divecrafts.skywars.api.SkyUser;
import net.divecrafts.skywars.game.votes.*;
import net.divecrafts.skywars.tasks.LobbyTask;
import net.divecrafts.skywars.utils.Menus;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameArena extends Game {

    @Getter
    private SkyWars plugin = SkyWars.getInstance();

    @Getter
    private ArrayList<SkyIsland> islands;

    @Getter
    private Arena arena;

    @Getter
    @Setter
    private boolean damageOnFall = true;

    private Menus menus;

    @Getter @Setter
    private static int difficulty = 1; // 0-> Facil | 1-> Normal | 2-> Dificil

    public GameArena() {
        super(SServer.GameID.SKYWARS, "SkyWars");

        islands = new ArrayList<>();

        menus = new Menus(plugin);

        arena = new Arena("sw");
        setArena(arena);

        correctMap();
    }

    @Override
    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player)) return;
        SkyUser u = SkyWars.getUser((Player) e.getEntity());
        SkyUser killer = SkyWars.getUser((Player) e.getDamager());

        if (u.getHealth() - e.getDamage() <= 0) {
            u.death();

            killer.getUserData().addKill(SServer.GameID.SKYWARS);
        }
    }

    @Override
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        SkyUser u = SkyWars.getUser((Player) e.getEntity());
        if (GameState.state == GameState.LOBBY) e.setCancelled(true);
        if (!damageOnFall) e.setCancelled(true);
    }

    @Override
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {

    }

    @Override
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final SkyUser user = SkyWars.getUser(e.getPlayer());

        user.setLobbyPlayer();
        arena.teleportToLobby(user);

        final SkyIsland island = new SkyIsland(genRandomSpawn());
        island.setOwner(user.getUuid());
        island.setId(String.valueOf(islands.size()));
        island.buildCapsule(Material.GLASS);

        islands.add(island);

        user.getInventory().setItem(1, new ItemMaker(Material.EMERALD).setName("&aVotar &7(Haz Click)").buildComplete());
        user.getPlayer().updateInventory();

        if (canStart()) new LobbyTask(plugin).runTaskTimer(plugin, 0, 20);
    }

    @Override
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        final SkyUser user = SkyWars.getUser(e.getPlayer());
        final List<SkyIsland> search = islands.stream()
                .filter(is -> is.getOwner() == user.getUuid())
                .collect(Collectors.toList());

        if (search.size() == 0) return;

        final SkyIsland island = search.get(0);

        getPlayersInGame().remove(user);
        island.destroyCapsule();
        arena.getArenaSpawnList().add(island.getSpawn());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        final SkyUser user = SkyWars.getUser(e.getPlayer());
        final List<SkyIsland> search = islands.stream()
                .filter(is -> is.getOwner() == user.getUuid())
                .collect(Collectors.toList());

        if (search.size() == 0) return;

        final SkyIsland island = search.get(0);

        getPlayersInGame().remove(user);
        island.destroyCapsule();
        arena.getArenaSpawnList().add(island.getSpawn());
    }

    @Override
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final SkyUser user = SkyWars.getUser(e.getPlayer());

        if (e.getItem() == null) return;
        if (GameState.state != GameState.LOBBY) {
            e.setCancelled(false);
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem().getType() == Material.EMERALD) {
                user.closeInventory();
                user.openInventory(menus.createMenu(Menus.MenuType.MENU));
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(GameState.state == GameState.LOBBY);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        this.getPlayersInGame().remove(SkyWars.getUser(e.getEntity()));
        Bukkit.getScheduler().runTask(plugin, () -> this.arena.teleportToLobby(SkyWars.getUser(e.getEntity())));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Bukkit.getScheduler().runTask(plugin, () -> this.arena.teleportToLobby(SkyWars.getUser(e.getPlayer())));
    }

    @EventHandler
    public void onInteractInv(InventoryClickEvent e) {
        final SkyUser user = SkyWars.getUser((Player) e.getWhoClicked());
        if (GameState.state != GameState.LOBBY) return;

        switch (e.getInventory().getTitle()) {
            case "Menu de Skywars": {
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 1:
                        user.closeInventory();
                        user.openInventory(menus.createMenu(Menus.MenuType.DIFF));
                        break;
                    case 4:
                        user.closeInventory();
                        user.openInventory(menus.createMenu(Menus.MenuType.TIME));
                        break;
                    case 7:
                        user.closeInventory();
                        user.openInventory(menus.createMenu(Menus.MenuType.WHEATER));
                        break;
                }
                return;
            }
            case "Dificultad": {
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 1:
                        new VoteMode(ModeType.EASY);//TODO: Alert
                        break;
                    case 4:
                        new VoteMode(ModeType.NORMAL);//TODO: Alert
                        break;
                    case 7:
                        new VoteMode(ModeType.HARD);//TODO: Alert
                        break;
                }
                user.closeInventory();
                return;
            }
            case "Horario": {
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 1:
                        new VoteTime(TimeType.DAY);//TODO: Alert
                        break;
                    case 4:
                        new VoteTime(TimeType.AFTERNOON);//TODO: Alert
                        break;
                    case 7:
                        new VoteTime(TimeType.NIGHT);//TODO: Alert
                        break;
                }
                user.closeInventory();
                user.getWorld().setGameRuleValue("doDaylightCycle", "false");
                return;
            }
            case "Clima": {
                e.setCancelled(true);
                switch (e.getSlot()) {
                    case 2:
                        new VoteBiome(BiomeType.CLEAR);//TODO: Alert
                        break;
                    case 6:
                        new VoteBiome(BiomeType.RAINING);//TODO: Alert
                        break;
                }
                user.closeInventory();
            }
        }

        e.setCancelled(true);
    }

    @Override
    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent e) {

    }

    @Override
    public List<Kit> availableKits() {

        Kit builder = new Kit("Builder");
        builder.addInvItem(new ItemMaker(Material.BRICK, 32).build());

        Kit sword = new Kit("Espadachín");
        sword.addInvItem(new ItemMaker(Material.IRON_SWORD).build());

        return Arrays.asList(builder, sword);
    }

    @Override
    public List<ItemStack> playerItems() {
        return null;
    }

    private Location genRandomSpawn() {
        final Location location = arena.getArenaSpawnList().get(new Random().nextInt(arena.getArenaSpawnList().size() - 1));
        arena.getArenaSpawnList().remove(location);
        return location;
    }

    public void fillChests() {
        for (Chunk chunk : arena.getArenaData().getWorld().getLoadedChunks()) {
            for (BlockState e : chunk.getTileEntities()) {
                if (e instanceof Chest) {
                    final Random r = new Random();
                    final Chest chest = (Chest) e;

                    chest.getInventory().clear();
                    Inventory inv = chest.getBlockInventory();
                    inv.clear();

                    final ArrayList<ItemStack> aleatorios = new ArrayList<>();

                    switch (difficulty) {
                        case 0:
                            int i1 = r.nextInt(plugin.getChestItems().items_basic.size());
                            int i2 = r.nextInt(plugin.getChestItems().armaduras_basic.size());
                            int i3 = r.nextInt(plugin.getChestItems().armas_basic.size());
                            int i4 = r.nextInt(plugin.getChestItems().comida_basic.size());


                            for (int i = 0; i < i1; i++)
                                aleatorios.add(plugin.getChestItems().items_basic.get(r.nextInt(plugin.getChestItems().items_basic.size())));
                            for (int i = 0; i < i2; i++)
                                aleatorios.add(plugin.getChestItems().armaduras_basic.get(r.nextInt(plugin.getChestItems().armaduras_basic.size())));
                            for (int i = 0; i < i3; i++)
                                aleatorios.add(plugin.getChestItems().armas_basic.get(r.nextInt(plugin.getChestItems().armas_basic.size())));
                            for (int i = 0; i < i4; i++)
                                aleatorios.add(plugin.getChestItems().comida_basic.get(r.nextInt(plugin.getChestItems().comida_basic.size())));
                            break;
                        case 1:
                            int m1 = r.nextInt(plugin.getChestItems().items.size());
                            int m2 = r.nextInt(plugin.getChestItems().armaduras.size());
                            int m3 = r.nextInt(plugin.getChestItems().armas.size());
                            int m4 = r.nextInt(plugin.getChestItems().comida.size());

                            for (int i = 0; i < m1; i++)
                                aleatorios.add(plugin.getChestItems().items.get(r.nextInt(plugin.getChestItems().items.size())));
                            for (int i = 0; i < m2; i++)
                                aleatorios.add(plugin.getChestItems().armaduras.get(r.nextInt(plugin.getChestItems().armaduras.size())));
                            for (int i = 0; i < m3; i++)
                                aleatorios.add(plugin.getChestItems().armas.get(r.nextInt(plugin.getChestItems().armas.size())));
                            for (int i = 0; i < m4; i++)
                                aleatorios.add(plugin.getChestItems().comida.get(r.nextInt(plugin.getChestItems().comida.size())));
                            break;
                        case 2:
                            int h1 = r.nextInt(plugin.getChestItems().items_op.size());
                            int h2 = r.nextInt(plugin.getChestItems().armaduras_op.size());
                            int h3 = r.nextInt(plugin.getChestItems().armas_op.size());
                            int h4 = r.nextInt(plugin.getChestItems().comida_op.size());

                            for (int i = 0; i < h1; i++)
                                aleatorios.add(plugin.getChestItems().items_op.get(r.nextInt(plugin.getChestItems().items_op.size())));
                            for (int i = 0; i < h2; i++)
                                aleatorios.add(plugin.getChestItems().armaduras_op.get(r.nextInt(plugin.getChestItems().armaduras_op.size())));
                            for (int i = 0; i < h3; i++)
                                aleatorios.add(plugin.getChestItems().armas_op.get(r.nextInt(plugin.getChestItems().armas_op.size())));
                            for (int i = 0; i < h4; i++)
                                aleatorios.add(plugin.getChestItems().comida_op.get(r.nextInt(plugin.getChestItems().comida_op.size())));
                            break;
                    }

                    aleatorios.forEach(is -> inv.setItem(r.nextInt(inv.getSize()), is));
                    chest.update();
                }
            }
        }
    }
}
