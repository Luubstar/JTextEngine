package Engine;

import Engine.Debug.Debug;

public class SoundEngine {

    public static float GeneralVolume = 0.75f;

    public static void playSound(Sound Sonido) {
        Sonido.start();
    }

    public static void playSound(Sound Sonido, int Looptimes) {
        Sonido.Loops = Looptimes;
        Sonido.start();
    }

    public static void stopSound(Sound Sonido){
        try{
            Sonido.clip.close();
            Sonido.AudioIS.close();
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }

    /**@param newvol (min 0, max 1)*/
    public void setGeneralVolume(float NewVol){
        try{
            if (!(NewVol > 1 && NewVol < 0)){GeneralVolume = NewVol;}
            else{throw new Exception("Float value out of bounds");}
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }
    

    public float getGenrealVolume(){return GeneralVolume;}
}
