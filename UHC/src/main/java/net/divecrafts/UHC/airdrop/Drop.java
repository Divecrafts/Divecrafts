package net.divecrafts.UHC.airdrop;

import org.bukkit.inventory.ItemStack;

public class Drop {

    private ItemStack item;
    private float prob;

    private boolean disabled = false;

    public Drop(ItemStack item, float prob) {
        this.item = item;
        this.prob = prob;
    }

    public Drop setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public ItemStack getItem() {
        return item;
    }

    public float getProb() {
        return prob;
    }
}
