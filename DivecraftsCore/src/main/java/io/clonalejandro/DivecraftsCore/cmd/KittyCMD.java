package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.utils.Sounds;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

public class KittyCMD extends SCmd {

    public KittyCMD() {
        super("kitty", Rank.ADMIN, "kittycannon", "kc");
    }

    public void run(SUser user, String label, String... args) {
        final Ocelot o = (Ocelot) user.getLoc().getWorld().spawnEntity(user.getLoc(), EntityType.OCELOT);
        o.setCatType(Ocelot.Type.values()[r.nextInt(Ocelot.Type.values().length)]);
        o.setSitting(true);
        o.setTamed(true);
        o.setAge(r.nextInt(2));
        o.setCustomName(Utils.colorize("*&dGateteeeeeee"));
        o.setCustomNameVisible(true);
        o.setNoDamageTicks(Integer.MAX_VALUE);
        o.setVelocity(user.getPlayer().getLocation().getDirection().multiply(2));

        SServer.users.forEach(u -> u.sendSound(Sounds.CAT_MEOW));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            SServer.users.forEach(u -> u.sendSound(Sounds.EXPLODE));
            //Particles.EXPLOSION_HUGE.send(plugin.getServer().getOnlinePlayers(), o.getLocation(), 0, 0, 0, 1, 20, 50);
            o.remove();
        }, 60);
    }
}
