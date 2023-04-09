

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
        pos++;
        if (pos >= opciones.length){pos = 0;}
        return out + "\n" + opciones[pos];
    }

    @Override
    public void Update() {
        if (Keyboard.getKeyValue() == "Intro"){ Engine.SetMenu( new StartMenu());}
        Keyboard.Clear();
    }
}
