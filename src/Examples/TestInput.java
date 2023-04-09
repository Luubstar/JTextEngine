package Examples;
import Engine.Keyboard;
import Engine.Menu;

public class TestInput extends Menu {
    
    String pantalla = "";
    @Override
    public String Frame() {
        
        return "Prueba de entrada de teclado: \n" +  pantalla;
    }
    
    @Override
    public void Update() {
        if (Keyboard.getKeyCode() != 0){pantalla = Keyboard.getKeyValue() + " -> " + Keyboard.getKeyCode() + " -> " + String.valueOf(Keyboard.getKeyCharacter());}
        Keyboard.Clear();
    }
}
