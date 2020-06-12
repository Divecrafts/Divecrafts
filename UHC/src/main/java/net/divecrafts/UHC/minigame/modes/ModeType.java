package net.divecrafts.UHC.minigame.modes;

import net.divecrafts.UHC.Main;

/**
 * Created by alejandrorioscalera
 * On 13/2/18
 *
 * -- SOCIAL NETWORKS --
 *
 * GitHub: https://github.com/clonalejandro or @clonalejandro
 * Website: https://clonalejandro.me/
 * Twitter: https://twitter.com/clonalejandro11/ or @clonalejandro11
 * Keybase: https://keybase.io/clonalejandro/
 *
 * -- LICENSE --
 *
 * All rights reserved for clonalejandro ©StylusUHC 2017 / 2018
 */

public enum ModeType {


    /** ENUM **/

    CUTCLEAN(new CutClean(Main.instance)),
    OLDSCHOOL(null),
    VANILLAP(new VanillaP(Main.instance)),
    TIMEBOMB(new TimeBomb(Main.instance)),
    HORSELESS(new Horseless(Main.instance)),
    NOCLEAN(new NoClean(Main.instance)),
    COLDWEAPONS(new ColdWeapons(Main.instance)),
    FIRELESS(new Fireless(Main.instance)),
    BOWLESS(new Bowless(Main.instance)),
    RODLESS(new Rodless(Main.instance)),
    SOUP(new Soup(Main.instance)),
    LIMITATIONS(new Limitations(Main.instance)),
    DIAMONDLESS(new DiamondLess(Main.instance)),
    GOLDLESS(new GoldLess(Main.instance)),
    IRONLESS(new IronLess(Main.instance)),
    COALLESS(new CoalLess(Main.instance)),
    BLOODDIAMONDS(new BloodDiamonds(Main.instance)),
    BLOODENCHANTS(new BloodEnchants(Main.instance)),
    BACKPACKS(null),
    AIRDROPS(new AirDrops(Main.instance)),
    BOUNTYHUNTER(new BountyHunter(Main.instance)),
    MONSTERHUNTER(null),
    BAREBONES(new Barebones(Main.instance)),
    WATERWORLD(null),
    OREFRENZY(new OreFrenzy(Main.instance)),
    SHAREDHEALTH(null),
    OREBANKER(null),
    NINESLOT(new NineSlot(Main.instance)),
    OPUHC(new OpUHC(Main.instance)),
    LIMITEDENCHANT(null),
    LONGSHOOTS(null),
    LOVEATFIRSTSIGHT(null),
    MINESWEEPER(null),
    MOLES(null),
    FLOWERPOWER(null),
    ERRATICPVP(null),
    ENDERDRAGONRUSH(null);


    /** SMALL CONSTRUCTORS **/

    private final Object clazz;

    ModeType(final Object clazz){
        this.clazz = clazz;
    }


    /** REST **/

    Object getClazz() {
        return clazz;
    }


}
