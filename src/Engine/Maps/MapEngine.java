package Engine.Maps;

import java.util.List;
import Engine.Debug.Debug;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class MapEngine {
    
    /**
     * Loads in the map the new set of tiles
     * @param map
     * @param NewTiles
     */
    public static void updateMap(MapObject map, Tile[][] NewTiles){
        int Width = map.getWidth();
        int Height = map.getHeight();

        if(NewTiles.length * NewTiles[0].length < Width*Height){throw new IllegalArgumentException("Size of Tiles insufficient");}
        
        map.setTiles(NewTiles);
    }

    /**
     * Loads in the map the new set of tiles
     * @param map
     * @param NewTiles
     */
    public static void updateMap(MapObject map, List<Tile> NewTiles){
        
        int Width = map.getWidth();
        int Height = map.getHeight();

        if(NewTiles.size() < Width*Height){throw new IllegalArgumentException("Size of Tiles insufficient");}
        int actualWidth = 0;
        int actualHeight = 0;
        
        Tile[][] Tiles = new Tile[Height][Width];

        for (Tile t:NewTiles){
            Tiles[actualHeight][actualWidth] = t;
            actualWidth++;
            if (actualWidth >= Width){
                actualHeight++;
                actualWidth = 0;
            }
            if (actualHeight >= Height){
                break;
            }
            System.out.println(actualWidth + "  " + actualHeight);
        }
        map.setTiles(Tiles);
    }

    /**
     * Loads in the map the new set of tiles
     * @param map
     * @param NewTiles
     */
    public static void updateMap(MapObject map, Tile[] NewTiles){

        int Width = map.getWidth();
        int Height = map.getHeight();
        if(NewTiles.length < Width*Height){throw new IllegalArgumentException("Size of Tiles insufficient");}
        int actualWidth = 0;
        int actualHeight = 0;

        Tile[][] Tiles = new Tile[Height][Width];

        for (Tile t:NewTiles){
            Tiles[actualHeight][actualWidth] = t;
            actualWidth++;
            if (actualWidth >= Width){
                actualHeight++;
                actualWidth = 0;
            }
            if (actualHeight >= Height){
                break;
            }
        }
        map.setTiles(Tiles);
    }

    /**
     * Returns the map as string
     * @param map
     * @return
     */
    public static String printMap(MapObject map){
        String res = "";
        for (int i = 0; i < map.getHeight(); i++){
            for (int a = 0; a < map.getWidth(); a++){
                res += map.getTile(a, i);
            }
            res += "\n";
        }
        res = res.substring(0, res.length()-2);
        return res;
    }

    /**
     * Returns the map as string, but only the Tiles inside the Field Of View, starting in the coordinate (centerW, centerH)
     * @param map
     * @param centerW
     * @param centerH
     * @param FOV
     * @return
     */
    public static String printMap(MapObject map, int centerW, int centerH, int FOV){
        try{
            String res = "";
            for (int i = centerH-1-FOV; i < centerH+FOV; i++){
                for (int a = centerW-1-FOV; a < centerW+FOV; a++){
                    if (a >= 0 && a <= map.getWidth()-1){
                        res += map.getTile(a, i);
                    }
                }
                res += "\n";
            }
            res = res.substring(0, res.length()-2);
            return res;
        }
        catch(IndexOutOfBoundsException e){
            Debug.LogError("Error, width or heigth is out of bounds. " + e.getMessage());
            return null;
        }
        catch(Exception e){
            Debug.LogError(e.getMessage());
            return null;}
    }

    /**
     * Saves the map in the file (name)
     * @param map
     * @param name
     * @return
     * @throws IOException
     */
    public static boolean saveMap(MapObject map, String name) throws IOException{
        
        FileOutputStream fileOut = new FileOutputStream(name);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(map.getTiles());
        out.close();
        fileOut.close();
        return true;
        
    }

    /**
     * Loads and returns map from the file (name)
     * @param name
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static MapObject loadMap(String name) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(name);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Tile[][] tiles = (Tile[][]) in.readObject();
        in.close();
        fileIn.close();
        return new MapObject(tiles[0].length, tiles.length,tiles);
    }

    /**
     * Checks if point exists in map
     * @param map
     * @param W
     * @param H
     * @return
     */
    public static boolean isPointValid(MapObject map, int W, int H){return map.isPointValid(W, H);}

    
}