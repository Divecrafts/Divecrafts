package io.clonalejandro.DivecraftsCore.cmd;

import io.clonalejandro.DivecraftsCore.api.SUser;
import io.clonalejandro.DivecraftsCore.idiomas.Languaje;
import io.clonalejandro.DivecraftsCore.utils.Utils;

import java.util.Arrays;

public class ColorCMD extends SCmd {

    public ColorCMD() {
        super("color", Rank.MOD, Arrays.asList("color"));
    }

    @Override
    public void run(SUser user, String lbl, String[] args) {
        if (args.length < 1) {
            user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Color.Uso"));
        } else {
            if (isValidColor(args[0])) {
                user.getUserData().setNickcolor(args[0]);
                user.save();
                Utils.updateUserColor(user);
                user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Ajustes.cambiado"));
            } else {
                user.getPlayer().sendMessage(Languaje.getLangMsg(user.getUserData().getLang(), "Color.notvalid"));
            }
        }
    }

    public boolean isValidColor(String string) {
        switch (string) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "a":
            case "b":
            case "c":
            case "d":
            case "e":
            case "f":
                return true;
            default:
                return false;
        }
    }

}
