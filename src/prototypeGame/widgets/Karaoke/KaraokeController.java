package prototypeGame.widgets.Karaoke;

import controller.FXMLController;

public class KaraokeController extends FXMLController {

    private Karaoke karaoke;

    KaraokeController(Karaoke karaoke){
        this.karaoke = karaoke;
    }


    /**
     * Return the current char highlighted
     * @return
     */
    public char getCurrentChar(){

        return 0;
    }

    /**
     * Select the next highlighted char according to the difficulty
     */
    public void nextChar(){

    }

}
