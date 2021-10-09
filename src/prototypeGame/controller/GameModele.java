package prototypeGame.controller;


import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import model.Difficulty;
import prototypeGame.model.Game;
import prototypeGame.model.GameBoard;
import prototypeGame.widgets.Karaoke.Karaoke;
import widgets.tools.Utilities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameModele  implements PropertyChangeListener {

    /**
     * The rate at which the game happen -> allow accelerating and decelerating of game.
     * <br/> ex : setting at 2 will divide the time the tiles take by 2
     * <br/>Is not a rate per ce, modify the duration of the keyFrames
     * Default is 1
     */
    private double initialDurationRate = 1;

    private Game game;

    private GameBoard gameBoard;

    /**The time it takes between each new tile */
    private Duration addingTileDuration = Duration.seconds(2);

    private Duration spaceDuration = addingTileDuration.add(Duration.seconds(1));

    private final SpeedModifier speedModifier;

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

    /**The number of tiles that will appear in the game*/
    private int nbTiles;

    /**
     * Store the data necessary to run a game and allow manipulation of score.
     * <br/>Take all panes necessary from gameUI and distribute it
     */
    public GameModele(Game game){
        this.game = game;
        this.gameBoard = new GameBoard(this.game.getGameUI().gameUINodes.getBoard(), Utilities.parentToScreen(this.game.getGameUI().gameUINodes.getBar()));//this.game.getGameUI().gameUINodes.getBar().getBoundsInParent());
        if(this.initialDurationRate != 1 && this.initialDurationRate != 0){
            this.addingTileDuration = this.addingTileDuration.divide(this.initialDurationRate);
            this.gameBoard.setOriginalTileModifier(this.initialDurationRate);
        }
        setListeners();
        setTimers();
        this.nbTiles = this.game.getGameUI().karaoke.getKaraokeController().getLengthForDifficulty();
        this.valueTile = ((double) 100/ (double) this.nbTiles);
        this.speedModifier = new SpeedModifier(this);
    }

    /**
     * Set the current listeners
     */
    private void setListeners(){
        this.gameBoard.getBar().addPropertyChangeListener(this);

        this.game.setOnKeyTyped(event -> {
            System.out.println("Key typed");
            if(this.gameBoard.getBar().getCurrentTile() != null){
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

        this.addingTile.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, event -> {
            Karaoke karaoke = this.game.getGameUI().karaoke;
            if(! karaoke.getKaraokeController().isPreviewFinished().getValue()){
                char newTileChar = 0;
                try {
                    karaoke.getKaraokeController().nextPreviewChar();
                    newTileChar = karaoke.getKaraokeController().getPreviewChar();
                    this.speedModifier.updateSpeed();
                    System.out.println("new char : "+newTileChar);
                } catch (Exception e) {
                    System.out.println("No new char in preview");
                    e.printStackTrace();
                }
                this.gameBoard.createTile(newTileChar);
                if(newTileChar == ' '){ //Slow down next tile apparition if space
                    //double comp = addingTileDuration.toMillis() / this.spaceDuration.toMillis();
                    //this.addingTile.setRate(comp);
                    double comp = addingTileDuration.divide(this.speedModifier.getCurrentRate()).toMillis() / this.spaceDuration.divide(this.speedModifier.getCurrentRate()).toMillis();
                    this.addingTile.setRate(comp);
                } else {
                    //this.addingTile.setRate(1);
                    this.addingTile.setRate(this.speedModifier.getCurrentRate());
                }
            }

        }), new KeyFrame(addingTileDuration));
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
        this.speedModifier.start();
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


    /**
     * Class that will manage the speed rate of animations in the game
     */
    private static class SpeedModifier {

        private GameModele gameModele;

        /**The speed will be maximal at maxAtPortion part of the poem for each difficulty (1 max)*/
        private double maxAtPortionEasy = 1,maxAtPortionMedium = 1, maxAtPortionHard = 1;
        /**the speed at this separator will be the maximal*/
        private int maxAtSeparator = 0;

        /**The current rate applied to the game*/
        private double currentRate = 1;

        private boolean canChangeSpeed = false;

        private double rateIncrement = 0;

        /**The maximal rate the game can go to after acceleration*/
        private final double maxRate = 4;

        private Timeline sleepTimeLine = new Timeline();

        /**The time during which no change to the speed of the game will be made*/
        private Duration calmDuration = Duration.seconds(10);

        /**
         * Class that will manage the speed rate of animations in the game
         */
        private SpeedModifier(GameModele gameModele){

            this.gameModele = gameModele;
            calculateMaxSeparator();
            this.sleepTimeLine.getKeyFrames().add(new KeyFrame(calmDuration, e -> setRateInflation()));
        }

        /**
         * Set the increment the rate inflation will use
         */
        private void setRateInflation(){
            int currentPreviewIdx = this.gameModele.game.getGameUI().karaoke.getKaraokeController().getPreviewSeparatorIdx();
            int remainingTilesForMax = this.maxAtSeparator - currentPreviewIdx;
            double workingRate = this.maxRate - this.gameModele.addingTile.getRate(); //1 being the original rate
            if(this.gameModele.addingTile.getRate() != 1){
                System.out.println("AddingTiles timeline original rate != 1 !!");
            }
            this.rateIncrement = workingRate / remainingTilesForMax;
            canChangeSpeed = true;

        }

        /**Calculate the separator at which the game will reach its maximum speed
         *  depending on the difficulty and length of the poem*/
        private void calculateMaxSeparator(){
            double maxPortion = 1;
            if(this.gameModele.game.getDifficulty() == Difficulty.DifficultyLevel.Easy){
                maxPortion = maxAtPortionEasy;
            } else if(this.gameModele.game.getDifficulty() == Difficulty.DifficultyLevel.Medium){
                maxPortion = maxAtPortionMedium;
            } else {
                maxPortion = maxAtPortionHard;
            }
            this.maxAtSeparator = (int) Math.round(this.gameModele.nbTiles * maxPortion);
        }

        /**The current rate of the game*/
        public double getCurrentRate() {
            return currentRate;
        }

        /**
         * Start the calculations for the speed
         */
        public void start(){
            this.sleepTimeLine.play();
        }

        /**
         * Calculate the current rate based on the current separator
         * and accelerate both the addingTile timeline and the TranslateTransition of the tiles
         * <br/>Will increment the rate of the addingTiles and tile TranslateTransition animations if
         * it is inferior the the maximal rate.
         */
        public void updateSpeed(){
            if(canChangeSpeed){
                this.currentRate += this.rateIncrement;
                this.gameModele.addingTile.setRate(this.currentRate);
                this.gameModele.gameBoard.setRate(this.currentRate);
                System.out.println("New rate = "+this.currentRate);
                if(this.currentRate >= this.maxRate){
                    this.canChangeSpeed = false;
                }
            }
        }
    }
}
