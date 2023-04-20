package Engine;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioInputStream;

import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Sound extends Thread {

    Clip clip;
    Player player;
    float volume;
    AudioInputStream AudioIS;
    int Loops = 1;
    String extension;
    File file;

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
                for (int i = 0; i < Loops; i++){
                    Sound sonido = new Sound(file, volume);
                    sonido.player.play();
                    sonido.player.close();
                }
                player.play();
                player.close();
            }
            else{throw new Exception("Error reproduciendo");}

        } catch (Exception e){Debug.LogError(e.getMessage());}
    }
    
    public Sound(File soundFile, float volume){
        try{
            extension = Engine.getFileExtension(soundFile);

            if (extension.equals(".wav")){
                AudioIS = AudioSystem.getAudioInputStream(soundFile);

                clip = AudioSystem.getClip();
                clip.open(AudioIS);

                this.setVolume(volume);
                this.file = soundFile;
            
            }
            else if(extension.equals(".mp3")){
                FileInputStream IS = new FileInputStream(soundFile);
                player = new Player(IS);
                player.setVolume(volume);
                this.volume = mapToDecibels(volume);
                this.file = soundFile;
            }
            else{
                throw new Exception("Error loading the audio file " + extension);
            }
        }
        catch (Exception e){Debug.LogError(e.getMessage());}
    }

    public static Sound fromPath(String filepath){
        try{
            File file = new File(filepath);
            return new Sound(file, SoundEngine.GeneralVolume);
        }
        catch(Exception e){Debug.LogError(e.getMessage()); return null;}
    }

    public static Sound fromPath(String filepath, int volume){
        try{
            File file = new File(filepath);
            return new Sound(file, volume);
        }
        catch(Exception e){Debug.LogError(e.getMessage()); return null;}
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
        catch(Exception e){Debug.LogError(e.getMessage());}
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
        catch (Exception e){Debug.LogError(e.getMessage()); return 0;}
    }

    public void setPosition(long Microseconds){
        if(extension.equals(".wav")){
            try{this.clip.setMicrosecondPosition(Microseconds);}
            catch(Exception e){Debug.LogError(e.getMessage());}}
        else if(extension.equals(".mp3")){Debug.Log("Los archivos de tipo .mp3 no admiten el comando ''.setPosition'", Colors.Red);}}

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
