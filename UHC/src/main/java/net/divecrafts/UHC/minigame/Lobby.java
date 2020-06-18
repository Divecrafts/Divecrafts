package net.divecrafts.UHC.minigame;

import net.divecrafts.UHC.utils.Api;

import net.divecrafts.UHC.utils.clonadoc.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by alejandrorioscalera
 * On 17/1/18
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

public final class Lobby {


    /** SMALL CONSTRUCTORS **/

    private Location location;

    public Lobby(){
        if (!isDataNull()) {
            final World world = Bukkit.getWorld(
                    Api.getConfigManager().getLobbyWorld()
            );

            final double x = Api.getConfigManager().getLobbyX(),
                         y = Api.getConfigManager().getLobbyY(),
                         z = Api.getConfigManager().getLobbyZ();

            final float yaw = Api.getConfigManager().getLobbyYaw(),
                    pitch = Api.getConfigManager().getLobbyPitch();

            location = new Location(world, x, y, z, yaw, pitch);
        }
        else location = null;
    }


    /** REST **/

    /**
     * This function update the lobby location data
     * @param location
     */
    public void setLobby(final Location location){
        final String world = location.getWorld().getName();

        final double x = location.getX(),
                     y = location.getY(),
                     z = location.getZ();

        final float yaw = location.getYaw(),
                    pitch = location.getPitch();

        //Save the new location
        setConfigData("Lobby.World", world);
        setConfigData("Lobby.X", x);
        setConfigData("Lobby.Y", y);
        setConfigData("Lobby.Z", z);
        setConfigData("Lobby.Yaw", yaw);
        setConfigData("Lobby.Pitch", pitch);
    }


    /** OTHERS **/

    /**
     * This function update data in config
     * @param key
     * @param value
     */
    private void setConfigData(String key, Object value){
        Api.getDataManager().set(key, value);
    }


    /**
     * This function return is Data of Lobby location is corrupted
     * @return
     */
    private boolean isDataNull(){
        return Api.getConfigManager().getLobbyPitch() == null ||
                Api.getConfigManager().getLobbyYaw() == null ||
                Api.getConfigManager().getLobbyZ() == null ||
                Api.getConfigManager().getLobbyY() == null ||
                Api.getConfigManager().getLobbyX() == null ||
                Api.getConfigManager().getLobbyWorld() == null;
    }


    /** GETTERS **/

    @Getter public Location getLocation() {
        return location;
    }


}
