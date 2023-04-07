 

public class TestInput implements Menu {
    
    String pantalla = "";
    @Override
    public String Frame() {
        
        return "Prueba de entrada de teclado: \n" +  pantalla;
    }
    
    @Override
    public void InputSystem() {
        if (Keyboard.getKeyCode() != 0){pantalla = Keyboard.getKeyValue() + " -> " + Keyboard.getKeyCode();}
        Keyboard.Reset();
    }
}
