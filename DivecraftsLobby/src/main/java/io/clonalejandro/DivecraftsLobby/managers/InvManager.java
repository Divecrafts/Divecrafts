package io.clonalejandro.DivecraftsLobby.managers;

import io.clonalejandro.DivecraftsCore.api.SServer;
import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.cmd.SCmd;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.BungeeMensager;
import io.clonalejandro.DivecraftsCore.utils.ItemMaker;
import io.clonalejandro.DivecraftsCore.utils.SoundUtils;
import io.clonalejandro.DivecraftsCore.utils.Utils;
import io.clonalejandro.DivecraftsLobby.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

public class InvManager {

    public enum InvType { //Debemos incluir todos los invs para abrirlos
        SETTINGS, PROFILE, GAMES, STATS, IDIOMAS, SOCIAL;
    }

    public static void openInventory(Player player, InvType invType) {
        Inventory inv = null;

        SUser u = SServer.getUser(player);

        switch (invType){
            case IDIOMAS:
                inv = Bukkit.getServer().createInventory(null, 45, "Language");

                inv.setItem(20, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/7e6b73755aec4be3ddbbe8b68f71c8f7d5124e6006191664b035dfbfd06e2a36", "&6ESPA\u00d1OL", Arrays.asList("")));
                inv.setItem(24, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/eaf370e34c197eadd82d5c34cb89550eeceb36c732cfd30975804e47c4cba443", "&6ENGLISH", Arrays.asList("")));

                break;
            case SOCIAL:
                inv = Bukkit.getServer().createInventory(null, 45, "Social");

                inv.setItem(20, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/b4dfc9ef8635032f116719ba9eb5e55912e500f6d56221c541e79d6675348d2f", "&5DISCORD &7(CLICK)", Arrays.asList("")));
                inv.setItem(22, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/35a851eb1b6a7447f603419b61a6126aacc79456237b1809b447c6f182ae82c", "&cYOUTUBE &7(CLICK)", Arrays.asList("")));
                inv.setItem(24, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/9228a1f45d741ea33a973cb2c824e9106784a546dddbacda9f4af73399fbbfd4", "&bTWITTER &7(CLICK)", Arrays.asList("")));

                break;
            case PROFILE:
                inv = Bukkit.getServer().createInventory(null, 45, "Perfil");

                String rankname = Utils.colorize("&" + SCmd.Rank.groupColor(u.getUserData().getRank()) + u.getUserData().getRank().getPrefix());

                inv.setItem(11, ItemUtil.createSkull(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.infoname"), player.getName(), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.infolore").replace("%rango%",  rankname).replace("%coins%", String.valueOf(u.getUserData().getCoins())).split("\n"))));
                inv.setItem(15, new ItemMaker(Material.PAPER).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.statsname")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.statslore").split("\n"))).build());
                inv.setItem(13, new ItemMaker(Material.REDSTONE_COMPARATOR).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.ajustesname")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.ajusteslore").split("\n"))).build());
                inv.setItem(30, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/9228a1f45d741ea33a973cb2c824e9106784a546dddbacda9f4af73399fbbfd4", Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.socialname"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.sociallore").split("\n"))));
                inv.setItem(32, ItemUtil.createCustomSkull("http://textures.minecraft.net/texture/9dfc8932865fd57d9d2365f1ae2d475135d746b2af15abd33ffc2a6abd36282", Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.idiomasname"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Inventarios.idiomaslore").split("\n"))));
                break;
            case SETTINGS:
                inv = Bukkit.getServer().createInventory(null, 18, "Settings");

                inv.setItem(1, ItemUtil.createItem(Material.ANVIL, Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.clanes"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.clanes").split("\n"))));

                inv.setItem(3, ItemUtil.createItem(Material.BOOK, Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.chat"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.chat").split("\n"))));

                inv.setItem(5, ItemUtil.createItem(Material.BREWING_STAND_ITEM, Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.party"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.party").split("\n"))));

                inv.setItem(7, ItemUtil.createItem(Material.ENDER_PEARL, Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.visibilidad"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.visibilidad").split("\n"))));

                //Clanes
                DyeColor clanesColor = u.getUserData().getClanes() ? DyeColor.LIME : DyeColor.RED;
                inv.setItem(10, ItemUtil.createClay(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.clanes"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.clanes").split("\n")), clanesColor));

                //Party
                DyeColor partyColor = u.getUserData().getPartys() ? DyeColor.LIME : DyeColor.RED;
                inv.setItem(14, ItemUtil.createClay(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.party"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.party").split("\n")), partyColor));

                //Chat
                DyeColor chatColor = u.getUserData().getChat() ? DyeColor.LIME : DyeColor.RED;
                inv.setItem(12, ItemUtil.createClay(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.chat"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.chat").split("\n")), chatColor));

                //Visibilidad
                DyeColor visibilidadColor = (u.getUserData().getVisible() == 0 ? DyeColor.RED : (u.getUserData().getVisible() == 1 ? DyeColor.YELLOW : DyeColor.LIME));
                inv.setItem(16, ItemUtil.createClay(Languaje.getLangMsg(u.getUserData().getLang(), "Ajustesname.visibilidad"), Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Itemlores.visibilidad").split("\n")), visibilidadColor));
                break;
            case GAMES:
                inv = Bukkit.getServer().createInventory(null, 45, "Games");

                ArrayList<String> loreLobby = new ArrayList<String>();
                String[] lores = Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.lobby").split("\n");
                for (String addlore : lores) {
                    loreLobby.add((addlore).replace("%players%", String.valueOf(BungeeMensager.getLobbies().get("lobby"))));
                }

                inv.setItem(4, new ItemMaker(Material.BOOKSHELF).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.lobby")).setLore(loreLobby).build());

                //inv.setItem(20, new ItemMaker(Material.BLAZE_ROD).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.ffa")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.ffa").replace("%players%", String.valueOf(BungeeMensager.getJuegos().get("ffa"))).split("\n"))).build());

                //inv.setItem(22, new ItemMaker(Material.DIAMOND_CHESTPLATE).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.uhc")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.uhc").replace("%players%", String.valueOf(BungeeMensager.getJuegos().get("uhc"))).split("\n"))).build());

                //inv.setItem(24, new ItemMaker(Material.BOW).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.sw")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.sw").replace("%players%", String.valueOf(BungeeMensager.getLobbies().get("lsw") + BungeeMensager.getJuegos().get("sw"))).split("\n"))).build());

                //inv.setItem(28, new ItemMaker(Material.GOLDEN_APPLE).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.fc")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.fc").replace("%players%", String.valueOf(BungeeMensager.getLobbies().get("lfc") + BungeeMensager.getJuegos().get("fc"))).split("\n"))).build());

                //inv.setItem(30, new ItemMaker(Material.DIAMOND_SWORD).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.mum")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.mum").replace("%players%", String.valueOf(BungeeMensager.getJuegos().get("mum"))).split("\n"))).build());

                //inv.setItem(32, new ItemMaker(Material.WOOL, 1, (byte) 2).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.mb")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.mb").replace("%players%", String.valueOf(BungeeMensager.getJuegos().get("mb"))).split("\n"))).build());

                //inv.setItem(34, new ItemMaker(Material.DRAGON_EGG).setName(Languaje.getLangMsg(u.getUserData().getLang(), "Juegosname.ew")).setLore(Arrays.asList(Languaje.getLangMsg(u.getUserData().getLang(), "Juegoslore.ew").replace("%players%", String.valueOf(BungeeMensager.getLobbies().get("lew") + BungeeMensager.getJuegos().get("ew"))).split("\n"))).build());

                break;
            case STATS:
                inv = Bukkit.getServer().createInventory(null, 36, "Stats");;

                inv.setItem(11, new ItemMaker(Material.BLAZE_ROD).setName("&6FFA").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.FFA),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.FFA), "&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.FFA)).build());

                inv.setItem(13, new ItemMaker(Material.DIAMOND_CHESTPLATE).setName("&6UHC").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.UHC), "&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.UHC), "&fWINS: &6" + u.getUserData().getWins(SServer.GameID.UHC), "&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.UHC)).build());

                inv.setItem(15, new ItemMaker(Material.BOW).setName("&6SkyWars").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.SKYWARS),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.SKYWARS),"&fWINS: &6" + u.getUserData().getWins(SServer.GameID.SKYWARS),"&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.SKYWARS)).build());

                inv.setItem(19, new ItemMaker(Material.GOLDEN_APPLE).setName("&6FightClub").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.FIGTHCLUB),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.FIGTHCLUB),"&fWINS: &6" + u.getUserData().getWins(SServer.GameID.FIGTHCLUB),"&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.FIGTHCLUB)).build());

                inv.setItem(21, new ItemMaker(Material.DIAMOND_SWORD).setName("&6MUM").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.MUM),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.MUM),"&fWINS: &6" + u.getUserData().getWins(SServer.GameID.MUM),"&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.MUM),"&fELO: &6" + u.getUserData().getMum_elo(),"&fREROLL: &6" + u.getUserData().getMum_reroll()).build());

                inv.setItem(23, new ItemMaker(Material.WOOL, 1, (byte) 2).setName("&6MicroBattle").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.MICROBATTLES),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.MICROBATTLES),"&fWINS: &6" + u.getUserData().getWins(SServer.GameID.MICROBATTLES),"&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.MICROBATTLES)).build());

                inv.setItem(25, new ItemMaker(Material.DRAGON_EGG).setName("&6EggWars").setLore("&fKILLS: &6" + u.getUserData().getKills(SServer.GameID.EGGWARS),"&fDEATHS: &6" + u.getUserData().getDeaths(SServer.GameID.EGGWARS),"&fWINS: &6" + u.getUserData().getWins(SServer.GameID.EGGWARS),"&fPLAYS: &6" + u.getUserData().getPlays(SServer.GameID.EGGWARS)).build());

                break;
        }
        if (inv == null) return;

        player.closeInventory();
        player.openInventory(inv);
        SoundUtils.sendPlayerSound(player, Sound.CLICK, 2, 2);
    }
}
