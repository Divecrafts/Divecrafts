package io.clonalejandro.DivecraftsCore.events;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SBooster;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Disguise;
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

import java.util.*;

@AllArgsConstructor
public class PlayerEvents implements Listener {

    private final Main plugin;
    private final List<String> nulledWords = new ArrayList<>();
    private final List<String> bannedCmds = Arrays.asList("/plugins", "/version", "/ver", "/me");

    public HashMap<Player, PermissionAttachment> perms = new HashMap<>();
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
        checkBoosters(u);
        checkFly(u);
        //checkDisguise(u);

        if (u.getUserData().getRank().getRank() > 0)
            e.setJoinMessage(Utils.colorize(String.format("%s &ejoined the game", e.getPlayer().getDisplayName())));

        if (!plugin.getServer().getPluginManager().isPluginEnabled("Essentials")){
            u.sendHeaderAndFooter(Languaje.getLangMsg(u.getUserData().getLang(), "Global.Header"), Languaje.getLangMsg(u.getUserData().getLang(), "Global.Footer"));
            u.sendActionBar((Languaje.getLangMsg(u.getUserData().getLang(), "Global.Header")));
        }

        if (!u.isOnRank(SCmd.Rank.ADMIN) && !plugin.getServer().getPluginManager().isPluginEnabled("Essentials")) u.getPlayer().setGameMode(GameMode.SURVIVAL);
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
                if (npc.getInventory() != null)
                    u.openInventory(npc.getInventory());
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        e.setMessage(e.getMessage().replace("%", "%%"));

        final Player p = e.getPlayer();
        final SUser mu = SServer.getUser(p);
        final String[] domains = new String[]{".com", ".net", ".es", ".org", ".com.ar", ".ar", ".mx"};
        final boolean containsDomain = String.join("", Arrays.asList(domains)).contains(e.getMessage());

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

        for (String domain : domains){
            if (e.getMessage().contains(domain)){
                mu.getPlayer().sendMessage(Languaje.getLangMsg(mu.getUserData().getLang(), "Chat.noips"));
                e.setCancelled(true);
            }
        }

        SCmd.Rank r = mu.getUserData().getRank();

        if (r.getRank() > 0) e.setFormat(Utils.colorize(p.getDisplayName() + ": &f" + e.getMessage()));
        else e.setFormat(p.getDisplayName() + ": §7" + e.getMessage());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        final Player player = event.getPlayer();
        final SUser user = SServer.getUser(event.getPlayer());

        if (user.getUserData().getRank().getRank() < SCmd.Rank.SMOD.getRank()){
            if (bannedCmds.contains(event.getMessage().toLowerCase())){
                player.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.cmdnopuedes"));
                event.setCancelled(true);
            }
        }
    }

    private void loadPermissions(Player player){
        final SUser user = SServer.getUser(player);
        final int rankId = user.getUserData().getRank().getRank();
        final PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        final List<String> permissions = new ArrayList<>();

        try {
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("Essentials")){
                Class<?> clazz = Class.forName("io.clonalejandro.Essentials.objects.Permission");
                Object permission = clazz.getConstructor(UUID.class).newInstance(user.getUuid());
                List<String> individualPermissions = (List<String>) clazz.getMethod("get").invoke(permission);
                permissions.addAll(individualPermissions);
            }
        }
        catch (Exception ex){
        }

        if (rankId > 0){
            for (int i = 0; i <= rankId; i++) {
                permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", i)));
            }
        }
        else permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", rankId)));

        permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.noheredar", rankId)));
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
        task.runTaskTimerAsynchronously(plugin, 0L, 60 * 20L);

        user.getTasks().add(task);
    }

    private void checkFly(SUser user){
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                user.getPlayer().setAllowFlight(user.getUserData().getFly());
                user.getPlayer().setFlying(user.getUserData().getFly());
            }
            catch (Exception ex){
                //ignore
            }
        }, 4);
    }

    private void checkDisguise(SUser user){
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!user.getUserData().getDisguise().equals(""))
                new Disguise(user, user.getUserData().getDisguise());
        }, 2 * 20L);
    }
}
