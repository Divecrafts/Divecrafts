package net.divecrafts.UHC.exceptions;

/**
 * Created by alejandrorioscalera
 * On 25/1/18
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

public class WorldException extends Exception {


    /** SMALL CONSTRUCTORS **/

    public WorldException(){

    }

    public WorldException(String message){
        super(message);
    }

    public WorldException(Throwable cause){
        super(cause);
    }

    public WorldException(String message, Throwable cause){
        super(message, cause);
    }


}
