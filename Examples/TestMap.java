

import java.util.List;

import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.*;

import java.util.ArrayList;

public class TestMap extends Menu {

    String map;
    MapObject mapa;

    String[] textures = new String[]{"0","1","2","3","4","5","6","7","8","9", "*"};
    int posx = 5;
    int posy = 5;

    @Override
    public void Start() {
        int Width = 11;
        int Height = 5;
        List<Tile> Tiles = new ArrayList<>();

        for (int i = 0; i <5; i++ ){
            for (String t: textures){
                Tiles.add(new Tile(t, Colors.White));
            }
        }

        try{
            mapa = new MapObject(Width, Height);
            MapEngine.updateMap(mapa, Tiles);
            MapEngine.saveMap(mapa, "mapa.map");
            Keyboard.Clear();
        }
        catch(Exception e){System.out.println(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}

    }

    @Override
    public String Frame() {
        return MapEngine.printMap(mapa);
    }

    @Override
    public void Update() {
        if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
            posy--;
            Engine.Render();
        }
        else if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
            posy++;
            Engine.Render();
        }
        else if(Keyboard.IsLastKeyValue("D") || Keyboard.IsLastKeyOfType("ArrowRight")){
            posx++;
            Engine.Render();
        }
        else if(Keyboard.IsLastKeyValue("A") || Keyboard.IsLastKeyOfType("ArrowLeft")){
            posx--;
            Engine.Render();
        }
        if (Keyboard.IsLastKeyOfType("Enter")){ 
            try{
                mapa = MapEngine.loadMap("mapa.map");}
            catch(Exception e){Engine.LogException(e);}
            Keyboard.Clear();
        }

        Keyboard.Clear();
    }
}
