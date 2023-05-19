package Engine.Maps;
import java.io.Serializable;

import Engine.Colors;

public class Tile implements Serializable{
    
    private String Texture = "*";
    private Colors Color;
    private String[] tags;   

    public Tile(String T, Colors C, String[] tags){
        Texture = T;
        Color = C;
        this.tags = tags;
    }

    public Tile(String T, Colors C){
        Texture = T;
        Color = C;
    }

    @Override
    public String toString() {
        return Color.colorize(Texture);
    }

    public String getTexture(){return Texture;}
    public void setTexture(String T){Texture = T;}

    public Colors getColor() {return this.Color;}
    public void setColor(Colors Color) {this.Color = Color;}

    public String[] getTags() {return this.tags;}
    public void setTags(String[] tags) {this.tags = tags;}

}
