package net.divecrafts.UHC.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.*;

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

public class PortalGroup {


    /** SMALL CONSTRUCTORS **/

    private World world;
    private Set<Vector> portal;
    private HashMap<Integer, Set<Vector>> yBlock;
    private Location teleportTo;
    private double distanceSquared;
    private int bottom;


    public PortalGroup(World world) {
        this.world = world;
        portal = new HashSet<>();
        yBlock = new HashMap<>();
        bottom = Integer.MAX_VALUE;
        distanceSquared = Double.MAX_VALUE;
    }


    /** REST **/

    /**
     * Adds the Location to the PortalGroup.
     * @param vec Vector to add
     * @return if the Location was added. Otherwise false.
     */
    public boolean add(Vector vec) {
        if (vec.toLocation(world).getBlock().getType() != Material.PORTAL)
            return false;

        boolean b = portal.add(vec);

        if (b) {
            final int y = vec.getBlockY();

            if (y < bottom) bottom = vec.getBlockY();

            final Set<Vector> set = yBlock.computeIfAbsent(y, k -> new HashSet<>());
            set.add(vec);

            distanceSquared = Double.MAX_VALUE;
            teleportTo = null;
        }
        return b;
    }


    /**
     * This function return a Portal Size
     * @return
     */
    public int size() {
        return portal.size();
    }


    /**
     * This function return a PortalBlocks
     * @return
     */
    public Collection<Vector> getBlocks() {
        return Collections.unmodifiableCollection(portal);
    }


    /**
     * Gets the average distance squared of all the portal blocks.
     * @param vector vector to compare.
     * @return distance squared
     */
    public double distanceSquared(Vector vector) {
        if (distanceSquared < Double.MAX_VALUE) return distanceSquared;

        double d = 0;
        for (Vector vec : portal) d += vec.distanceSquared(vector);

        return d / portal.size();
    }


    /**
     * Gets the location to teleport an entity to the Nether portal.
     * @return The location in the bottom center of the Nether portal.
     */
    public Location teleportTo() {
        if (teleportTo != null) return teleportTo;
        if (portal.size() == 0) return null;

        final Set<Vector> bottomY = yBlock.get(bottom);
        int x = 0, z = 0;

        for (Vector loc : bottomY) {
            x += loc.getBlockX();
            z += loc.getBlockZ();
        }

        teleportTo = new Location(world, ((x / bottomY.size()) + 0.5),
                bottom, ((z / bottomY.size()) + 0.5));

        return teleportTo;
    }


    @Override
    public int hashCode() {
        return portal.hashCode();
    }


    @Override
    public boolean equals(Object object) {
        if (object instanceof PortalGroup) {
            PortalGroup pg = (PortalGroup) object;
            return portal.equals(pg.portal);
        }
        return portal.equals(object);
    }


}
