package model;

import widgets.SoundPlayer;

/**
 * The class managing all audios of the game other than the songs,
 * such as buttons sounds or tiles sounds
 */
public class Audio {

    /**Sound played when a bad Tile is typed*/
    public static SoundPlayer badTile;

    /**Sound played when a button is fired*/
    public static SoundPlayer buttonSound;

    /**Sound played by the countdown*/
    public static SoundPlayer countDown;

    /*-------------Path------------------*/

    private static String path = "src/assets/audio/sounds/";


    /*------------------Init-----------------*/


    /**
     * Load all the sounds into its static components
     */
    public static void load(){
        badTile = new SoundPlayer(path+"badKey.mp3");
        buttonSound = new SoundPlayer(path+"button_selection.mp3");
        countDown = new SoundPlayer(path+"countdown.mp3");
    }



}
