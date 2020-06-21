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

    CUTCLEAN(new CutClean()),
    //OLDSCHOOL(null),
    VANILLAP(new VanillaP()),
    TIMEBOMB(new TimeBomb()),
    HORSELESS(new Horseless()),
    NOCLEAN(new NoClean()),
    COLDWEAPONS(new ColdWeapons()),
    FIRELESS(new Fireless()),
    BOWLESS(new Bowless()),
    RODLESS(new Rodless()),
    SOUP(new Soup()),
    LIMITATIONS(new Limitations()),
    DIAMONDLESS(new DiamondLess()),
    GOLDLESS(new GoldLess()),
    IRONLESS(new IronLess()),
    COALLESS(new CoalLess()),
    BLOODDIAMONDS(new BloodDiamonds()),
    BLOODENCHANTS(new BloodEnchants()),
    //BACKPACKS(null),
    //AIRDROPS(new AirDrops(Main.instance)),
    //BOUNTYHUNTER(new BountyHunter()),
    //MONSTERHUNTER(null),
    BAREBONES(new Barebones()),
    //WATERWORLD(null),
    OREFRENZY(new OreFrenzy()),
    //SHAREDHEALTH(null),
    //OREBANKER(null),
    NINESLOT(new NineSlot()),
    OPUHC(new OpUHC());
    /*LIMITEDENCHANT(null),
    LONGSHOOTS(null),
    LOVEATFIRSTSIGHT(null),
    MINESWEEPER(null),
    MOLES(null),
    FLOWERPOWER(null),
    ERRATICPVP(null),
    ENDERDRAGONRUSH(null);*/


    /** SMALL CONSTRUCTORS **/

    private final Object clazz;

    ModeType(final Object clazz){
        this.clazz = clazz;
    }


    /** REST **/

    public Object getClazz() {
        return clazz;
    }


}
