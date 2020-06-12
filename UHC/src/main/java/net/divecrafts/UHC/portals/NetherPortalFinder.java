package net.divecrafts.UHC.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

import static org.bukkit.World.Environment.NORMAL;
import static org.bukkit.block.BlockFace.*;

/**
 * Created by alejandrorioscalera
 * On 27/2/18
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

public final class NetherPortalFinder {


    /** SMALL CONSTRUCTORS **/

    private static final BlockFace[] BLOCK_FACES = new BlockFace[] {EAST, WEST, NORTH, SOUTH, UP, DOWN };


    /** REST **/

    /**
     * * Gets the nearest Nether portal within the specified radius in relation to the given location.
     * @param location  Center of the search radius
     * @param radius    The search radius
     * @param minHeight Minimum height of search
     * @param maxHeight Maximum height of search
     * @return Returns location in the bottom center of the nearest nether portal if found. Otherwise returns null.
     */
    public static Location locate(Location location, int radius, int minHeight, int maxHeight) {
        Set<PortalGroup> portals = new HashSet<>();
        Set<Vector> stored = new HashSet<>();
        World world = location.getWorld();

        PortalGroup nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        int yStart = location.getBlockY() - radius;
        yStart = yStart < minHeight ? minHeight : yStart;

        int yEnd = location.getBlockY() + radius;
        yEnd = yEnd > maxHeight ? maxHeight : yEnd;

        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++)
            for (int y = yStart; y <= yEnd; y += 2)
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    if (x + z % 2 == 0) break;

                    final Location loc = new Location(world, x, y, z);
                    final Vector vec = loc.toVector();

                    if (!stored.contains(vec)) {
                        final PortalGroup pg = getPortalBlocks(loc);

                        if (pg != null) if (portals.add(pg)) {
                                stored.addAll(pg.getBlocks());

                                double distanceSquared = pg.distanceSquared(vec);

                                if (distanceSquared < nearestDistance) {
                                    nearestDistance = distanceSquared;
                                    nearest = pg;
                                }
                        }
                    }
                }

        return nearest == null ? null : nearest.teleportTo();
    }


    public static Location locate(Location location) {
        World w = location.getWorld();
        World.Environment dimension = w.getEnvironment();

        int maxHeight = dimension == NORMAL ? w.getMaxHeight() - 1 : 127;

        return locate(location, 128, 1, maxHeight);
    }


    /**
     * Gets the Portal blocks that is part of the Nether portal.
     * @param loc - Location to start the getting the portal blocks.
     * @return A PortalGroup of all the found Portal blocks. Otherwise returns null. This will return null if the amount of found blocks if below 6.
     */
    public static PortalGroup getPortalBlocks(Location loc) {
        if (loc.getBlock().getType() != Material.PORTAL) return null;
        final PortalGroup pg = portalBlock(new PortalGroup(loc.getWorld()), loc);
        return pg.size() > 5 ? pg : null;
    }


    /** OTHERS **/

    private static PortalGroup portalBlock(PortalGroup group, Location loc) {
        for (BlockFace face : BLOCK_FACES) {
            Block relative = loc.getBlock().getRelative(face);
            Location relLoc = relative.getLocation();
            if (group.add(relLoc.toVector())) portalBlock(group, relLoc);
        }
        return group;
    }


}
