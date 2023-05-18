package Examples;
import Engine.Colors;
import Engine.Engine;
import Engine.Keyboard;
import Engine.Menu;

public class Rainbow extends Menu{

    String[] colores = {Colors.RED, Colors.YELLOW, Colors.GREEN,Colors.CYAN, Colors.BLUE, Colors.MAGENTA};
    int desfase = 0;

    @Override
    public String Frame() {
        String res = "";
        for(int i = 0; i < colores.length; i++){
            int index = i + desfase;
            if (index >= colores.length){index -= colores.length;}
            res += colores[index] + "█████████████████████████████████████████████\n" + Colors.SANE;
        }
        return res;
    }

    @Override
    public void Update() {
        
        desfase++;
        if (desfase >= colores.length){desfase -= colores.length;}
        if (Keyboard.IsLastKeyOfType("Enter")){ Engine.SetMenu( new StartMenu());}
        Keyboard.Clear();
        Engine.Render();
    }
}
