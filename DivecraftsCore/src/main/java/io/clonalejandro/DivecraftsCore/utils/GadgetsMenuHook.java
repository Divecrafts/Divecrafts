package io.clonalejandro.DivecraftsCore.utils;

import com.yapzhenyie.GadgetsMenu.economy.GEconomyProvider;
import com.yapzhenyie.GadgetsMenu.player.OfflinePlayerManager;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SCoin;

/**
 * Created by Alex
 * On 29/05/2020
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
 * All rights reserved for clonalejandro ©DivecraftsCore 2017/2020
 */

public class GadgetsMenuHook extends GEconomyProvider {

    public GadgetsMenuHook(Main plugin) {
        super(plugin, "Coins");
    }

    @Override
    public int getMysteryDust(OfflinePlayerManager pManager) {
        SCoin coin = new SCoin(pManager.getUUID());
        return coin.balance();
    }

    @Override
    public void addMysteryDust(OfflinePlayerManager pManager, int amount) {
        SCoin coin = new SCoin(pManager.getUUID());
        coin.deposit(amount);
    }

    @Override
    public void setMysteryDust(OfflinePlayerManager pManager, int amount) {
        SCoin coin = new SCoin(pManager.getUUID());
        coin.set(amount);
    }

    @Override
    public void removeMysteryDust(OfflinePlayerManager pManager, int amount) {
        SCoin coin = new SCoin(pManager.getUUID());
        coin.withdraw(amount);
    }

}
