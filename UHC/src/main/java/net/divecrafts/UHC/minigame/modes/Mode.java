package net.divecrafts.UHC.minigame.modes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alejandrorioscalera
 * On 20/2/18
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

public class Mode {


    /** SMALL CONSTRUCTORS **/

    private ArrayList<ModeType> modeTypes = new ArrayList<>();

    public Mode(ModeType... type){
        modeTypes.addAll(Arrays.asList(type));
    }


    /** REST **/

    /**
     * This function return a modesList
     * @return
     */
    public ArrayList<ModeType> getModes() {
        return modeTypes;
    }


    /**
     * This function reassign the modeTypes
     * @param modeList
     */
    public void setModes(final ArrayList<ModeType> modeList){
        modeTypes = modeList;
    }


    /**
     * This function add Mode to modeTypes
     * @param type
     */
    public void addMode(final ModeType type){
        modeTypes.add(type);
    }


}
