package net.divecrafts.UHC.minigame.arena.worlds;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alejandrorioscalera
 * On 2019-07-08
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
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2019
 */

public class Border {


    /** SMALL CONSTRUCTORS **/

    private World world;
    private WorldBorder worldBorder;

    public Border(World world){
        this.world = world;
        this.worldBorder = world.getWorldBorder();
    }


    /** REST **/

    /**
     * This function set the center of the world border
     * @param location
     */
    public void setCenter(final Location location){
        worldBorder.setCenter(location);
    }


    /**
     * This function set the size of the worldBorder
     * @param size
     */
    public void setSize(final double size){
        worldBorder.setSize(size);
    }


    /**
     * This function return the size of the worldBorder
     * @return
     */
    public double getSize(){
        return worldBorder.getSize();
    }


    /**
     * This function returns the worldborder center
     * @return
     */
    public Location getCenter(){
        return worldBorder.getCenter();
    }


    /**
     * This function resolves the player distance between the world border
     * @param player
     * @return
     */
    public int resolveDistanceBetweenPlayerByDirection(final Player player){
        final Location worldBorder = getCenter();
        final Location playerLocation = player.getLocation();
        final Vector playerDir = playerLocation.getDirection();

        worldBorder.setY(0);
        playerLocation.setY(0);

        if (playerDir.getX() < 0){
            worldBorder.add(playerLocation.getX(), 0, getSize() / -2.0);
            return (int) Math.round(playerLocation.distance(worldBorder));
        }
        else if (playerDir.getX() > 0){
            worldBorder.add(playerLocation.getX(), 0, getSize() / 2.0);
            return (int) Math.round(playerLocation.distance(worldBorder));
        }
        else if (playerDir.getZ() < 0){
            worldBorder.add(getSize() / -2.0, 0, playerLocation.getZ());
            return (int) Math.round(playerLocation.distance(worldBorder));
        }
        else if (playerDir.getZ() > 0){
            worldBorder.add(getSize() / 2.0, 0, playerLocation.getZ());
            return (int) Math.round(playerLocation.distance(worldBorder));
        }

        return -1;
    }

    /**
     * This function resolves the player distance between the world border
     * @param player
     * @return
     */
    public int resolveDistanceBetweenPlayer(final Player player){
        final Location worldBorder = getCenter();
        final Location playerLocation = player.getLocation();

        final List<Integer> values = new ArrayList<>();

        worldBorder.setY(0);
        playerLocation.setY(0);

        worldBorder.add(playerLocation.getX(), 0, getSize() / -2.0);
        values.add((int) Math.round(playerLocation.distance(worldBorder)));

        worldBorder.add(playerLocation.getX(), 0, getSize() / 2.0);
        values.add((int) Math.round(playerLocation.distance(worldBorder)));

        worldBorder.add(getSize() / -2.0, 0, playerLocation.getZ());
        values.add((int) Math.round(playerLocation.distance(worldBorder)));

        worldBorder.add(getSize() / 2.0, 0, playerLocation.getZ());
        values.add((int) Math.round(playerLocation.distance(worldBorder)));

        return values.stream()
                .sorted()
                .collect(Collectors.toList())
                .get(0);
    }
}
