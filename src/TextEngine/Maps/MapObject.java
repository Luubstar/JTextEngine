package TextEngine.Maps;

import java.util.ArrayList;
import java.util.List;

import TextEngine.Debug.Debug;

public class MapObject {

    private Tile[][] Tiles;
    private int Height;
    private int Width;

    public Tile[][] getTiles() {return this.Tiles;}

    public void setTiles(Tile[][] Tiles) {this.Tiles = Tiles;}

    public int getHeight() {return this.Height;}

    public void setHeight(int Height) {this.Height = Height;}

    public int getWidth() {return this.Width;}

    public void setWidth(int Width) {this.Width = Width;}
    

    public MapObject(int Width, int Height, Tile[][] Tiles){
        this.Width = Width;
        this.Height = Height;
        this.Tiles = Tiles;
    }

    public MapObject(int Width, int Height){
        this.Width = Width;
        this.Height = Height;
        Tiles = new Tile[Height][Width];
    }

    public void setTile(int Width, int Height, Tile newTile){
        try{
            Tiles[Height][Width] = newTile;
        }
        catch(IndexOutOfBoundsException e){
            Debug.LogError("Error, width or heigth is out of bounds. " + e.getMessage());
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }

    public Tile getTile(int Width, int Height){
        try{
            return Tiles[Height][Width];
        }
        catch(IndexOutOfBoundsException e){
            Debug.LogError("Error, width or heigth is out of bounds. " + e.getMessage());
            return null;
        }
        catch(Exception e){
            Debug.LogError(e.getMessage());
            return null;}
    }


    public boolean isPointValid(int W, int H){
        return ( (H < getHeight() && H >= 0 ) && (W < getWidth() && W >= 0));
    }

    public Tile[] getTilesByTag(String tag){
        List<Tile> TWithTag = new ArrayList<>(); 
        for (Tile[] row : Tiles){
            for (Tile tile: row){
                String[] tags = tile.getTags();
                for(String t : tags){
                    if (t == tag){
                        TWithTag.add(tile);
                        break;
                    }
                }
            }
        }
        Tile[] endTiles = new Tile[TWithTag.size()];
        return TWithTag.toArray(endTiles);
    }

 
}
