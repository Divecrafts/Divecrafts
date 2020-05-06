package io.clonalejandro.DivecraftsCore.fireworks;

import io.clonalejandro.DivecraftsCore.Main;
import io.clonalejandro.DivecraftsCore.particles.Particles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class CustomFirework {

    private final Particles particle = Particles.REDSTONE;
    private Color color;
    private BufferedImage image;
    private boolean useColor;

    public CustomFirework(BufferedImage image) {
        this(image, -1, -1, -1);
    }

    public CustomFirework(BufferedImage image, int red, int green, int blue) {
        if (red == -1 && green == -1 && blue == -1) {
            useColor = true;
        } else {
            this.color = new Color(red, green, blue);
        }
        this.image = image;
    }

    public void useFirework(final Location center) {
        final Firework item = (Firework) center.getWorld().spawnEntity(center, EntityType.FIREWORK);
        FireworkMeta fM = item.getFireworkMeta();
        fM.setPower(2);
        item.setFireworkMeta(fM);
        final float yaw = center.getYaw();

        new BukkitRunnable() {
            int timer = 100;

            public void run() {
                if (this.timer > 0) {
                    this.timer -= 1;
                    if (item.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) {
                        Location loc = item.getLocation().clone();
                        loc.setY(loc.getY() + 4.0D);
                        loc.setYaw(yaw);
                        CustomFirework.this.explodeFirework(loc);
                        item.remove();
                        cancel();
                    }
                    if (item.getLocation().getY() >= center.getY() + 10.0D) {
                        Location loc = item.getLocation().clone();
                        loc.setY(loc.getY() + 4.0D);
                        loc.setYaw(yaw);
                        CustomFirework.this.explodeFirework(loc);
                        item.remove();
                        cancel();
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);
    }

    private void explodeFirework(final Location center) {
        final ArrayList<Pixel> firework = generateFirework(this.image);
        final int xIni = center.getBlockX();
        final int yIni = center.getBlockY();
        final int zIni = center.getBlockZ();
        final double rot = center.getYaw();

        new BukkitRunnable() {
            int times = 30;

            public void run() {
                if (this.times > 0) {
                    this.times -= 1;
                    for (int i = 0; i < firework.size(); i++) {
                        Vector v = new Vector((firework.get(i).getLoc()).getX() / 5.0D, (firework.get(i).getLoc()).getY() / 5.0D, 0);

                        Vector rotated = rotateVector(v, rot);

                        center.setX(xIni + rotated.getX());
                        center.setY(yIni + rotated.getY());
                        center.setZ(zIni + rotated.getZ());

                        try {
                            particle.display(new Particles.OrdinaryColor(firework.get(i).getColor().getRed(), firework.get(i).getColor().getGreen(), firework.get(i).getColor().getBlue()), center);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 0L);
    }

    private Vector rotateVector(Vector v, double angleInDegrees) {
        double angRad = (Math.PI * (angleInDegrees + 180)) / 180.0;
        double rotatedX = v.getX() * Math.cos(angRad) - v.getZ() * Math.sin(angRad);
        double rotatedZ = v.getX() * Math.sin(angRad) + v.getZ() * Math.cos(angRad);
        double rotatedY = v.getY();

        return new Vector(rotatedX, rotatedY, rotatedZ);
    }

    private BufferedImage getBufferedImage(String image) {
        BufferedImage imagen;

        File imageFile = new File(Main.getInstance().getDataFolder() + File.separator + "images" + File.separator + image);
        if (!imageFile.exists()) {
            try {
                throw new Exception(ChatColor.RED + "Could not find " + image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            imagen = ImageIO.read(imageFile);
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage() + " - File:" + imageFile.getAbsolutePath());
        }
        return imagen;
    }

    private ArrayList<Pixel> generateFirework(BufferedImage image) {
        ArrayList<Pixel> result = new ArrayList();

        if (image == null) return result;
        process(image, result);
        return result;
    }

    private void process(BufferedImage image, ArrayList<Pixel> result) {
        int offsetX = -image.getWidth() / 2;
        int offsetY = image.getHeight() / 2;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y), true);
                if (c.getAlpha() != 0) {
                    if (useColor) {
                        result.add(new Pixel(new Vector(x + offsetX, -1 * y + offsetY, 0), c.getRed(), c.getGreen(), c.getBlue()));
                    } else {
                        result.add(new Pixel(new Vector(x + offsetX, -1 * y + offsetY, 0), color.getRed(), color.getGreen(), color.getBlue()));
                    }
                }
            }
        }
    }
}
