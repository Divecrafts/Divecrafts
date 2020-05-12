package io.clonalejandro.DivecraftsLobby.cosmetics.gadgets;

import lombok.AllArgsConstructor;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.utils.ItemMaker;
import io.clonalejandro.DivecraftsLobby.Main;
import io.clonalejandro.DivecraftsLobby.cosmetics.Cosmetic;
import io.clonalejandro.DivecraftsLobby.cosmetics.CosmeticInfo;
import io.clonalejandro.DivecraftsLobby.ex.MissingUserException;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CosmeticInfo(id = 2, name = "RainbowFountain", mat = Material.STAINED_CLAY, type = Cosmetic.CosmeticType.GADGET)

public class RainbowFountain extends Gadget {

    public RainbowFountain() {
        super(SCmd.Rank.YOUTUBER);
    }

    @Override
    public void onClick() {
        if (!isEnabled()) {
            start();
            setEnabled(true);
            return;
        }
        stop();
        setEnabled(false);
    }

    @Override
    protected void start() {
        if (hasUser()) throw new MissingUserException();
        bt = this.runTaskTimerAsynchronously(plugin, 0, 10);
    }

    @Override
    protected void stop() {
        if (bt == null) return;
        bt.cancel();
    }

    @Override
    public void run() {
        World w = getUser().getWorld();
        items().forEach(i -> {
            final Item it = w.dropItemNaturally(getUser().getLoc(), i);
            it.setVelocity(new Vector(0, 2, 0));
            it.setPickupDelay(Integer.MAX_VALUE);
            new ItemRemover(it).runTaskLater(Main.getInstance(), 60);
        });
    }

    private List<ItemStack> items() {
        List<ItemStack> items = new ArrayList<>();
        Arrays.asList(DyeColor.values()).forEach(c -> items.add(new ItemMaker(Material.STAINED_CLAY, 1, (short)c.getDyeData()).build()));
        return items;
    }

    @AllArgsConstructor
    private class ItemRemover extends BukkitRunnable {

        private Item item;

        @Override
        public void run() {
            item.remove();
        }
    }
}
