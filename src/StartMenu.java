public class StartMenu extends Menu {

    String[] opciones = {"   Pantalla de carga", "   Prueba de colores", "   Prueba de sonido", "   Prueba de entrada", "   Salir"};
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
        if(Keyboard.getKeyValue() == "S"){
            pos++;
            if (pos >= opciones.length){pos = 0;}
        }
        else if(Keyboard.getKeyValue() == "W"){
            pos--;
            if(pos < 0){pos = opciones.length-1;}
        }
        else if (Keyboard.getKeyValue() == "Intro"){
            if (pos == 0) {Engine.SetMenu( new LoadMenu());}
            else if (pos == 1 ){Engine.SetMenu( new Rainbow());}
            else if (pos == 2 ){Engine.SetMenu( new TestSound());}
            else if (pos == 3 ){Engine.SetMenu( new TestInput());}
            else{ System.exit(1);}
            
        }
        Keyboard.Clear();

    }
    
}
