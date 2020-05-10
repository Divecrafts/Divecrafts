package io.clonalejandro.DivecraftsCore.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SServer {

    public static final String defURL = "http://91.121.76.115:3000/api/";


    private static final HashMap<UUID, UUID> tp = new HashMap<>();
    private static final HashMap<UUID, UUID> tph = new HashMap<>();

    public static ArrayList<SUser> users = new ArrayList<>();
    public static ArrayList<SUser> afkMode = new ArrayList<>();
    @Getter private static ArrayList<SUser> adminChatMode = new ArrayList<>();

    public static ArrayList<NPC> npc = new ArrayList<>();

    public static SUser getUser(UUID id) {
        for (SUser pl : users) {
            if (pl.getUuid() == null) continue;
            if (pl.getUuid().equals(id)) return pl;
        }
        SUser us = new SUser(id);
        if (us.isOnline()) users.add(us);
        return us;
    }

    public static SUser getUser(OfflinePlayer p) {
        if (p.getUniqueId() == null) throw new NullPointerException("UUID can't be null (GetUser)");
        return getUser(p.getUniqueId());
    }


    @Getter
    @AllArgsConstructor
    public enum GameID {
        FFA(1), ARCADE(2), SKYWARS(3), MICROBATTLES(4), UHC(5), FIGTHCLUB(6), MUM(7), EGGWARS(8);

        private final int id;
    }



    public static HashMap<UUID, UUID> getTeleportRequests() {
        return tp;
    }
    public static HashMap<UUID, UUID> getTeleportHereRequests() {
        return tph;
    }

    public static void addTeleportRequest(UUID u1, UUID u2) {
        tp.put(u1, u2);
    }
    public static void addTeleportHereRequest(UUID u1, UUID u2) {
        tph.put(u1, u2);
    }

    public static void removeTeleportRequest(UUID u) {
        tp.remove(u);
    }
    public static void removeTeleportHereRequest(UUID u) {
        tph.remove(u);
    }
}
