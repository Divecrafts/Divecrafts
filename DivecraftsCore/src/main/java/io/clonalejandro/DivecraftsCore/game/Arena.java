package io.clonalejandro.DivecraftsCore.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

public class Arena {

    private final String game;

    @Getter private final ArenaData arenaData;

    @Getter private List<Location> arenaSpawnList;
    @Getter private final ArrayList<Block> blocksPlaced = new ArrayList<>();
    @Getter private final ArrayList<Block> blocksRemoved = new ArrayList<>();

    private final Random r = new Random();

    /**
     * Default constructor
     *
     * @param game The game type
     */
    public Arena(String game) {
        this.game = game;
        arenaData = loadArena();

        arenaSpawnList = arenaData.getSpawns();
        if (arenaData.getMinPlayers() < 2) arenaData.setMinPlayers(2);
    }

    /**
     * Internal Method. Reads the JSON to load the Data
     *
     * @return data from the JSON
     */
    private Map<String, List<String>> loadGameArenas() {
        try {
            Type mapType = new TypeToken<Map<String, List<String>>>() {}.getType();
            final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(SServer.defURL + "gameList.json").openStream()));
            final StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            return new Gson().fromJson(sb.toString(), mapType);
        } catch (IOException e) {
            e.printStackTrace();
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
            return new HashMap<>();
        }
    }

    /**
     * Internal Method. Generates the ArenaData
     *
     * @return ArenaData
     *
     * @see ArenaData
     */
    private ArenaData loadArena() {
        try {
            String randomArena = loadGameArenas().get(game).get(new Random().nextInt(loadGameArenas().get(game).size()));
            Gson g = new GsonBuilder().create();
            final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(SServer.defURL + "games/" + game + "/" + randomArena).openStream()));
            final StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) sb.append(line);
            br.close();

            return g.fromJson(sb.toString(), ArenaData.class);
        } catch (IOException e) {
            e.printStackTrace();
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
            return new ArenaData();
        }
    }

    /**
     * Teleports a player to the Arena
     *
     * @param player The user to teleport
     */
    public void teleportToArena(SUser player) {
        teleportToArena(player, false);
    }

    /**
     * Teleports a player to the Arena and delete the spawn from the list
     *
     * @param player The user to teleport
     * @param delete If the spawn is deleted or not
     */
    public void teleportToArena(SUser player, boolean delete) {
        int rd = r.nextInt(arenaSpawnList.size());
        player.teleport(arenaSpawnList.get(rd));
        if (delete) arenaSpawnList.remove(rd);
    }

    /**
     * Teleports a player to the Lobby (from the game)
     *
     * @param player The user to teleport
     */
    public void teleportToLobby(SUser player) {
        player.teleport(arenaData.getLobby());
    }

    /**
     * Restores the Arena
     */
    @Deprecated
    public void restoreArena() {
        blocksRemoved.forEach(b -> b.getLocation().getBlock().setType(b.getType()));
        blocksPlaced.forEach(b -> b.setType(Material.AIR));
    }
}
