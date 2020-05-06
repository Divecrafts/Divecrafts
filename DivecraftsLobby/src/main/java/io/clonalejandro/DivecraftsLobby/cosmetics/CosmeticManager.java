package io.clonalejandro.DivecraftsLobby.cosmetics;

import lombok.Getter;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsLobby.cosmetics.gadgets.Butterfly;
import io.clonalejandro.DivecraftsLobby.cosmetics.gadgets.FireworkName;
import io.clonalejandro.DivecraftsLobby.cosmetics.gadgets.RainbowFountain;
import io.clonalejandro.DivecraftsLobby.ex.CosmeticRegisteredException;
import io.clonalejandro.DivecraftsLobby.ex.NeededClassException;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CosmeticManager {

    @Getter private ArrayList<Cosmetic> cosmetics;
    @Getter private HashMap<SUser, Cosmetic> selectedCosmetic;

    public CosmeticManager() {
        cosmetics = new ArrayList<>();
        selectedCosmetic = new HashMap<>();
        try {
            init();
        } catch (CosmeticRegisteredException | NeededClassException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        register(new FireworkName(), new Butterfly(), new RainbowFountain());
    }

    public void register(Cosmetic... cos) throws CosmeticRegisteredException, NeededClassException {
        Arrays.asList(cos).forEach(c -> {
            betterCheck(c);
            if (c.getClass().getAnnotation(CosmeticInfo.class) == null) throw new NeededClassException("CosmeticInfo");
            cosmetics.add(c);
        });
    }

    private void betterCheck(Cosmetic c) throws CosmeticRegisteredException {
        if (cosmetics.contains(c)) throw new CosmeticRegisteredException(c);
        cosmetics.forEach(cos -> {
            if (cos.getID() == c.getID()) throw new CosmeticRegisteredException(c);
            if (cos.getName().equalsIgnoreCase(c.getName())) throw new CosmeticRegisteredException(c);
            if (cos.getMaterial() == c.getMaterial()) throw new CosmeticRegisteredException(c);
        });
    }
    
    public Cosmetic getCosmeticByMaterial(Material mat) {
        for (Cosmetic c : cosmetics) if (c.getMaterial() == mat) return c;
        return null;
    }
}
