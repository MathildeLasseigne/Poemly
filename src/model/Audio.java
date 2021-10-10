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


    /*---------------Songs for tests----------------------*/

    public static Song camping;

    public static Song fingerFamily;

    public static Song humptyDumpty;

    public static Song maypole;

    public static Song theAnts;

    public static Song theMoose;

    public static Song theBear;

    public static Song theElephant;

    public static Song greenMouse;

    public static Song freshWind;


    /**
     * Load the songs for test purpose
     */
    public static void loadSongs(){
        camping = new Song("Camping", "src/assets/audio/songs/camping.mp3");
        fingerFamily = new Song("Finger Family", "src/assets/audio/songs/finger-family.mp3");
        humptyDumpty = new Song("Humpty Dumpty", "src/assets/audio/songs/humpty-dumpty.mp3");
        maypole = new Song("Maypole", "src/assets/audio/songs/Maypole.mp3");
        theAnts = new Song("The ants war", "src/assets/audio/songs/the-ants-go-marching-one-by-one.mp3");
        theMoose = new Song("The Moose", "src/assets/audio/songs/the-moose.mp3");
        theBear = new Song("The other day I met a bear", "src/assets/audio/songs/the-other-day-i-met-a-bear.mp3");
        theElephant = new Song("Un elephant qui se balancait", "src/assets/audio/songs/un-elephant-qui-se-balancait.mp3");
        greenMouse = new Song("Une souris verte", "src/assets/audio/songs/une-souris-verte.mp3");
        freshWind = new Song("Vent frais", "src/assets/audio/songs/Vent frais.mp3");
    }

}
