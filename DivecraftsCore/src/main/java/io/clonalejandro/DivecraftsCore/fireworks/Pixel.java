package io.clonalejandro.DivecraftsCore.fireworks;

import lombok.Getter;
import org.bukkit.util.Vector;

import java.awt.*;

public class Pixel {

    @Getter private final Vector loc;
    private final int red;
    private final int green;
    private final int blue;

    public Pixel(Vector loc, int r, int g, int b) {
        this.loc = loc;
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public Color getColor() {
        return new Color(red, green, blue);
    }
}
