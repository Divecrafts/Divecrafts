package io.clonalejandro.DivecraftsLobby.cosmetics.gadgets;

import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.particles.Particles;
import io.clonalejandro.DivecraftsLobby.cosmetics.Cosmetic;
import io.clonalejandro.DivecraftsLobby.cosmetics.CosmeticInfo;
import io.clonalejandro.DivecraftsLobby.ex.MissingUserException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.Random;

@CosmeticInfo(id = 1, name = "&5Butterfly", mat = Material.FEATHER, type = Cosmetic.CosmeticType.GADGET)
public class Butterfly extends Gadget {

    public Butterfly() {
        super(SCmd.Rank.INDRA);
    }

    private final Random dice = new Random();
    private final char[] hexValues = {'a', 'b', 'c', 'd', 'e', 'f', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private int r;
    private int g;
    private int b;

    @Override
    public void onClick() {
        if (!isEnabled()) {
            start();
            setEnabled(true);
            return;
        }
        stop();
        setEnabled(false);
    }

    @Override
    protected void start() {
        if (hasUser()) throw new MissingUserException();
        getColour();
        bt = this.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    @Override
    protected void stop() {
        if (bt == null) return;
        bt.cancel();
    }

    @Override
    public void run() {
        animation();
    }

    private void getColour() {
        String uuid = getUser().getUuid().toString().replace("-", "").toUpperCase();
        String longString = "";
        for (int i = 0; i < uuid.length(); i++) longString = longString + uuid.charAt(i);
        String random = "";
        for (int i = 0; i < 6; i++) {
            dice.setSeed(Long.parseLong(longString.substring(i * 10, (i + 1) * 10)));
            random += hexValues[dice.nextInt(16)];
        }
        this.r = Integer.valueOf(random.substring(0, 2), 16);
        this.g = Integer.valueOf(random.substring(2, 4), 16);
        this.b = Integer.valueOf(random.substring(4, 6), 16);
    }

    private void animation() {
        Location loc = getUser().getPlayer().getEyeLocation().subtract(0.0D, 0.3D, 0.0D);
        loc.setPitch(0.0F);
        loc.setYaw(getUser().getPlayer().getEyeLocation().getYaw());
        Vector v1 = loc.getDirection().normalize().multiply(-0.3D);
        v1.setY(0);
        loc.add(v1);
        for (double i = -8.32D; i < -4.26D; i += 0.1D) {
            double var = Math.sin(i / 12.0D);
            double x = Math.sin(i * 0.5D) * (Math.exp(Math.cos(i * 7.0D)) - 1.0D * Math.cos(3.0D * i) - Math.pow(var, 5.0D)) * 0.8D;
            double z = Math.cos(i * 0.5D) * (Math.exp(Math.cos(i * 7.0D)) - 1.0D * Math.cos(3.0D * i) - Math.pow(var, 5.0D)) * 0.6D;
            Vector v = new Vector(x, 0.0D, -z);
            rotateAroundAxisX(v, 2.0071287155151367D);
            rotateAroundAxisY(v, -loc.getYaw() * 0.017453292F);
            Particles.REDSTONE.display(new Particles.OrdinaryColor(r, g, b), loc.clone().add(v));
        }
    }

    private void rotateAroundAxisX(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        v.setY(y).setZ(z);
    }

    private void rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        v.setX(x).setZ(z);
    }
}
