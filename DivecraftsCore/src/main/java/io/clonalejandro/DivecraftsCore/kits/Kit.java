package io.clonalejandro.DivecraftsCore.kits;

import lombok.Getter;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.ex.SlotAlreadyInUseException;
import io.clonalejandro.DivecraftsCore.utils.Log;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Kit {

    @Getter private final String name;
    @Getter private final int cost;
    @Getter private final SCmd.Rank rank;

    private HashMap<ItemSlot, List<ItemStack>> contents;

    public Kit(String name) {
        this(name, 0, SCmd.Rank.USUARIO);
    }
    public Kit(String name, int cost) {
        this(name, cost, SCmd.Rank.USUARIO);
    }
    public Kit(String name, int cost, SCmd.Rank rank) {
        this.name = name;
        this.cost = cost;
        this.rank = rank;

        contents = new HashMap<>();
    }

    public void addInvItem(ItemStack... item) {
        addItem(ItemSlot.INV, item);
    }

    public void addItem(ItemSlot slot, ItemStack... item) {
        try {
            sameSlot(slot);
            addItems(slot, item);
        } catch (SlotAlreadyInUseException e) {
            Log.log(Log.ERROR, e.getMessage());
        }
    }

    private void sameSlot(ItemSlot slot) throws SlotAlreadyInUseException {
        if (contents.isEmpty()) return;
        for (ItemSlot s : contents.keySet()) {
            if (s.equals(ItemSlot.INV)) continue;
            if (s.equals(slot)) throw new SlotAlreadyInUseException(slot);
        }
    }

    public ItemStack getItem(ItemSlot slot) {
        if (slot == ItemSlot.INV) return null;
        return contents.getOrDefault(slot, Arrays.asList(new ItemStack(Material.AIR))).get(0);
    }

    public List<ItemStack> getInventoryItems() {
        return contents.getOrDefault(ItemSlot.INV, Arrays.asList(new ItemStack(Material.AIR)));
    }

    private void addItems(ItemSlot slot, ItemStack... item) {
        if (contents.get(slot) == null) {
            contents.put(slot, Arrays.asList(item));
            return;
        }
        List<ItemStack> items = contents.get(slot);

        items.addAll(Arrays.asList(item));
        contents.put(slot, items);
    }

    public enum ItemSlot {
        HELMET, CHEST, LEGGING, BOOTS, INV, MAIN
    }
}
