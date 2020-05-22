package io.clonalejandro.DivecraftsCore.events;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SBooster;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class PlayerEvents implements Listener {

    private final Main plugin;
    public HashMap<Player, PermissionAttachment> perms = new HashMap<Player, PermissionAttachment>();
    public PlayerEvents(Main instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.ALLOWED) plugin.getMySQL().setupTable(e.getPlayer());
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        SUser u = SServer.getUser(e.getPlayer());

        // Update
        u.getUserData().setLastConnect(System.currentTimeMillis());
        u.getUserData().setTimeJoin(System.currentTimeMillis());
        u.getUserData().setIp(u.getPlayer().getAddress());
        u.save();

        Utils.updateUserColor(u);
        loadPermissions(e.getPlayer());

        e.setJoinMessage(Utils.colorize(String.format("%s &ejoined the game", e.getPlayer().getDisplayName())));

        if (!u.isOnRank(SCmd.Rank.ADMIN)) u.getPlayer().setGameMode(GameMode.SURVIVAL);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        SUser u = SServer.getUser(e.getPlayer());

        u.getUserData().setLastLocation(u.getPlayer().getLocation());
        u.getUserData().setLastConnect(System.currentTimeMillis());

        u.getUserData().setTimePlayed(u.getUserData().getTimePlayed() + System.currentTimeMillis() - u.getUserData().getTimeJoin());
        u.save();

        u.getTasks().forEach(BukkitRunnable::cancel);
        u.getTasks().clear();

        SServer.users.remove(u);

        if (SServer.getAdminChatMode().contains(u)) SServer.getAdminChatMode().remove(u);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e){
        SUser u = SServer.getUser(e.getPlayer());

        u.getUserData().setLastLocation(u.getPlayer().getLocation());
        u.getUserData().setLastConnect(System.currentTimeMillis());

        u.getUserData().setTimePlayed(u.getUserData().getTimePlayed() + System.currentTimeMillis() - u.getUserData().getTimeJoin());
        u.save();

        u.getTasks().forEach(BukkitRunnable::cancel);
        u.getTasks().clear();

        SServer.users.remove(u);

        if (SServer.getAdminChatMode().contains(u)) SServer.getAdminChatMode().remove(u);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent e) {
        //God
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (SServer.getUser(p).getUserData().getGod()) {
                p.setFireTicks(0);
                p.setHealth(p.getMaxHealth());
                p.setRemainingAir(p.getMaximumAir());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        //God
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (SServer.getUser(p).getUserData().getGod()) {
                e.setCancelled(true);
                p.setFoodLevel(20);
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        SUser u = SServer.getUser(e.getPlayer());

        switch (e.getCause()) {
            case COMMAND:
            case PLUGIN:
                u.getUserData().setLastLocation(e.getFrom());
                break;
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        SUser u = SServer.getUser(e.getPlayer());

        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractAtEntityEvent e) {
        SUser u = SServer.getUser(e.getPlayer());

        if (SServer.npc.isEmpty()) {
            e.setCancelled(true);
            return;
        }

        SServer.npc.forEach(npc -> {
            if (npc.getLoc().equals(e.getRightClicked().getLocation())) {
                if (npc.getInventory() != null) u.openInventory(npc.getInventory());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) e.setCancelled(true);
    }

    ArrayList<String> nulledWords = new ArrayList<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) throws SQLException, ClassNotFoundException {
        Player p = e.getPlayer();
        SUser mu = SServer.getUser(p);

        if (!mu.getUserData().getChat()) {
            mu.getPlayer().sendMessage(Languaje.getLangMsg(mu.getUserData().getLang(), "Chat.desabilitado"));
            e.setCancelled(true);
        }
        for (Player o : Bukkit.getOnlinePlayers()) {
            SUser mo = SServer.getUser(o.getUniqueId());
            if (!mo.getUserData().getChat()) e.getRecipients().remove(o);
        }

        for (String word : nulledWords) {
            if (e.getMessage().contains(word)) {
                mu.getPlayer().sendMessage(Languaje.getLangMsg(mu.getUserData().getLang(), "Chat.noips"));
                e.setCancelled(true);
            }
        }

        if (e.getMessage().contains(".")) {
            mu.getPlayer().sendMessage(Languaje.getLangMsg(mu.getUserData().getLang(), "Chat.noips"));
            e.setCancelled(true);
        }

        SCmd.Rank r = mu.getUserData().getRank();

        if (r.getRank() > 0) {
            e.setFormat(Utils.colorize(p.getDisplayName() + ": &7" + e.getMessage()));
        } else {
            e.setFormat(p.getDisplayName() + ": §7" + e.getMessage());
        }
    }

    private void loadPermissions(Player player){
        final SUser user = SServer.getUser(player);
        final int rankId = user.getUserData().getRank().getRank();
        final PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        final List<String> permissions = new ArrayList<>();

        if (rankId > 0){
            for (int i = 0; i <= rankId; i++) {
                permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", i)));
            }
        }
        else permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", rankId)));

        permissions.forEach(permission -> attachment.setPermission(permission, true));
        perms.put(player, attachment);
    }

    private void checkBoosters(SUser user){
        final BukkitRunnable task = new BukkitRunnable(){
            @Override
            public void run() {
                if (!user.isOnline()) {
                    cancel();
                    return;
                }

                user.getUserData().getBoosters().forEach(SBooster::isExpired);
            }
        };
        task.runTaskTimer(plugin, 0L, 60 * 20L);

        user.getTasks().add(task);
    }
}
