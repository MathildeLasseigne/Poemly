package prototypeGame.model;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * The bar containing the tiles in its boundaries. <b>Manage & choose the current selected tile.</b>
 * <br/> Select the first tile not validated (on a first come basis) automatically.
 * Current selected tile can be null if no not validated Tile is in the boundary
 * <br/>Select a new tile automatically when current is either validated or removed from the Bar
 * <br/><b>Notify listeners when current tile is modified</b>
 */
public class Bar {

    ArrayList<Tile> tileContained = new ArrayList<>();

    Bounds bounds;

    private Tile currentTile = null;

    /**Listen to modifications on the current tile validated property.
     * If changes happen, call selectNewTile() */
    private ChangeListener tileChangeListener;

    /**
     * The bar containing the tiles in its boundaries. <b>Manage & choose the current selected tile.</b>
     * <br/> Select the first tile not validated (on a first come basis) automatically.
     * Current selected tile can be null if no not validated Tile is in the boundary
     * <br/>Select a new tile automatically when current is either validated or removed from the Bar
     * <br/><b>Notify listeners when current tile is modified</b>
     * @param bounds The bounds of the bar, used to add or remove tiles
     */
    public Bar(Bounds bounds){
        this.bounds = bounds;

        tileChangeListener = (observable, oldValue, newValue) -> selectNewTile();
    }

    /**
     * Try to add a tile to the list if it is not already in the list.
     * <br/>Select a new current tile if necessary
     * @param tile the Tile to add
     * @return false if the tile is already in the bar or do not intersect the bar
     */
    public boolean tryAddTile(Tile tile){
        if(! tileContained.contains(tile)){
            if(this.bounds.intersects(tile.getBoundsInParent())){
                this.tileContained.add(tile);
                selectNewTile();
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a tile from the list and from the current selection if necessary.
     * If necessary, remove its listeners
     * @param tile the tile to remove
     */
    private void removeTile(Tile tile){
        this.tileContained.remove(tile);
        if(this.currentTile == tile){
            this.currentTile.validated.removeListener(tileChangeListener);
            this.currentTile = null;
        }
    }


    /**
     * Return the current active tile. Can be null if if last selected Tile is validated and no new tile is present
     * @return the current tile
     */
    public Tile getCurrentTile(){
        return this.currentTile;
    }

    /**
     * Remove tiles that aren't in the bar anymore. Update the currentTile var if necessary
     * <br/>Fire property change to Bar listener if current Tile was changed
     * @return the tile that was removed. Null if no tile was removed
     */
    public Tile update(){
        boolean mod = false;
        Tile tmp = null;
        for(int i = 0; i<this.tileContained.size(); i++){
            if(! this.bounds.intersects(tileContained.get(i).getBoundsInParent())){
                tmp = tileContained.get(i);
                this.removeTile(tileContained.get(i));
                mod = true;
                break;
            }
        }
        if(mod){
            selectNewTile();
        }
        return tmp;
    }


    /**
     * Select the next tile from the list, if current tile is validated.
     * Set the listeners.
     * <br/> this.currentTile can be null if there was no new Tile
     * <br/>Fire property change to Bar listener if current Tile changed successfully
     * @return false if there wasn't any new tile or no change, else true
     */
    private boolean selectNewTile(){
        if(this.currentTile == null){ //If no current tile, select new one and add listener
            Tile t = getFirstNotValidated();
            if(t != null){
                this.currentTile = t;
                this.currentTile.validated.addListener(tileChangeListener);
                currentTileProperty.firePropertyChange("currentTile", null, this.currentTile); //Notify property listeners
                return true;
            } else {
                return false;
            }
        } else {
            //If current tile validated, change it, if not keep it
            if(this.currentTile.validated.getValue()){
                this.currentTile.validated.removeListener(tileChangeListener);
                this.currentTile = null;
                return selectNewTile();
            } else {
                return false;
            }
        }
    }

    /**
     * Search and return the first tile not validated from the list
     * @return null if no tile not validated was found in the bar
     */
    private Tile getFirstNotValidated(){
        for(int i = 0; i < tileContained.size(); i++){
            if(tileContained.get(i).validated.getValue() == false){
                return tileContained.get(i);
            }
        }
        return null;
    }



    /*---------------Change listener---------------------*/

    /**
     * The list of listeners
     */
    private final PropertyChangeSupport currentTileProperty = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        currentTileProperty.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        currentTileProperty.removePropertyChangeListener(pcl);
    }



    /*Example :
    public class PCLNewsChannel implements PropertyChangeListener {

    private String news;

    public void propertyChange(PropertyChangeEvent evt) {
        this.setNews((String) evt.getNewValue());
    }
}
     */
}
