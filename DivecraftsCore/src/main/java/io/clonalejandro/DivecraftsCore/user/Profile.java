package io.clonalejandro.DivecraftsCore.user;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import org.bukkit.inventory.Inventory;

public class Profile {

    private SUser u;
    private SUser.UserData userData;

    public Profile(SUser u) {
        this.u = u;
        this.userData = u.getUserData();
    }

    public Inventory getProfileInventory() {
        Inventory inv = Main.getInstance().getServer().createInventory(null, 36, "Perfil");



        return inv;
    }
}
