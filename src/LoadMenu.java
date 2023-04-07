 

public class LoadMenu implements Menu {
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
        pos++;
        if (pos >= opciones.length){pos = 0;}
        return out + "\n" + opciones[pos];
    }

    @Override
    public void InputSystem() {
        if (Keyboard.getKeyValue() == "Intro"){ Engine.SetMenu( new StartMenu());}
        Keyboard.Reset();
    }
}
