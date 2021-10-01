package prototypeGame.model;

import javafx.geometry.Bounds;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Bar {

    ArrayList<Tile> tileContained = new ArrayList<>();

    Bounds bounds;

    Tile currentTile = null;

    public Bar(Bounds bounds){
        this.bounds = bounds;
    }

    /**
     * Try to add a tile to the list if it is not already in the list
     * @param tile
     * @return
     */
    public boolean tryAddTile(Tile tile){
        if(! tileContained.contains(tile)){
            if(this.bounds.intersects(tile.getBoundsInParent())){
                this.tileContained.add(tile);
                if(this.currentTile == null){
                    this.currentTile = tile;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return the first tile that entered the bar
     * @return null if no tile in bar
     */
    public Tile getFirstTile(){
        if(this.tileContained.size() >= 1){
            return this.tileContained.get(0);
        }
        return null;
    }

    /**
     * Return the current active tile
     * @return
     */
    public Tile getCurrentTile(){
        return this.currentTile;
    }

    /**
     *
     */
    /**
     * Remove tiles that aren't in the bar anymore. Update the currentTile var
     * @return the tile that was removed. Null if no tile was removed
     */
    public Tile updateTile(){
        boolean mod = false;
        Tile tmp = null;
        for(int i = 0; i<this.tileContained.size(); i++){
            if(this.bounds.intersects(tileContained.get(i).getBoundsInParent())){
                tmp = tileContained.get(i);
                tileContained.remove(i);
                mod = true;
                break;
            }
        }
        if(mod){
            Tile newCurrentTile = getFirstTile();
            support.firePropertyChange("currentTile", this.currentTile, newCurrentTile);
            this.currentTile = newCurrentTile;
        }
        return tmp;
    }


    /*---------------Change listener---------------------*/

    /**
     * The list of listeners
     */
    private PropertyChangeSupport support;

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

}
