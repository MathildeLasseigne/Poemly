package prototypeGame.controller;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import prototypeGame.model.Game;
import prototypeGame.model.GameBoard;
import prototypeGame.widgets.Karaoke.Karaoke;
import widgets.tools.Utilities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameModele  implements PropertyChangeListener {

    private Game game;

    private GameBoard gameBoard;

    /**The time it takes between each new tile */
    private Duration addingTileDuration = Duration.seconds(1);

    /**
     * The timeLine responsible to add tile to the board every x time
     */
    private Timeline addingTile = new Timeline();

    /**
     * The timer used to check all collisions
     */
    private AnimationTimer updateAll;

    private double score = 0;
    /**The value of 1 tile in percentage*/
    private double valueTile = 0;

    /**
     * Store the data necessary to run a game and allow manipulation of score.
     * <br/>Take all panes necessary from gameUI and distribute it
     */
    public GameModele(Game game){
        this.game = game;
        this.gameBoard = new GameBoard(this.game.getGameUI().gameUINodes.getBoard(), Utilities.parentToScreen(this.game.getGameUI().gameUINodes.getBar()));//this.game.getGameUI().gameUINodes.getBar().getBoundsInParent());
        setListeners();
        setTimers();
        this.valueTile = ((double) 100/ (double) this.game.getGameUI().karaoke.getKaraokeController().getLengthForDifficulty());
    }

    /**
     * Set the current listeners
     */
    private void setListeners(){
        this.gameBoard.getBar().addPropertyChangeListener(this);

        this.game.setOnKeyTyped(event -> {
            System.out.println("Key typed");
            if(! game.getGameUI().karaoke.isFinished().getValue()){
                boolean good = false;
                try {
                    good = game.getGameUI().karaoke.checkKey(event);
                } catch (Exception e) {
                    System.out.println("Karaoke finished");
                    e.printStackTrace();
                }
                if(good){
                    gameBoard.getBar().getCurrentTile().validated.set(true);
                    //score += valueTile;
                    ProgressBar gameScore = game.getGameUI().gameUINodes.getScoreBar();
                    gameScore.setProgress(gameScore.getProgress() + (valueTile/100));
                }
            }
        });
    }

    private void setTimers(){
        this.updateAll = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        this.addingTile.getKeyFrames().add(new KeyFrame(addingTileDuration, event -> {
            //TODO with karaoke
            Karaoke karaoke = this.game.getGameUI().karaoke;
            if(! karaoke.getKaraokeController().isPreviewFinished().getValue()){
                char newTileChar = 0;
                try {
                    karaoke.getKaraokeController().nextPreviewChar();
                    newTileChar = karaoke.getKaraokeController().getPreviewChar();
                    System.out.println("new char : "+newTileChar);
                } catch (Exception e) {
                    System.out.println("No new char in preview");
                    e.printStackTrace();
                }
                this.gameBoard.createTile(newTileChar);

            }

        }));
        this.addingTile.setCycleCount(Animation.INDEFINITE);
        this.addingTile.setAutoReverse(false);
        this.addingTile.setRate(1);
    }

    /**
     * Update all the game
     */
    private void update(){
        if(this.gameBoard.isFirstCall()){
            this.gameBoard.getBar().setBounds(Utilities.parentToScreen(this.game.getGameUI().gameUINodes.getBar()));
        }
        this.gameBoard.update();
        if(this.game.getGameUI().karaoke.isFinished().getValue()){
            closeGame();
            ProgressBar gameScore = game.getGameUI().gameUINodes.getScoreBar();
            this.game.getGameUI().showScorePanel(gameScore.getProgress()*100);
        }
    }

    public void start(){
        //TODO Sounds ? Or with countdown ?
        this.addingTile.playFromStart();
        this.updateAll.start();
    }

    /**
     * Reset all sounds and send Score to Score manager
     */
    public void closeGame(){
        this.addingTile.stop();
        this.updateAll.stop();
    }

    /**
     * Happen when the bar change the current tile
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.game.getGameUI().karaoke.next();
    }
}
