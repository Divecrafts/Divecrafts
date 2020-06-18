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
     * This function return a license for this plugin
     * @return
     */
    public String getLicense(){
        return (String) configuration.get("License");
    }


    /**
     * This function return a time count down of this game in seconds
     * @return
     */
    public Integer getTimeCountDown(){
        return (Integer) configuration.get("Game.CountDown");
    }


    /**
     * This function return a message alert for count down
     * @return
     */
    public String getCountDownMessage(){
        return (String) configuration.get("Game.CountDownMessage");
    }


    /**
     * This function return a ip for Scoreboard
     * @return
     */
    public String getIp(){
        return (String) configuration.get("Score.Ip");
    }


    /**
     * This function return a Tittle for a Scoreboard
     * @return
     */
    public String getTitleScore(){
        return (String) configuration.get("Score.Title");
    }


    /**
     * This function return a Kills for a Scoreboard
     * @return
     */
    public String getKillScore(){
        return (String) configuration.get("Score.Kills");
    }


    /**
     * This function return a Current Border for a Scoreboard
     * @return
     */
    public String getBorderScore(){
        return (String) configuration.get("Score.CurrentBorder");
    }


    /**
     * This function return a Online Players for a Scoreboard
     * @return
     */
    public String getOnlineScore(){
        return (String) configuration.get("Score.OnlinePlayers");
    }


    /**
     * This function return a Team Mode for a Scoreboard
     * @return
     */
    public String getTeamScore(){
        return (String) configuration.get("Score.Teams");
    }


    /**
     * This function return Host for a Scoreboard
     * @return
     */
    public String getHostScore(){
        return (String) configuration.get("Score.Host");
    }


    /**
     * This function return a Gamemode for a Scoreboard
     * @return
     */
    public String getGameScore(){
        return (String) configuration.get("Score.Gamemode");
    }


    /**
     * This function return a Alive Players for a Scoreboard
     * @return
     */
    public String getAliveScore(){
        return (String) configuration.get("Score.AlivePlayers");
    }


    /**
     * This function return a Time for a Scoreboard
     * @return
     */
    public String getTimeScore(){
        return (String) configuration.get("Score.Time");
    }


    /**
     * This function return a Spectators for a Scoreboard
     * @return
     */
    public String getSpectatorScore(){
        return (String) configuration.get("Score.Spectators");
    }


    /**
     * This function return a JoinMessage
     * @return
     */
    public String getJoinMessage(){
        return (String) configuration.get("Game.JoinMessage");
    }


    /**
     * This function return a min players for game start
     * @return
     */
    public Integer getPlayersForStart(){
        return (Integer) configuration.get("Game.MinPlayers");
    }


    /**
     * This function return a message when the game is forced to start
     * @return
     */
    public String getForceStart(){
        return (String) configuration.get("Game.ForceStart");
    }


    /**
     * This function return a message when the game start
     * @return
     */
    public String getGameStart(){
        return (String) configuration.get("Game.GameStart");
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
     * This function return a error message for bowless is active
     * @return
     */
    public String getErrBowless(){
        return (String) configuration.get("Game.MessageErrBowless");
    }


    /**
     * This function return a error message for rodless is active
     * @return
     */
    public String getErrRodless(){
        return (String) configuration.get("Game.MessageErrRodless");
    }


    /**
     * This function return a error message for limitations is active
     * @return
     */
    public String getErrLimitations(){
        return (String) configuration.get("Game.MessageErrLimitations");
    }


    /**
     * This function return a error message for diamondless is active
     * @return
     */
    public String getErrDiamondLess(){
        return (String) configuration.get("Game.MessageErrDiamondLess");
    }


    /**
     * This function return a error message for goldless is active
     * @return
     */
    public String getErrGoldLess(){
        return (String) configuration.get("Game.MessageErrGoldLess");
    }


    /**
     * This function return a error message for ironless is active
     * @return
     */
    public String getErrIronLess(){
        return (String) configuration.get("Game.MessageErrIronLess");
    }


    /**
     * This function return a error message for coalless is active
     * @return
     */
    public String getErrCoalLess(){
        return (String) configuration.get("Game.MessageErrCoalLess");
    }


    /**
     * This function return a message for AirDrop is falling
     * @return
     */
    public String getAirDropAlert(){
        return (String) configuration.get("Game.AirDropAlert");
    }


    /**
     * This function return a time for throw new AirDrop
     * @return
     */
    public Integer getAirDropTime(){
        return (Integer) configuration.get("Game.AirDropTime");
    }


    /**
     * This function return a Message on Bounty Hunter is generated
     * @return
     */
    public String getBountyMessageOnAdd(){
        return (String) configuration.get("Game.BountyHunterAdd");
    }


    /**
     * This function return a Message on Bounty Hunter is killed by You
     * @return
     */
    public String getBountyMessageOnRemove(){
        return (String) configuration.get("Game.BountyHunterRemove");
    }


    /**
     * This function return a Message error will player try craft item locked
     * @return
     */
    public String getBarebonesOnCraft(){
        return (String) configuration.get("Game.BarebonesCraftLocked");
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
