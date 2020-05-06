package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.api.SUser;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillAllCMD extends SCmd {

    public KillAllCMD() {
        super("killall", Rank.ADMIN, Arrays.asList("kall"));
    }

    @Override
    public void run(SUser user, String label, String[] args) {
        if (args.length == 0) {
            user.sendDiv();
            user.getPlayer().sendMessage(label + " <Entity> [-n] &7-> &2Elimina las entidades del mundo");
            user.getPlayer().sendMessage("&3-n &7-> &cParámetro para no comprobar el nombre");
            user.sendDiv();
        }

        if (args.length == 1) {
            EntityType entity = EntityType.valueOf(args[0].toUpperCase());

            if (entity == null || entity == EntityType.UNKNOWN) return;

            int mobs = worldEntities(user.getPlayer().getWorld(), entity).size();
            worldEntities(user.getPlayer().getWorld(), entity).forEach(Entity::remove);
            user.getPlayer().sendMessage(Main.getPREFIX() + "&6Eliminados &c" + mobs + " &6mobs");
        }

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("-n")) {
                EntityType entity = EntityType.valueOf(args[0].toUpperCase());

                if (entity == null || entity == EntityType.UNKNOWN) return;

                int mobs = worldEntities(user.getPlayer().getWorld(), entity).size();
                worldClassEntities(user.getPlayer().getWorld(), entity).forEach(Entity::remove);
                user.getPlayer().sendMessage(Main.getPREFIX() + "&6Eliminados &c" + mobs + " &6mobs");
            }
        }
    }

    private List<Entity> worldEntities(World w, EntityType entityType) {
        List<Entity> entities = new ArrayList<>();

        w.getEntities().forEach(e -> {
            if (e.getType() == entityType) {
                entities.add(e);
            }
        });
        return entities;
    }

    private List<Entity> worldClassEntities(World w, EntityType entityType) {
        List<Entity> entities = new ArrayList<>();

        w.getEntitiesByClass(entityType.getEntityClass()).forEach(e -> {
            if (e.getType() == entityType) {
                entities.add(e);
            }
        });
        return entities;
    }
}
