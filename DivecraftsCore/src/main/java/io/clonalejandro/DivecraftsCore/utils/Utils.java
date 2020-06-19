package io.clonalejandro.DivecraftsCore.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.events.UpdateNameEvent;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void broadcastMsg(String key) {
        Main.getInstance().getServer().getOnlinePlayers().forEach(p -> SServer.getUser(p).getPlayer().sendMessage(key));
    }

    public static String buildString(String[] args) {
        return buildString(args, 0);
    }

    public static String buildString(String[] args, int empiece) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = empiece; i < args.length; i++) {
            if (i > empiece){
                stringBuilder.append(" ");
            }
            stringBuilder.append(args[i]);
        }
        return stringBuilder.toString();
    }

    public static boolean isInt(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void updateUserColor(SUser user){
        final SCmd.Rank rank = user.getUserData().getRank();
        final String color = user.getUserData().getNickcolor().isEmpty() ? "&7" : "&" + user.getUserData().getNickcolor();
        final String prefix = rank.getRank() > 0 ? "&" + SCmd.Rank.groupColor(rank) + rank.getPrefix() + " " : "";

        final String name = prefix + color + user.getPlayer().getName();
        user.getPlayer().setPlayerListName(Utils.colorize(name));
        user.getPlayer().setDisplayName(Utils.colorize(name));
        user.getPlayer().setCustomName(Utils.colorize(name));
        user.getPlayer().setCustomNameVisible(true);

        Bukkit.getPluginManager().callEvent(new UpdateNameEvent(name, user));
        new TagAPI();
    }

    public static void loadPermissions(Player player){
        final SUser user = SServer.getUser(player);
        final int rankId = user.getUserData().getRank().getRank();
        final PermissionAttachment attachment = player.addAttachment(Main.getInstance());
        final List<String> permissions = new ArrayList<>();

        try {
            Class<?> clazz = Class.forName("io.clonalejandro.Essentials.objects.Permission");
            Object permission = clazz.getConstructor(UUID.class).newInstance(user.getUuid());
            List<String> individualPermissions = (List<String>) clazz.getMethod("get").invoke(permission);
            permissions.addAll(individualPermissions);
        }
        catch (Exception ignored){
        }

        if (rankId > 0) for (int i = 0; i <= rankId; i++)
            permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", i)));
        else permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.perms", rankId)));

        permissions.addAll(Main.getInstance().getConfig().getStringList(String.format("Permissions.%s.noheredar", rankId)));
        permissions.forEach(permission -> attachment.setPermission(permission, true));

        Main.getPerms().put(player, attachment);
    }

    /**
     * This function rescue a json from url
     */
    public static JsonObject getJson(String url) {
        try {
            URL cUrl = new URL(url);
            HttpURLConnection request = (HttpURLConnection) cUrl.openConnection();

            request.connect();

            JsonParser jsonParser = new JsonParser();
            InputStream stream = (InputStream) request.getContent();
            JsonElement response = jsonParser.parse(new InputStreamReader(stream));

            return response.getAsJsonObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String locationToString(@NonNull Location loc) {
        if (loc == null) return "";
        return loc.getWorld().getName() + "%" + loc.getX() + "%" + loc.getY() + "%" + loc.getZ() + "%" + loc.getYaw() + "%" + loc.getPitch();
    }

    public static Location stringToLocation(@NonNull String string) {
        if (string == null) return null;
        String[] s = string.split("%");
        float yaw = 0;
        float pitch = 0;

        if (s.length > 4) {
            yaw = Float.parseFloat(s[4]);
            pitch = Float.parseFloat(s[5]);
        }

        return new Location(Bukkit.getWorld(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]), yaw, pitch);
    }

    public static Location cuboidToLocation(String string, int args) {
        if (args > 1 || string == null) return null;
        return stringToLocation(string.split(";")[args]);
    }

    public static CuboidZone stringToArea(String string) {
        Block b1 = cuboidToLocation(string, 0).getBlock();
        Block b2 = cuboidToLocation(string, 1).getBlock();
        return new CuboidZone(b1, b2);
    }

    public static Location centre(Location loc) {
        Location clon = loc.clone();
        return clon.add(0.5, 0, 0.5);
    }

    //Amount = Número de puntos del círculo
    public static ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = (2 * Math.PI) / amount;
        ArrayList<Location> locations = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static World getWorld(String name) {
        return Main.getInstance().getServer().getWorld(name);
    }
}
