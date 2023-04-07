import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioInputStream;

import java.io.File;

public class Sound extends Thread {

    Clip clip;
    String tags;
    AudioInputStream AudioIS;
    int Loops = 1;

    @Override
    public void run() {
        try {
            this.clip.loop(Loops);

            while (!this.clip.isRunning()) {
                Thread.sleep(10);
            }
            while (this.clip.isRunning()) {
                Thread.sleep(10);
            }

            this.clip.close();
            this.AudioIS.close();

        } catch (Exception e){Engine.DebugLog(e.getMessage(), Colors.Red);}
    }
    

    //ONLY WAV FOR NOW
    public Sound(File soundFile, float volume){
        try{
            String extension = Engine.getFileExtension(soundFile);
            if (extension == "wav"){
                AudioIS = AudioSystem.getAudioInputStream(soundFile);

                clip = AudioSystem.getClip();
                clip.open(AudioIS);

                this.setVolume(volume);
            
            }
            else{
                throw new Exception("Error loading the audio file");
            }
        }
        catch (Exception e){Engine.DebugLog(e.getMessage(), Colors.Red);}
    }

    public static Sound fromPath(String filepath){
        try{
            File file = new File(filepath);
            return new Sound(file, SoundEngine.GeneralVolume);
        }
        catch(Exception e){Engine.DebugLog(e.getMessage(), Colors.Red); return null;}
    }

    public void setPosition(long Microseconds){
        try{this.clip.setMicrosecondPosition(Microseconds);}
        catch(Exception e){Engine.DebugLog(e.getMessage(), Colors.Red);}}
    public long getPosition(){return this.clip.getMicrosecondPosition();}

    public boolean isPlaying(){return this.clip.isRunning();}


    public void setVolume(float volume){
        try{
            if (!(volume > 1 && volume < 0)){
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float gain = volume > 0 ? (float) (Math.log10(volume) * 20) : -80; 

                gainControl.setValue(gain);
            }
            else{throw new Exception("Float value out of bounds");}
        }
        catch(Exception e){Engine.DebugLog(e.getMessage(), Colors.Red);}
    }
    

    
}
