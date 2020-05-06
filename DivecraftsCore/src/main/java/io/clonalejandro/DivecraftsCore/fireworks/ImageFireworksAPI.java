package io.clonalejandro.DivecraftsCore.fireworks;

import org.bukkit.Location;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFireworksAPI {

    /**
     * Creates a Image with Fireworks
     *
     * @param loc The location to launch the Firework
     * @param image The image to be displayed
     */
    public static void launchFullColorFirework(Location loc, BufferedImage image) {
        CustomFirework cfw = new CustomFirework(image);
        cfw.useFirework(loc);
    }

    /**
     * Creates a Image with Fireworks with custom color
     *
     * @param loc The location to launch the Firework
     * @param image The image to be displayed
     * @param color The selected color
     */
    public static void launchSingleColorFirework(Location loc, BufferedImage image, Color color) {
        launchSingleColorFirework(loc, image, color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Creates a Image with Fireworks with custom color
     *
     * @param loc The location to launch the Firework
     * @param image The image to be displayed
     * @param red The Red color
     * @param green The Green color
     * @param blue The Blue color
     */
    public static void launchSingleColorFirework(Location loc, BufferedImage image, int red, int green, int blue) {
        CustomFirework cfw = new CustomFirework(image, red, green, blue);
        cfw.useFirework(loc);
    }
}
