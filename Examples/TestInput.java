
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;

public class TestInput extends Menu {
    
    String pantalla = "";

    @Override
    public String Frame() {
        
        return "Prueba de entrada de teclado: \n" +  pantalla;
    }
    
    @Override
    public void Update() {

        if (Keyboard.getKeyType() != null ){pantalla = Keyboard.getKeyValue() + " -> " + Keyboard.getKeyCode() + " -> " + Keyboard.getKeyType();}
        Keyboard.Clear();
        Engine.Render();
    }

}
