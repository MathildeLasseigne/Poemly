package prototypeGame.model;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import widgets.tools.Utilities;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <ul>
 *     <li>Store the board + all the tiles + the difficulty.</li>
 *     <li>Can create and delete tiles</li>
 * </ul>
 */
public class GameBoard {

    private ArrayList<Tile> tileList = new ArrayList<>();

    private Bar bar;

    private Pane boardPane;

    private Bounds originalBoundsGameBoard;

    /**Detect if it is the first time an access was made after putting the game in a window.
     * <br/>To use to get Bounds in screens*/
    private boolean isFirstCall = true;

    /**The duration of the tile translation to the bottom of the screen*/
    private Duration translationDuration = Duration.millis(10000);

    private final ReentrantLock tileListMutex = new ReentrantLock();

    /**
     * Manage the game panel. Create the bar in the board
     * @param gameBoard the pane to which the tiles will be added
     * @param barBounds the bounds of the Bar
     */
    public GameBoard(Pane gameBoard, Bounds barBounds){
        this.bar = new Bar(barBounds);
        this.boardPane = gameBoard;
        this.originalBoundsGameBoard = this.boardPane.localToScreen(this.boardPane.getBoundsInLocal());
    }

    /**
     * Create a new tile and add it to the list.
     * New tile will immediately begin its movement
     * @param charTile the char of the new tile
     */
    public void createTile(char charTile){
        synchronized (this.tileList){
            try {
                this.tileListMutex.lock();
                int offsetX = 20; // To prevent tile from being next to the border
                int offsetY = 100;
                //Find random place on the board on X axis
                int x = Utilities.rangedRandomInt((int) (this.boardPane.getBoundsInLocal().getMinX() + offsetX), (int) (this.boardPane.getBoundsInLocal().getWidth() - offsetX - Tile.WIDTH));
                Tile t = new Tile(x, -offsetY, charTile);
                t.getTranslateTransition().setByY(this.boardPane.getHeight() + offsetY + 100); //Margin of error of 100 px
                t.getTranslateTransition().setDuration(translationDuration);

                this.tileList.add(t);
                this.boardPane.getChildren().add(t);

                t.getTranslateTransition().play();
            } finally {
                this.tileListMutex.unlock();
            }
        }

    }

    /**
     * Remove the tile from the tile list
     * @param tile the tile to remove
     */
    public void removeTile(Tile tile){
        synchronized (this.tileList){
            try {
                this.tileListMutex.lock();
                this.tileList.remove(tile);
                tile.getTranslateTransition().stop();
                this.boardPane.getChildren().remove(tile);
                tile.validated.unbind();
            } finally {
                this.tileListMutex.unlock();
            }
        }
    }

    /**
     * Return the bar managing the selected tile
     * @return the bar
     */
    public Bar getBar() {
        return bar;
    }

    /**Detect if it is the first time an access was made after putting the game in a window.
     * <br/>To use to get Bounds in screens*/
    public boolean isFirstCall() {
        return isFirstCall;
    }

    int i = 0;

    /**
     * Move all tiles, remove tiles that are out of the screen and update the bar
     */
    public void update(){
        //- Move all tiles- Replaced with TranslateTransition

        if(this.isFirstCall){
            this.originalBoundsGameBoard = Utilities.parentToScreen(this.boardPane);
            this.isFirstCall = false;
        }
        synchronized (this.tileList){
            try {
                this.tileListMutex.lock();
                //for(Tile tile : this.tileList){
                for(int i = 0; i < this.tileList.size(); i++){
                    //Bounds b = Utilities.parentToScreen(tile);
                    //Bounds b2 = this.originalBoundsGameBoard;
            /*if(this.boardPane.localToScreen(this.boardPane.getBoundsInLocal()).intersects(tile.localToScreen(tile.getBoundsInLocal()))){
                System.out.println("In bounds");
            }
             */
            /*if( Utilities.parentToScreen(tile).getMinY() >= this.originalBoundsGameBoard.getMinY()){
                if(this.originalBoundsGameBoard.intersects(Utilities.parentToScreen(tile))){
                    System.out.println("In bounds" + i++);
                } else {
                    System.out.println("Not in bounds");
                }
            } else {
                System.out.println("test in bounds");
            }

             */
                    //Prevent from being removed if just created
                    if( Utilities.parentToScreen(tileList.get(i)).getMinY() >= this.originalBoundsGameBoard.getMinY()){
                        if(! this.originalBoundsGameBoard.intersects(Utilities.parentToScreen(tileList.get(i)))){
                            removeTile(tileList.get(i));
                        } else {
                            if(! tileList.get(i).validated.getValue()){ //No need to try validated tiles
                                this.getBar().tryAddTile(tileList.get(i));
                            }
                        }
                    }
                }
                this.bar.update();
            } finally {
                this.tileListMutex.unlock();
            }
        }
    }


}
