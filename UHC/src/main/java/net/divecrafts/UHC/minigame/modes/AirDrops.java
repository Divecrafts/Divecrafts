package net.divecrafts.UHC.minigame.modes;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;

import net.divecrafts.UHC.Main;
import net.divecrafts.UHC.airdrop.AirDrop;
import net.divecrafts.UHC.airdrop.Drop;
import net.divecrafts.UHC.listeners.GameStartEvent;
import net.divecrafts.UHC.minigame.Game;
import net.divecrafts.UHC.minigame.arena.Arena;
import net.divecrafts.UHC.task.AirDropsTask;
import net.divecrafts.UHC.utils.Api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by alejandrorioscalera
 * On 20/2/18
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

class AirDrops implements Listener {


    /** SMALL CONSTRUCTORS **/

    private AirDropsTask task;
    private final List<Drop> drops = Arrays.asList(
            new Drop(new ItemStack(Material.DIAMOND, 3), 10),
            new Drop(new ItemStack(Material.GOLDEN_APPLE, 1), 5),
            new Drop(new ItemStack(Material.MELON, 4), 100),
            new Drop(new ItemStack(Material.EXP_BOTTLE, 10), 20),
            new Drop(new ItemStack(Material.IRON_SWORD), 30),
            new Drop(new ItemStack(Material.DIAMOND_CHESTPLATE), 5),
            new Drop(new ItemStack(Material.IRON_CHESTPLATE), 25),
            new Drop(new ItemStack(Material.IRON_INGOT, 10), 20),
            new Drop(new ItemStack(Material.FLINT_AND_STEEL), 40),
            new Drop(new ItemStack(Material.FISHING_ROD), 45),
            new Drop(new ItemStack(Material.GRILLED_PORK, 5), 25),
            new Drop(new ItemStack(Material.ARROW, 16), 20)
    );


    /** REST **/

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        task = new AirDropsTask() {
            @Override
            public void onEnd() {
                airDropGenerator();
            }
        };
        task.runTaskTimer(Main.instance, 1L, 20L);
    }

    @EventHandler
    public void itemDropped(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            final FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            fallingBlock.setDropItem(false);

            if (fallingBlock.getMaterial() == Material.CHEST && fallingBlock.hasMetadata("supplyDrop")) {
                event.getBlock().setType(Material.CHEST);
                final Chest chest = (Chest) event.getBlock().getState();
                genDropContent().forEach(drop -> chest.getBlockInventory().addItem(drop.getItem()));
                event.setCancelled(true);
            }
        }
    }


    /**
     * This function return a process task
     * @return
     */
    public AirDropsTask getTask() {
        return task;
    }


    /** OTHERS **/

    /**
     * This function generates a AirDrop
     */
    private void airDropGenerator() {
        final Location location = Game.game != null ? Arena.genRandomSpawn(256 / 2, (int) Api.getArena().getBorder().getSize()) : null;
        if (location == null) return;

         new AirDrop(location){
            @Override
            public void onCreate(){
                Bukkit.getOnlinePlayers().forEach(p -> {
                    final SUser user = SServer.getUser(p);
                    final String coord = String.format("(x: %s, y: %s, z: %s)", (int) location.getX(), (int) location.getY(), (int) location.getZ());
                    p.sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "UHC.airdrop").replace("%coord%", coord));
                });

                super.getChest().getLocation().getWorld().strikeLightning(super.getChest().getLocation());
                Api.playSound(Bukkit.getWorld("Normal_tmp"), Sound.BLAZE_DEATH, 3.0F, 0.533F);
            }
        };
    }

    private List<Drop> genDropContent(){
        List<Drop> target = new ArrayList<>();

        drops.forEach(drop -> {
            int prob = (int) drop.getProb();
            int rand = new Random().nextInt((100) + 1);

            if (rand > 100 - prob){
                target.add(drop);
            }
        });

        return target;
    }
}
