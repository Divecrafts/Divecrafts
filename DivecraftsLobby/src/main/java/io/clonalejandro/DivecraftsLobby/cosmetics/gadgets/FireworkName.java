package io.clonalejandro.DivecraftsLobby.cosmetics.gadgets;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.fireworks.ImageFireworksAPI;
import io.clonalejandro.DivecraftsLobby.cosmetics.Cosmetic;
import io.clonalejandro.DivecraftsLobby.cosmetics.CosmeticInfo;
import io.clonalejandro.DivecraftsLobby.ex.MissingUserException;
import io.clonalejandro.DivecraftsLobby.utils.CosmeticsUtils;
import org.bukkit.Material;

@CosmeticInfo(id = 0, name = "&3Firework&5Name", mat = Material.NAME_TAG, type = Cosmetic.CosmeticType.GADGET)
public class FireworkName extends Gadget {

    public FireworkName() {
        super(SCmd.Rank.YOUTUBER);
    }

    @Override
    public void onClick() {
        start();
    }

    @Override
    protected void start() {
        if (hasUser()) throw new MissingUserException();
        ImageFireworksAPI.launchFullColorFirework(getUser().getLoc(), CosmeticsUtils.stringToImage(getUser().getName()));
    }

    @Override
    protected void stop() {}

    @Override
    public void run(){}
}
