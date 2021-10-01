package prototypeGame.model;

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

    public GameBoard(){

    }

    /**
     * Create a new tile and add it to the list
     * @param charTile
     */
    public void createTile(char charTile){
        //TODO, Place the tile randomly on x axis. On y = -20
    }

    /**
     * Remove the tile from the tile list
     * @param tile
     */
    public void removeTile(Tile tile){
        this.tileList.remove(tile);
    }

    /**
     * Remove tiles that are out of the screen
     */
    public void updateTile(){
        //TODO with size of the screen
    }


}
