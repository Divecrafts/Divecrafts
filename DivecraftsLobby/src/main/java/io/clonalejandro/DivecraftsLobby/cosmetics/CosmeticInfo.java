package io.clonalejandro.DivecraftsLobby.cosmetics;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CosmeticInfo {
    int id();
    String name();
    Material mat();
    Cosmetic.CosmeticType type();
}
