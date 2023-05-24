
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;

public class LoadMenu extends Menu {
    int pos = 0;
    String[] opciones = {"/", "/", "-", "-", "\\", "\\", "|", "|"};

    @Override
    public String Frame() {
        String out = """
            ██       ██████   █████  ██████  ██ ███    ██  ██████  
            ██      ██    ██ ██   ██ ██   ██ ██ ████   ██ ██       
            ██      ██    ██ ███████ ██   ██ ██ ██ ██  ██ ██   ███ 
            ██      ██    ██ ██   ██ ██   ██ ██ ██  ██ ██ ██    ██ 
            ███████  ██████  ██   ██ ██████  ██ ██   ████  ██████  
                                                                   
                
            
            Pulsa intro para regresar
            
            """;
        return out + "\n" + opciones[pos/2];
    }

    @Override
    public void Update() {
        pos++;
        if (pos >= opciones.length*2){pos = 0;}
        if (Keyboard.IsLastKeyOfType("Enter")){ Engine.SetMenu( new StartMenu());}
        
        Engine.Render();
        Keyboard.Clear();
    }
}
