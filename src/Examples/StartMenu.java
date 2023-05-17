package Examples;

import Engine.Engine;
import Engine.Keyboard;
import Engine.Menu;

public class StartMenu extends Menu {

    String[] opciones = {"   Pantalla de carga", "   Prueba de colores", "   Prueba de sonido", "   Prueba de entrada", "   Prueba de red", "   Salir"};
    int pos = 0;
    String Titulo = """
        ████████╗███████╗███████╗████████╗    ███╗   ███╗███████╗███╗   ██╗██╗   ██╗
        ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝    ████╗ ████║██╔════╝████╗  ██║██║   ██║
           ██║   █████╗  ███████╗   ██║       ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║
           ██║   ██╔══╝  ╚════██║   ██║       ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║
           ██║   ███████╗███████║   ██║       ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝
           ╚═╝   ╚══════╝╚══════╝   ╚═╝       ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝
    
        """;

    @Override
    public String Frame() {
        String res = Titulo;
        for( int i = 0; i < opciones.length; i++){
            if (i== pos){res += ">" + opciones[i].substring(1) + "\n";}
            else{res += opciones[i] + "\n"; }
        }
        return res;
    }

    @Override
    public void Update() {
        if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
            pos++;
            if (pos >= opciones.length){pos = 0;}
            Engine.Render();
        }
        else if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
            pos--;
            if(pos < 0){pos = opciones.length-1;}
            Engine.Render();
        }
        else if (Keyboard.IsLastKeyOfType("Enter")){
            Keyboard.Clear();
            if (pos == 0) {Engine.SetMenu( new LoadMenu());}
            else if (pos == 1 ){Engine.SetMenu( new Rainbow());}
            else if (pos == 2 ){Engine.SetMenu( new TestSound());}
            else if (pos == 3 ){Engine.SetMenu( new TestInput());}
            else if (pos == 4 ){Engine.SetMenu( new TestChat());}
            else{ System.exit(1);}
            
        }

        Keyboard.Clear();

    }
    

}
