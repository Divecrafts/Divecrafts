package net.divecrafts.UHC.utils;

import net.divecrafts.UHC.Main;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by alejandrorioscalera
 * On 16/1/18
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

public final class ConfigManager {


    /** SMALL CONSTRUCTORS **/

    private final FileConfiguration configuration = Main.instance.getConfig();
    public static ConfigManager instance;

    public ConfigManager(){
        instance = this;
    }


    /** REST **/

    /**
     * This function return a debug mode for this plugin
     * @return
     */
    public Boolean getDebugMode(){
        return (Boolean) configuration.get("Debug");
    }


    /**
     * This function return a time count down of this game in seconds
     * @return
     */
    public Integer getTimeCountDown(){
        return (Integer) configuration.get("Game.CountDown");
    }


    /**
     * This function return a Gamemode for a Scoreboard
     * @return
     */
    public String getGameScore(){
        return (String) configuration.get("Score.Gamemode");
    }


    /**
     * This function return a min players for game start
     * @return
     */
    public Integer getPlayersForStart(){
        return (Integer) configuration.get("Game.MinPlayers");
    }


    /**
     * This function return a mode of teams in this game
     * @return
     */
    public String getTeamMode(){
        return (String) configuration.get("Game.Mode");
    }


    /**
     * This function return a lobby location world
     * @return
     */
    public String getLobbyWorld(){
        return (String) Api.getDataManager().get("Lobby.World");
    }


    /**
     * This function return a lobby location x
     * @return
     */
    public Double getLobbyX(){
        return (Double) Api.getDataManager().get("Lobby.X");
    }


    /**
     * This function return a lobby location y
     * @return
     */
    public Double getLobbyY(){
        return (Double) Api.getDataManager().get("Lobby.Y");
    }


    /**
     * This function return a lobby location z
     * @return
     */
    public Double getLobbyZ(){
        return (Double) Api.getDataManager().get("Lobby.Z");
    }


    /**
     * This function return a lobby location yaw
     * @return
     */
    public Float getLobbyYaw(){
        return Api.getDataManager().getFloat("Lobby.Yaw");
    }


    /**
     * This function return a lobby location pitch
     * @return
     */
    public Float getLobbyPitch(){
        return Api.getDataManager().getFloat("Lobby.Pitch");
    }


    /**
     * This function return a mob limit of default world uhc
     * @return
     */
    public Integer getWorldMobLimit(){
        return (Integer) configuration.get("Game.UhcWorldMobLimit");
    }


    /**
     * This function return a mob limit of nether world uhc
     * @return
     */
    public Integer getNetherMobLimit(){
        return (Integer) configuration.get("Game.UhcNetherMobLimit");
    }


    /**
     * This function return a time for throw new AirDrop
     * @return
     */
    public Integer getAirDropTime(){
        return (Integer) configuration.get("Game.AirDropTime");
    }


    /**
     * This function return a center X coord of the uhc map
     * @return
     */
    public Double getCenterX() {
        return (Double) configuration.get("Map.CenterX");
    }


    /**
     * This function return a center Z coord of the uhc map
     * @return
     */
    public Double getCenterZ(){
        return (Double) configuration.get("Map.CenterZ");
    }


    /**
     * This function return a radius of the uhc map
     * @return
     */
    public Integer getWidth(){
        return (Integer) configuration.get("Map.Width");
    }


    /**
     * This function return a file configuration
     * @return
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }


}
