package io.clonalejandro.Essentials.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex
 * On 02/06/2020
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
 * All rights reserved for clonalejandro ©Essentials 2017/2020
 */

public class Warp {

    private String name;
    private String world;
    private double x, y, z;
    private float yaw, pitch;

    public static List<Warp> warpList = new ArrayList<>();

    public Warp(String name, String world, double x, double y, double z, float yaw, float pitch) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Warp(ResultSet rs) throws SQLException {
        this.name = rs.getString("name");
        this.world = rs.getString("world");
        this.x = rs.getDouble("x");
        this.y = rs.getDouble("y");
        this.z = rs.getDouble("z");
        this.yaw = rs.getFloat("yaw");
        this.pitch = rs.getFloat("pitch");
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public Location getLocation(){
        return new Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch());
    }
}
