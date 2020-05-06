package io.clonalejandro.DivecraftsCore.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CuboidZone {

    @Getter private Block corner1;
    @Getter private Block corner2;
    @Getter private World world;

    /**
     * Constructor to delimit Areas
     *
     * @param corner1
     * @param corner2
     */
    public CuboidZone(Block corner1, Block corner2) {
        if (corner1.getWorld().equals(corner2.getWorld())) {
            this.corner1 = corner1;
            this.corner2 = corner2;
            world = corner1.getWorld();
        } else {
            throw new IllegalArgumentException("La selección cuboid debe estar en el mismo mapa");
        }
    }

    /**
     * Changes all Area to this Material
     *
     * @param material
     */
    public void set(Material material) {
        toArray().forEach(b -> b.setType(material));
    }

    /**
     * Replaces one material for another
     *
     * @param oldMaterial
     * @param newMaterial
     */
    public void replace(Material oldMaterial, Material newMaterial) {
        toArray().stream().filter(b -> b.getType() == oldMaterial).forEach(b -> b.setType(newMaterial));
    }

    /**
     * Checks if the area contains a block
     *
     * @param block
     * @return if the area contains a block
     */
    public boolean contains(Block block) {
        return toArray().contains(block);
    }

    /**
     * Checks if the area contains a location
     *
     * @param loc
     * @return if the area contains a location
     */
    public boolean contains(Location loc) {
        return toLocations().contains(loc);
    }

    /**
     * Converts all the Area to List<Block>
     *
     * @return List<Block>
     */
    public List<Block> toArray() {
        List<Block> result = new ArrayList<>();

        int minX = Math.min(corner1.getX(), corner2.getX());
        int minY = Math.min(corner1.getY(), corner2.getY());
        int minZ = Math.min(corner1.getZ(), corner2.getZ());
        int maxX = Math.max(corner1.getX(), corner2.getX());
        int maxY = Math.max(corner1.getY(), corner2.getY());
        int maxZ = Math.max(corner1.getZ(), corner2.getZ());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    result.add(world.getBlockAt(new Location(world, x, y, z)));
                }
            }
        }
        return result;
    }

    /**
     * Converts all the Area to List<Location>
     *
     * @return List<Location>
     */
    public List<Location> toLocations() {
        List<Location> result = new ArrayList<>();

        toArray().forEach(b -> result.add(b.getLocation()));
        return result;
    }

    /**
     * Gets the Area as string to store it
     *
     * @return Area
     */
    @Override
    public String toString() {
        Location l = corner1.getLocation();
        String s = String.valueOf(new StringBuilder(String.valueOf(world.getName())).append("%").append(l.getBlockX()).toString()) + "%" + String.valueOf(l.getBlockY()) + "%" + String.valueOf(l.getBlockZ()) + "%0.0%0.0";
        Location l1 = corner2.getLocation();
        String s1 = String.valueOf(new StringBuilder(String.valueOf(world.getName())).append("%").append(l1.getBlockX()).toString()) + "%" + String.valueOf(l1.getBlockY()) + "%" + String.valueOf(l1.getBlockZ()) + "%0.0%0.0";
        return s + ";" + s1;
    }

    /**
     * Clears all the Area
     */
    public void clear() {
        toArray().forEach(b -> b.setType(Material.AIR));
    }
}
