package net.divecrafts.skywars.api;

import lombok.Getter;
import lombok.Setter;

import net.divecrafts.skywars.SkyWars;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.UUID;

public class SkyIsland {

    @Getter ArrayList<Location> blocks = new ArrayList<>();
    @Getter @Setter UUID owner;
    @Getter Location spawn;
    @Getter @Setter String id;

    public SkyIsland(Location location){
        this.spawn = location;

    }

    public void addBlock(Block block) {
        blocks.add(block.getLocation());
    }

    public void addBlock(Location block) {
        blocks.add(block);
    }

    public void destroyCapsule() {
        blocks.forEach(loc -> loc.getBlock().setType(Material.AIR));
    }

    public static SkyIsland getIsland(UUID uuid) {
        if (SkyWars.getInstance().getGameArena().getIslands() != null || !SkyWars.getInstance().getGameArena().getIslands().isEmpty()) {
            for (SkyIsland i : SkyWars.getInstance().getGameArena().getIslands()) {
                if (i.getOwner() != null) {
                    if (i.getOwner() == uuid) {
                        return i;
                    }
                }
            }
        }
        return null;
    }

    public static SkyIsland getIsland(String id) {
        if (SkyWars.getInstance().getGameArena().getIslands() != null || !SkyWars.getInstance().getGameArena().getIslands().isEmpty()) {
            for (SkyIsland i : SkyWars.getInstance().getGameArena().getIslands()) {
                if (i.getId() == null ? id == null : i.getId().equals(id)) {
                    return i;
                }
            }
        }
        return null;
    }

    public void buildCapsule(Material material){
        buildCapsule(material, (byte) 0);
    }

    public void buildCapsule(Material material, byte data){
        //build floor
        setBlock(new Location(this.spawn.getWorld(), this.spawn.getX(), this.spawn.getY() -2, this.spawn.getZ()), material, data);

        //Corners
        for (int i = 1; i <= 3; i++){
            setBlock(new Location(this.spawn.getWorld(), this.spawn.getX(), (this.spawn.getY() - 2) + i, this.spawn.getZ() + 1), material, data);
            setBlock(new Location(this.spawn.getWorld(), this.spawn.getX(), (this.spawn.getY() - 2) + i, this.spawn.getZ() - 1), material, data);
            setBlock(new Location(this.spawn.getWorld(), this.spawn.getX() +1, (this.spawn.getY() - 2) + i, this.spawn.getZ()), material, data);
            setBlock(new Location(this.spawn.getWorld(), this.spawn.getX() -1, (this.spawn.getY() - 2) + i, this.spawn.getZ()), material, data);
        }
    }

    private void setBlock(Location location, Material material, byte data){
        final Block block = location.getBlock();

        block.setType(material);
        block.setData(data);

        blocks.add(location);
    }
}
