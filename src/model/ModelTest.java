package model;

import prototypeGame.model.ProjectDataManager;
import widgets.SoundPlayer;

/**
 * @test
 */
public class ModelTest {

    SoundPlayer sp = new SoundPlayer("bla");

    void test(){
        sp.pause();
    }

    ProjectDataManager a;

}
