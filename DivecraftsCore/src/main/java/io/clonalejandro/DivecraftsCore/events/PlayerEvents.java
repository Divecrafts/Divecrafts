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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class PlayerEvents implements Listener {

    private final Main plugin;
    private final List<String> nulledWords = new ArrayList<>();
    private final List<String> bannedCmds = Arrays.asList("/plugins", "/version", "/ver", "/me");

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
        Utils.loadPermissions(e.getPlayer());
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

        SServer.afkTasks.get(u).cancel();
        SServer.afkTasks.remove(u);
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

        SServer.afkTasks.get(u).cancel();
        SServer.afkTasks.remove(u);
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
        checkAfk(e.getPlayer());
        if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractAtEntityEvent e) {
        final SUser u = SServer.getUser(e.getPlayer());

        checkAfk(e.getPlayer());

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
        if (e.toWeatherState() && !Bukkit.getPluginManager().isPluginEnabled("Essentials")) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMoves(PlayerMoveEvent event){
        checkAfk(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        checkAfk(event.getPlayer());

        event.setMessage(event.getMessage().replace("%", "%%"));

        final Player player = event.getPlayer();
        final SUser user = SServer.getUser(player);
        final SCmd.Rank rank = user.getUserData().getRank();
        final String[] domains = new String[]{".com", ".net", ".es", ".org", ".com.ar", ".ar", ".mx"};
        final boolean containsDomain = String.join("", Arrays.asList(domains)).contains(event.getMessage());

        if (!user.getUserData().getChat()) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Chat.desabilitado"));
            event.setCancelled(true);
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            final SUser mUser = SServer.getUser(p.getUniqueId());
            if (!mUser.getUserData().getChat()) event.getRecipients().remove(p);
        }

        for (String word : nulledWords) {
            if (event.getMessage().contains(word)) {
                user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Chat.noips"));
                event.setCancelled(true);
            }
        }

        for (String domain : domains){
            if (event.getMessage().contains(domain)){
                user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Chat.noips"));
                event.setCancelled(true);
            }
        }

        if (rank.getRank() > 0) event.setFormat(Utils.colorize(player.getDisplayName() + ": &f" + event.getMessage()));
        else event.setFormat(player.getDisplayName() + ": §7" + event.getMessage());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        final Player player = event.getPlayer();
        final SUser user = SServer.getUser(event.getPlayer());

        if (user.getUserData().getRank().getRank() < SCmd.Rank.SMOD.getRank()){
            if (isBannedCmd(event.getMessage().toLowerCase())){
                player.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.cmdnopuedes"));
                event.setCancelled(true);
            }
        }
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
            catch (Exception ignored){}
        }, 4);
    }

    private void checkDisguise(SUser user){
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!user.getUserData().getDisguise().equals(""))
                new Disguise(user, user.getUserData().getDisguise());
        }, 2 * 20L);
    }

    private boolean isBannedCmd(String str){
        for (String cmd : bannedCmds)
            if (str.contains(cmd))
                return true;
        return false;
    }

    private void checkAfk(Player player){
        final SUser user = SServer.getUser(player);

        if (!SServer.afkMode.contains(user)){
            if (SServer.afkTasks.get(user) != null) {
                SServer.afkTasks.get(user).cancel();
                SServer.afkTasks.remove(user);
            }

            final BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                int minutes = 5;
                if (user.getPlayer() != null && user.getPlayer().isOnline()) {
                    if (Bukkit.getPluginManager().isPluginEnabled("Essentials"))
                        user.getPlayer().kickPlayer(Languaje.getLangMsg(user.getUserData().getLang(), "Global.afkKick").replace("%tiempo%", minutes + "min"));
                    else {
                        user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Global.afkLimbo").replace("%tiempo%", minutes + "min"));
                        user.sendToServer("limbo");
                    }
                }
                SServer.afkTasks.remove(user);
            }, 20 * 60 * 5);

            SServer.afkTasks.put(user, task);
        }
    }
}
