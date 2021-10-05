package prototypeGame.model;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import widgets.tools.Utilities;

import java.util.ArrayList;

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

    /**The duration of the tile translation to the bottom of the screen*/
    private Duration translationDuration = Duration.millis(10000);

    /**
     * Manage the game panel. Create the bar in the board
     * @param gameBoard the pane to which the tiles will be added
     * @param barBounds the bounds of the Bar
     */
    public GameBoard(Pane gameBoard, Bounds barBounds){
        this.bar = new Bar(barBounds);
        this.boardPane = gameBoard;
    }

    /**
     * Create a new tile and add it to the list.
     * New tile will immediately begin its movement
     * @param charTile the char of the new tile
     */
    public void createTile(char charTile){
        int offsetX = 20; // To prevent tile from being next to the border
        int offsetY = 20;
        //Find random place on the board on X axis
        int x = Utilities.rangedRandomInt((int) (this.boardPane.getBoundsInLocal().getMinX() + offsetX), (int) (this.boardPane.getBoundsInLocal().getWidth() - offsetX - Tile.WIDTH));
        Tile t = new Tile(x, -offsetY, charTile);
        t.getTranslateTransition().setByY(this.boardPane.getHeight() + offsetY + 100); //Margin of error of 100 px
        t.getTranslateTransition().setDuration(translationDuration);

        this.tileList.add(t);
        this.boardPane.getChildren().add(t);

        t.getTranslateTransition().play();

    }

    /**
     * Remove the tile from the tile list
     * @param tile the tile to remove
     */
    public void removeTile(Tile tile){
        this.tileList.remove(tile);
        tile.getTranslateTransition().stop();
        this.boardPane.getChildren().remove(tile);
        tile.validated.unbind();
    }

    /**
     * Return the bar managing the selected tile
     * @return the bar
     */
    public Bar getBar() {
        return bar;
    }

    /**
     * Move all tiles, remove tiles that are out of the screen and update the bar
     */
    public void update(){
        //- Move all tiles- Replaced with TranslateTransition
        for(Tile tile : this.tileList){
            if(! this.boardPane.getBoundsInLocal().intersects(tile.getBoundsInParent())){
                removeTile(tile);
            } else {
                if(! tile.validated.getValue()){ //No need to try validated tiles
                    this.getBar().tryAddTile(tile);
                }
            }
        }
        this.bar.update();
    }


}
