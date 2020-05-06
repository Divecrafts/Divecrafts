package io.clonalejandro.DivecraftsCore.game;

import lombok.Getter;
import lombok.Setter;
import io.clonalejandro.DivecraftsCore.utils.CuboidZone;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ArenaData {

    @Getter private String game;

    @Getter private int maxPlayers;
    @Getter @Setter private int minPlayers;

    @Getter private int lobbyTime;
    @Getter private int gameTime;
    @Getter private int deathMatchTime;

    private String lobbyArena;
    private String lobby;
    private String borders;
    @Getter private String[] spawns;

    @Getter private String[] worlds;

    /**
     * Gets the Lobby Area
     *
     * @return Arena of the Lobby (CuboidZone)
     */
    public CuboidZone getLobbyArea() {
        return Utils.stringToArea(lobbyArena);
    }

    /**
     * Gets the current world
     *
     * @return World
     */
    public World getWorld() {
        return getSpawns().get(0).getWorld();
    }

    /**
     * Gets the Lobby location
     *
     * @return Gets the Lobby location
     */
    public Location getLobby(){
        return Utils.stringToLocation(lobby);
    }

    /**
     * Gets the limits of the Arena
     *
     * @return limits
     */
    public Location[] getBorders() {
        Location[] locs = new Location[2];

        locs[0] = Utils.cuboidToLocation(borders, 0);
        locs[1] = Utils.cuboidToLocation(borders, 1);
        return locs;
    }

    /**
     * Gets the limits of the Arena
     *
     * @return limits
     */
    public CuboidZone getGameArea() {
        return new CuboidZone(getBorders()[0].getBlock(), getBorders()[1].getBlock());
    }

    /**
     * Gets the spawns for the Game
     *
     * @return List<Location>
     */
    public List<Location> getSpawns() {
        List<Location> locs = new ArrayList<>();

        Arrays.asList(spawns).forEach(s -> locs.add(Utils.stringToLocation(s)));
        return locs;
    }

    /**
     * Gets the name of a Random world
     *
     * @return Name of a Random world
     */
    public String getRandomNextWorld() {
        return worlds[new Random().nextInt(worlds.length)];
    }
}
