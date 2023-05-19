

import Engine.Engine;
import Engine.Keyboard;
import Engine.Menu;
import Engine.Sound;
import Engine.SoundEngine;

public class TestSound extends Menu {
    @Override
    public String Frame() {
        return """
            Prueba de sonido (Solo acepta .WAV y .MP3):

            1. Quest.wav
            2. Quest.mp3
            3. Salir
            """;
    }

    @Override
    public void Update() {
        if(Keyboard.getKeyValue().equals("1")){
            Sound boom = Sound.fromPath("resources/Quest.wav");
            SoundEngine.playSound(boom);
        }
        else if(Keyboard.getKeyValue().equals("2")){
            Sound boom = Sound.fromPath("resources/Quest.mp3",1);
            SoundEngine.playSound(boom);
        }
        else if (Keyboard.getKeyValue().equals("3")){Engine.SetMenu(new StartMenu());}

        Keyboard.Clear();
        Engine.Render();
    }
}
