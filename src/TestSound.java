public class TestSound implements Menu {
    @Override
    public String Frame() {
        return """
            Prueba de sonido (Solo acepta .WAV):

            1. Misión completada
            3. Salir
            """;
    }

    @Override
    public void InputSystem() {
        if(Keyboard.getKeyValue() == "1"){
            Sound boom = Sound.fromPath("../resources/Quest.wav");
            SoundEngine.playSound(boom);
        }
        else if (Keyboard.getKeyValue() == "3"){Engine.SetMenu(new StartMenu());}

        Keyboard.Reset();
    }
}
