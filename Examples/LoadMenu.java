
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Debug.Debug;

public class LoadMenu extends Menu {
    int pos = 0;
    String[] opciones = {"/", "/", "-", "-", "\\", "\\", "|", "|"};


    @Override
    public void Start() {
        Debug.LogMessage("Testing Debug...");
        Debug.LogWarning("Testing Debug...");
        Debug.LogError("Testing Debug...");
        Debug.LogOK("Testing Debug...");
        Debug.Log("Testing Debug...", new Colors(Colors.TYPE_TEXT,100,100,100).and(new Colors(Colors.TYPE_BACKGROUND, 10, 10, 10)));
    }

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

        Debug.LogMessage(Keyboard.isShiftPressed()+"Shift");
        Debug.LogMessage(Keyboard.isAltPressed()+"Alt");
        Debug.LogMessage(Keyboard.isControlPressed()+"Control");

        if (pos >= opciones.length*2){pos = 0;}
        if (Keyboard.IsLastKeyOfType("Enter")){ Engine.SetMenu( new StartMenu());}
        
        Engine.Render();
        Keyboard.Clear();
    }
}
