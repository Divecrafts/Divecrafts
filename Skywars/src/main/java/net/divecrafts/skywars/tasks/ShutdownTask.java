package net.divecrafts.skywars.tasks;

import io.clonalejandro.DivecraftsCore.api.SMap;
import io.clonalejandro.DivecraftsCore.game.GamesTask;

import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import net.divecrafts.skywars.SkyWars;
import net.divecrafts.skywars.api.SkyIsland;
import net.divecrafts.skywars.api.SkyUser;
import net.divecrafts.skywars.game.GameArena;

public class ShutdownTask extends GamesTask {

    private final SkyWars plugin;
    private final GameArena game;

    public ShutdownTask(SkyWars instance) {
        this.plugin = instance;
        game = plugin.getGameArena();
    }

    private int count = 10;

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach(p -> p.sendMessage(Languaje.getLangMsg(SkyWars.getUser(p).getUserData().getLang(), "SW.backtolobby").replace("%time%", String.valueOf(count))));
        if (count == 0) {
            plugin.getServer().getOnlinePlayers().forEach(p -> {
                final SkyUser user = SkyWars.getUser(p);
                p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "SW.disconnected"));
                user.sendToServer("lobby");//TODO: lsw
            });
            plugin.getGameArena().getIslands().forEach(SkyIsland::destroyCapsule);
            plugin.getServer().unloadWorld(plugin.getServer().getWorlds().get(0), false);
            end();
            plugin.getServer().shutdown();
            cancel();
        }
        --count;
    }

    @Override
    protected void end() {
        SMap map = new SMap(game.getName(), game.getArena().getArenaData().getWorld().getName());
        map.randomMap();
    }
}
