package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.utils.Api;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

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

public class BountyHunter implements Listener {


    /** SMALL CONSTRUCTORS **/

    private final HashMap<Player, Player> bountyList = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>(Api.getOnlinePlayers());

    BountyHunter(){
        genBountys();
    }


    /** REST **/

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if (playerIsBounty(event)) removeBounty(event);
    }


    /** OTHERS **/

    /**
     * This function check if player death is bounty of the killer
     * @param event
     * @return
     */
    private boolean playerIsBounty(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Player killer = player.getKiller();
        final Player bounty = bountyList.getOrDefault(killer, null);

        return bounty == player;
    }


    /**
     * This function gen a bountys
     */
    private void genBountys(){
        for (Player player : players)
            addBounty(player, genTargetForBounty(player));
    }


    /**
     * This function gen a random Target
     * @param key
     * @return
     */
    private Player genTargetForBounty(final Player key){
        Player player = genRandomPlayer();

        while (key != player)
            player = genRandomPlayer();

        return player;
    }


    /**
     * This function gen a random Player
     * @return
     */
    private Player genRandomPlayer(){
        return players.get(
                Api.getRandom(players.size(), 0)
        );
    }


    /**
     * This function add to list this parameters
     * @param key
     * @param value
     */
    private void addBounty(final Player key, final Player value){
        bountyList.put(key, value);
        key.sendMessage(Api.translator(Api.getConfigManager().getBountyMessageOnAdd().replace(
                "{BOUNTY}", value.getName()
        )));
    }


    /**
     * This function is the action when you want remove the bounty of player
     * @param event
     */
    private void removeBounty(final PlayerDeathEvent event){
        final Player player = event.getEntity();
        final Player killer = player.getKiller();

        event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 3));
        bountyListRemover(player, killer);
        killer.sendMessage(Api.translator(Api.getConfigManager().getBountyMessageOnRemove().replace(
                "{BOUNTY}", player.getName()
        )));
    }


    /**
     * This function remove the players of bounty list
     * @param player
     * @param killer
     */
    private void bountyListRemover(final Player player, final Player killer){
        bountyList.remove(killer);
        if (bountyList.containsKey(player))
            bountyList.remove(player);
    }


}
