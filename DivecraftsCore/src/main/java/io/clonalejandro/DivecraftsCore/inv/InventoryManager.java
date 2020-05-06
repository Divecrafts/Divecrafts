package io.clonalejandro.DivecraftsCore.inv;

import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InventoryManager {

    @Getter private HashMap<String, List<Inventory>> inventories;

    public InventoryManager() {
        inventories = new HashMap<>();
    }

    public boolean setInventories(String key, List<Inventory> invs) {
        if (!getInventoriesByKey(key).isEmpty()) return false;
        inventories.put(key, invs);
        return true;
    }

    public boolean addInventories(String key, Inventory... invs) {
        Arrays.asList(invs).forEach(inv -> addInventory(key, inv));
        return true;
    }
    public boolean addInventory(String key, Inventory inv) {
        List<Inventory> invs = getInventoriesByKey(key);

        if (invs.isEmpty()) return setInventories(key, Arrays.asList(inv));

        invs.add(inv);
        removeInventories(key);
        setInventories(key, invs);
        return true;
    }


    public boolean removeInventories(String key) {
        if (getInventoriesByKey(key).isEmpty()) return false;

        inventories.remove(key);
        return true;
    }

    public List<Inventory> getInventoriesByKey(String key) {
        return inventories.get(key);
    }
}
