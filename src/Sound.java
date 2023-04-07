import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioInputStream;

import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Sound extends Thread {

    Clip clip;
    String tags;
    Player player;
    float volume;
    AudioInputStream AudioIS;
    int Loops = 0;
    String extension;

    @Override
    public void run() {
        try {
            if (extension.equals(".wav")){
                this.clip.loop(Loops);

                while (!this.clip.isRunning()) {
                    Thread.sleep(10);
                }
                while (this.clip.isRunning()) {
                    Thread.sleep(10);
                }

                this.clip.close();
                this.AudioIS.close();}
            else if (extension.equals(".mp3")){
                player.play(Loops);
                player.setVolume(volume);

                while(!player.isComplete()){
                    Thread.sleep(10);
                }
                player.close();
            }

        } catch (Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}
    }
    
    public Sound(File soundFile, float volume){
        try{
            extension = Engine.getFileExtension(soundFile);

            if (extension.equals(".wav")){
                AudioIS = AudioSystem.getAudioInputStream(soundFile);

                clip = AudioSystem.getClip();
                clip.open(AudioIS);

                this.setVolume(volume);
            
            }
            else if(extension.equals(".mp3")){
                FileInputStream IS = new FileInputStream(soundFile);
                player = new Player(IS);
                player.setVolume(volume);
                this.volume = mapToDecibels(volume);
            }
            else{
                throw new Exception("Error loading the audio file " + extension);
            }
        }
        catch (Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}
    }

    public static Sound fromPath(String filepath){
        try{
            File file = new File(filepath);
            return new Sound(file, SoundEngine.GeneralVolume);
        }
        catch(Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red); return null;}
    }

    public void setVolume(float volume){
        try{
            if (extension.equals(".wav")){
                if (!(volume > 1 && volume < 0)){
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                    float gain = volume > 0 ? (float) (Math.log10(volume) * 20) : -80; 

                    gainControl.setValue(gain);
                }
                else{throw new Exception("Float value out of bounds");}
            }
            else if (extension.equals(".mp3")){
                player.setVolume(mapToDecibels(volume));
                this.volume = mapToDecibels(volume);
            }
        }
        catch(Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}
    }

    public static float mapToDecibels(float value) {
        try{
            if (value < 0 || value > 1) {
                throw new IllegalArgumentException("El valor debe estar en el rango [0, 1]");
            }

            float dbRange = -80.0f;

            float decibels = (1 -value) * dbRange;

            return decibels;
        }
        catch (Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red); return 0;}
    }

    public void setPosition(long Microseconds){
        if(extension.equals(".wav")){
            try{this.clip.setMicrosecondPosition(Microseconds);}
            catch(Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}}
        else if(extension.equals(".mp3")){Engine.DebugLog("Los archivos de tipo .mp3 no admiten el comando ''.setPosition'", Colors.Red);}}

    public long getPosition(){
        if(extension.equals(".wav")){return this.clip.getMicrosecondPosition();}
        else if (extension.equals(".mp3")){return this.player.getPosition();}
        else{return 0;}
    }

    //**Los archivos mp3 muestran si no están completados. Cuidado con eso */
    public boolean isPlaying(){
        if(extension.equals(".wav")){return this.clip.isRunning();}
        else if(extension.equals(".mp3")){return !this.player.isComplete();}
        return false;
    }

    

    
}
